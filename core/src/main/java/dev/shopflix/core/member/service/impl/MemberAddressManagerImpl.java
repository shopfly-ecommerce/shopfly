/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.member.service.impl;

import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.member.MemberErrorCode;
import dev.shopflix.core.member.model.dos.Member;
import dev.shopflix.core.member.model.dos.MemberAddress;
import dev.shopflix.core.member.service.MemberAddressManager;
import dev.shopflix.core.member.service.MemberManager;
import dev.shopflix.core.trade.order.support.CheckoutParamName;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.context.UserContext;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.exception.NoPermissionException;
import dev.shopflix.framework.exception.ResourceNotFoundException;
import dev.shopflix.framework.exception.ServiceException;
import dev.shopflix.framework.security.model.Buyer;
import dev.shopflix.framework.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 会员地址业务类
 *
 * @author zh
 * @version v2.0
 * @since v7.0.0
 * 2018-03-18 15:37:00
 */
@Service
public class MemberAddressManagerImpl implements MemberAddressManager {

    @Autowired
    
    private DaoSupport memberDaoSupport;
    @Autowired
    private MemberManager memberManager;
    @Autowired
    private Cache cache;

    /**
     * Query the collection of user shipping address information
     *
     * @return User delivery address collection
     */
    @Override
    public List<MemberAddress> list() {
        Buyer buer = UserContext.getBuyer();
        String sql = "select * from es_member_address where member_id = ? order by addr_id desc";
        return this.memberDaoSupport.queryForList(sql, MemberAddress.class, buer.getUid());
    }

    /**
     * Query the current user's shipping address paginated list
     *
     * @param page     Number of paginated pages
     * @param pageSize Number of paginated display
     * @param memberId User ID
     * @return User delivery address collection
     */
    @Override
    public Page list(int page, int pageSize, Integer memberId) {
        String sql = "select * from es_member_address  where member_id = ? ";
        Page webPage = this.memberDaoSupport.queryForPage(sql, page, pageSize, MemberAddress.class, memberId);
        return webPage;
    }

    /**
     * Add user shipping address
     *
     * @param memberAddress User shipping address parameter information
     * @return Add the user's shipping address information after successful addition
     */
    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberAddress add(MemberAddress memberAddress) {
        //Get currently logged in user information
        Buyer buyer = UserContext.getBuyer();
        //Verify that the currently logged in user information exists
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null) {
            throw new ResourceNotFoundException("The currently logged in user information does not exist");
        }
        //The number of user shipping addresses cannot exceed 20
        String sql = "select count(*) from es_member_address where member_id = ?";
        Integer count = this.memberDaoSupport.queryForInt(sql, buyer.getUid());
        if (count == 20) {
            throw new ServiceException(MemberErrorCode.E100.code(), "The number of user shipping addresses cannot exceed 20");
        }
        memberAddress.setMemberId(buyer.getUid());
        MemberAddress defAddr = this.getDefaultAddress(buyer.getUid());
        //默认地址的处理
        if (memberAddress.getDefAddr() > 1 || memberAddress.getDefAddr() < 0) {
            memberAddress.setDefAddr(0);
        }
        if (defAddr == null) {
            memberAddress.setDefAddr(1);
        } else {
            //不是第一个，且设置为默认地址了，则更新其它地址为非默认地址
            if (memberAddress.getDefAddr() == 1) {
                this.memberDaoSupport.execute("update es_member_address set def_addr = 0 where member_id = ?", buyer.getUid());
            }
        }
        this.memberDaoSupport.insert(memberAddress);
        int memberAddressId = this.memberDaoSupport.getLastId("es_member_address");
        memberAddress.setAddrId(memberAddressId);
        return memberAddress;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public MemberAddress edit(MemberAddress memberAddress, Integer id) {
        Buyer buyer = UserContext.getBuyer();
        Member member = memberManager.getModel(buyer.getUid());
        if (member == null) {
            throw new ResourceNotFoundException("当前会员不存在");
        }
        //权限判断
        MemberAddress address = this.getModel(id);
        if (address == null || !Objects.equals(address.getMemberId(), buyer.getUid())) {
            throw new NoPermissionException("无权限操作此地址");
        }
        //如果要将默认地址修改为非默认地址
        if (address.getDefAddr() == 1 && memberAddress.getDefAddr() == 0) {
            throw new ServiceException(MemberErrorCode.E101.code(), "无法更改当前默认地址为非默认地址");
        }
        //如果要将非默认地址修改为默认
        if (address.getDefAddr() == 0 && memberAddress.getDefAddr() == 1) {
            this.memberDaoSupport.execute("update es_member_address set def_addr = 0 where member_id = ?", buyer.getUid());
        }
        BeanUtil.copyProperties(memberAddress, address);
        this.memberDaoSupport.update(address, id);
        return address;
    }

    @Override
    @Transactional( propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delete(Integer id) {
        Buyer buyer = UserContext.getBuyer();
        MemberAddress address = this.getModel(id);
        if (address == null || !address.getMemberId().equals(buyer.getUid())) {
            throw new NoPermissionException("无权限操作此地址");
        }
        //默认地址不能删除
        if(address.getDefAddr().equals(1)){
            throw new ServiceException(MemberErrorCode.E101.code(), "默认地址不能删除");
        }
        this.memberDaoSupport.delete(MemberAddress.class, id);
        //会员地址删除后，检测结算参数中的收货地址是否为该地址
        String key = CachePrefix.CHECKOUT_PARAM_ID_PREFIX.getPrefix() + buyer.getUid();
        Map<String, Object> map = cache.getHash(key);

        if(map == null){
            return;
        }
        //如果取到了，则取出来生成param
        Integer addressId = (Integer) map.get("");
        if(id.equals(addressId)){
            //查询默认的收货地址
            MemberAddress defaultAddress = this.getDefaultAddress(buyer.getUid());
            this.cache.putHash(key, CheckoutParamName.ADDRESS_ID, defaultAddress.getAddrId());
        }
    }

    @Override
    public MemberAddress getModel(Integer id) {
        return this.memberDaoSupport.queryForObject(MemberAddress.class, id);
    }

    @Override
    public MemberAddress getDefaultAddress(Integer memberId) {
        String sql = "select * from es_member_address where member_id=? and def_addr=1";
        List<MemberAddress> addressList = this.memberDaoSupport.queryForList(sql, MemberAddress.class, memberId);
        MemberAddress address = null;
        if (!addressList.isEmpty()) {
            address = addressList.get(0);
        }
        return address;
    }

    @Override
    public void editDefault(Integer id) {
        MemberAddress memberAddress = this.getModel(id);
        if (memberAddress == null) {
            throw new NoPermissionException("权限不足");
        }
        Buyer buyer = UserContext.getBuyer();
        if (!buyer.getUid().equals(memberAddress.getMemberId())) {
            throw new NoPermissionException("权限不足");
        }
        this.memberDaoSupport.execute("update es_member_address set def_addr = 0");
        this.memberDaoSupport.execute("update es_member_address set def_addr = 1 where addr_id = ?", id);
    }
}
