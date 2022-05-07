/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.core.promotion.pintuan.service.impl;

import cloud.shopfly.b2c.core.promotion.pintuan.model.Pintuan;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanOptionEnum;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanOrder;
import cloud.shopfly.b2c.core.promotion.pintuan.model.PintuanOrderStatus;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PinTuanSearchManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanGoodsManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanManager;
import cloud.shopfly.b2c.core.promotion.pintuan.service.PintuanOrderManager;
import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionStatusEnum;
import cloud.shopfly.b2c.core.base.message.PintuanChangeMsg;
import cloud.shopfly.b2c.core.base.rabbitmq.TimeExecute;
import cloud.shopfly.b2c.core.promotion.pintuan.exception.PintuanErrorCode;
import cloud.shopfly.b2c.core.statistics.util.DateUtil;
import cloud.shopfly.b2c.framework.context.AdminUserContext;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.trigger.Interface.TimeTrigger;
import cloud.shopfly.b2c.framework.util.SqlUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cloud.shopfly.b2c.framework.database.DaoSupport;
import cloud.shopfly.b2c.framework.database.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Group business class
 *
 * @author admin
 * @version vv1.0.0
 * @since vv7.1.0
 * 2019-01-21 15:17:57
 */
@Service
public class PintuanManagerImpl implements PintuanManager {

    /**
     * Review group promotion prefix
     */
    private static final String TRIGGER_PREFIX = "pintuan_promotion_";

    @Autowired
    
    private DaoSupport tradeDaoSupport;

    @Autowired
    private PintuanGoodsManager pintuanGoodsManager;

    @Autowired
    private PinTuanSearchManager pinTuanSearchManager;

    @Autowired
    private TimeTrigger timeTrigger;

    @Autowired
    private PintuanOrderManager pintuanOrderManager;

    @Override
    public Page list(int page, int pageSize, String keyword) {

        String sql = "select * from es_pintuan ";

        List<String> where = new ArrayList<>();
        List param = new ArrayList<>();

        // If the seller visits
        if (!StringUtil.isEmpty(keyword)) {
            where.add(" promotion_name like (?) ");
            param.add("%" + keyword + "%");
        }
        sql += SqlUtil.sqlSplicing(where);
        sql += " order by promotion_id desc";
        Page webPage = this.tradeDaoSupport.queryForPage(sql, page, pageSize, Pintuan.class, param.toArray());

        return webPage;
    }

    @Override
    public List<Pintuan> get(String status) {
        String sql = "select * from es_pintuan where status = ?";
        if (PromotionStatusEnum.UNDERWAY.name().equals(status)) {
            long now = DateUtil.getDateline();
            sql += " and start_time > " + now + " and end_time < " + now;
        }
        return tradeDaoSupport.queryForList(sql, Pintuan.class, status);
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pintuan add(Pintuan pintuan) {

        this.verifyParam(pintuan.getStartTime(), pintuan.getEndTime());
        pintuan.setStatus(PromotionStatusEnum.WAIT.name());
        pintuan.setCreateTime(DateUtil.getDateline());
        // The actionable state is nothing, which means that the activity cannot perform any operations
        pintuan.setOptionStatus(PintuanOptionEnum.NOTHING.name());
        this.tradeDaoSupport.insert(pintuan);
        Integer pintuanId = this.tradeDaoSupport.getLastId("es_pintuan");
        pintuan.setPromotionId(pintuanId);

        // Create an activity to enable delayed tasks
        PintuanChangeMsg pintuanChangeMsg = new PintuanChangeMsg();
        pintuanChangeMsg.setPintuanId(pintuan.getPromotionId());
        pintuanChangeMsg.setOptionType(1);
        timeTrigger.add(TimeExecute.PINTUAN_EXECUTER, pintuanChangeMsg, pintuan.getStartTime(), TRIGGER_PREFIX + pintuan.getPromotionId());
        pintuan.setPromotionId(this.tradeDaoSupport.getLastId("es_pintuan"));
        return pintuan;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Pintuan edit(Pintuan pintuan, Integer id) {
        // Get a group event
        Pintuan oldPintaun = this.getModel(id);
        // Verify whether a group can be operated
        if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
            throw new ServiceException(PintuanErrorCode.E5017.code(), PintuanErrorCode.E5017.describe());
        }

        this.verifyParam(pintuan.getStartTime(), pintuan.getEndTime());
        this.tradeDaoSupport.update(pintuan, id);

        PintuanChangeMsg pintuanChangeMsg = new PintuanChangeMsg();
        pintuanChangeMsg.setPintuanId(pintuan.getPromotionId());
        pintuanChangeMsg.setOptionType(1);
        timeTrigger.edit(TimeExecute.PINTUAN_EXECUTER, pintuanChangeMsg, oldPintaun.getStartTime(), pintuan.getStartTime(), TRIGGER_PREFIX + id);

        return pintuan;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Pintuan pintuan = this.getModel(id);

        if (pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name())) {
            throw new ServiceException(PintuanErrorCode.E5017.code(), PintuanErrorCode.E5017.describe());
        }

        this.tradeDaoSupport.delete(Pintuan.class, id);


        timeTrigger.delete(TimeExecute.PINTUAN_EXECUTER, pintuan.getStartTime(), TRIGGER_PREFIX + id);
    }

