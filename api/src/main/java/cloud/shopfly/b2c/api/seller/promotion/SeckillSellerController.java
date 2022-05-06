/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.seckill.model.dos.SeckillDO;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillDTO;
import cloud.shopfly.b2c.core.promotion.seckill.model.enums.SeckillStatusEnum;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.SeckillVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.exception.SystemErrorCodeV1;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 限时抢购活动控制器
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-04-02 17:30:23
 */
@RestController
@RequestMapping("/seller/promotion/seckills")
@Api(description = "限时抢购活动相关API")
@Validated
public class SeckillSellerController {

    @Autowired
    private SeckillManager seckillManager;


    @ApiOperation(value = "查询限时抢购列表", response = SeckillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keywords", value = "关键字", dataType = "int", paramType = "query")
    })
    @GetMapping
    public Page<SeckillVO> list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize,String keywords) {

        return this.seckillManager.list(pageNo, pageSize, keywords);
    }


    @ApiOperation(value = "添加限时抢购入库", response = SeckillVO.class)
    @PostMapping
    public SeckillDTO add(@Valid @RequestBody SeckillDTO seckill) {
        this.verifyParam(seckill);
        seckill.setSeckillStatus(SeckillStatusEnum.EDITING.name());
        this.seckillManager.add(seckill);
        return seckill;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改限时抢购入库", response = SeckillDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO edit(@Valid @RequestBody SeckillDTO seckill, @PathVariable @NotNull(message = "限时抢购ID参数错误") Integer id) {
        this.verifyParam(seckill);
        this.seckillManager.edit(seckill, id);
        return seckill;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除限时抢购入库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的限时抢购入库主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.seckillManager.delete(id);

        return "";
    }

    @DeleteMapping(value = "/{id}/close")
    @ApiOperation(value = "关闭限时抢购")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要关闭的限时抢购入库主键", required = true, dataType = "int", paramType = "path")
    })
    public String close(@PathVariable Integer id) {

        this.seckillManager.close(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个限时抢购入库")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的限时抢购入库主键", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO get(@PathVariable Integer id) {
        SeckillDTO seckillVO = this.seckillManager.getModelAndRange(id);
        return seckillVO;
    }

    @GetMapping(value = "/{id}/seckill-applys")
    @ApiOperation(value = "查询一个限时抢购入库的已经申请的商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的限时抢购入库主键", required = true, dataType = "int", paramType = "path")
    })
    public SeckillVO getApply(@PathVariable Integer id) {
        SeckillVO seckillVO = this.seckillManager.getModelAndApplys(id);
        return seckillVO;
    }


    @ApiOperation(value = "发布限时抢购活动")
    @PostMapping("/{seckill_id}/release")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "seckill_id", value = "要查询的限时抢购入库主键", required = true, dataType = "int", paramType = "path")
    })
    public SeckillDTO publish(@Valid @RequestBody SeckillDTO seckill, @ApiIgnore @PathVariable("seckill_id") Integer seckillId) {

        this.verifyParam(seckill);
        //发布状态
        seckill.setSeckillStatus(SeckillStatusEnum.RELEASE.name());
        if (seckillId == null || seckillId == 0) {
            seckillManager.add(seckill);
        } else {
            seckillManager.edit(seckill, seckillId);
        }

        return seckill;
    }

    /**
     * 验证参数
     *
     * @param seckillVO
     */
    private void verifyParam(SeckillDTO seckillVO) {
        //获取活动开始时间
        long startDay = seckillVO.getStartDay();

        //获取活动开始当天0点的时间
        String startDate = DateUtil.toString(startDay, "yyyy-MM-dd");
        long startTime = DateUtil.getDateline(startDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        //获取报名截止时间
        long applyTime = seckillVO.getApplyEndTime();

        //获取当前时间
        long currentTime = DateUtil.getDateline();

        //获取当天开始时间
        String currentDay = DateUtil.toString(currentTime, "yyyy-MM-dd");
        long currentStartTime = DateUtil.getDateline(currentDay + " 00:00:00", "yyyy-MM-dd HH:mm:ss");

        //活动时间小于当天开始时间
        if (startDay < currentStartTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "活动时间不能小于当前时间");
        }
        //报名截止时间小于当前时间
        if (applyTime < currentTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "报名截止时间不能小于当前时间");
        }
        //报名截止时间大于活动开始当天的起始时间
        if (applyTime > startTime) {
            throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "报名截止时间不能大于活动开始时间");
        }

        List<Integer> termList = new ArrayList<>();
        for (Integer time : seckillVO.getRangeList()) {
            if (termList.contains(time)) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "抢购区间的值不能重复");
            }
            //抢购区间的值不在0到23范围内
            if (time < 0 || time > 23) {
                throw new ServiceException(SystemErrorCodeV1.INVALID_REQUEST_PARAMETER, "抢购区间必须在0点到23点的整点时刻");
            }
            termList.add(time);
        }
    }

}