    @Override
    public Pintuan getModel(Integer id) {
        return this.tradeDaoSupport.queryForObject(Pintuan.class, id);
    }


    /**
     * Start an activity
     *
     * @param promotionId
     */
    @Override
    public void openPromotion(Integer promotionId) {

        Pintuan pintuan = this.getModel(promotionId);

        // If its still active
        // Change the state to In progress and the actionable state to closed
        if (pintuan.getEndTime() > DateUtil.getDateline()) {
            this.tradeDaoSupport.execute("update es_pintuan set status = ? ,option_status=? where promotion_id = ?", PromotionStatusEnum.UNDERWAY.name(), PintuanOptionEnum.CAN_CLOSE.name(), promotionId);
            pintuanGoodsManager.addIndex(promotionId);
        } else {
            // Outside the activity time range, the modified state is finished and the actionable state becomes nothing
            this.tradeDaoSupport.execute("update es_pintuan set status = ? ,option_status=? where promotion_id = ?", PromotionStatusEnum.END.name(), PintuanOptionEnum.NOTHING.name(), promotionId);
        }

    }

    /**
     * Stop an activity
     *
     * @param promotionId
     */
    @Override
    public void closePromotion(Integer promotionId) {

        Pintuan pintuan = this.getModel(promotionId);

        // If the end time is smaller than the current time
        // The operation state is on, and the activity state is ended
        if (pintuan.getEndTime() > DateUtil.getDateline()) {
            // Indicates that it can be opened again. Unformed orders are not processed because it can be opened
            this.tradeDaoSupport.execute("update es_pintuan set status = ? ,option_status=? where promotion_id = ?", PromotionStatusEnum.END.name(), PintuanOptionEnum.CAN_OPEN.name(), promotionId);
        } else {
            this.tradeDaoSupport.execute("update es_pintuan set status = ? ,option_status=? where promotion_id = ?", PromotionStatusEnum.END.name(), PintuanOptionEnum.NOTHING.name(), promotionId);
            // Query all ungrouped orders for this activity (unpaid, paid ungrouped)
            String sql = "select * from es_pintuan_order where (order_status = ? or order_status = ?) and pintuan_id = ?";

            List<PintuanOrder> orderList = this.tradeDaoSupport.queryForList(sql, PintuanOrder.class, PintuanOrderStatus.new_order.name(), PintuanOrderStatus.wait.name(), promotionId);
            for (PintuanOrder order : orderList) {
                pintuanOrderManager.handle(order.getOrderId());
            }


        }
        pintuanGoodsManager.delIndex(promotionId);
    }

    /**
     * Manually stop an activity
     *
     * @param promotionId
     */
    @Override
    public void manualClosePromotion(Integer promotionId) {
        if (check(promotionId, 0)) {
            this.closePromotion(promotionId);
        } else {
            throw new ServiceException(PintuanErrorCode.E5012.code(), PintuanErrorCode.E5012.describe());
        }
    }

    /**
     * Start an activity manually
     *
     * @param promotionId
     */
    @Override
    public void manualOpenPromotion(Integer promotionId) {
        if (check(promotionId, 1)) {
            this.openPromotion(promotionId);
        } else {
            throw new ServiceException(PintuanErrorCode.E5012.code(), PintuanErrorCode.E5012.describe());
        }
    }

    /**
     * Verify whether the operation can be performed manually
     *
     * @param promotionId Spell groupid
     * @param type        1Open test0End of test
     * @return
     */
    private boolean check(Integer promotionId, Integer type) {


        Pintuan pintuan = this.getModel(promotionId);
        if (AdminUserContext.getAdmin() == null) {
            throw new ServiceException(PintuanErrorCode.E5013.code(), PintuanErrorCode.E5013.describe());
        }

        // Time segment is not correct, do not operate
        if (pintuan.getStartTime() > DateUtil.getDateline() || pintuan.getEndTime() < DateUtil.getDateline()) {
            return false;
        }
        // open
        if (type == 1) {
            // If the activity has ended, the operation can be started
            return pintuan.getStatus().equals(PromotionStatusEnum.END.name());
        } else {
            // You can stop an activity if it is in progress
            return pintuan.getStatus().equals(PromotionStatusEnum.UNDERWAY.name());
        }
    }

    /**
     * Validate parameter
     *
     * @param startTime Activity start time
     * @param endTime   End time
     */
    private void verifyParam(long startTime, long endTime) {

        // The start time cannot be later than the end time
        if (startTime > endTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "The start time cannot be later than the end time");
        }

    }

}
