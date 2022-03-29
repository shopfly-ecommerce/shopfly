/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.member;

import com.enation.app.javashop.core.member.model.dos.MemberReceipt;
import com.enation.app.javashop.core.member.model.dos.ReceiptHistory;
import com.enation.app.javashop.core.member.model.enums.ReceiptTypeEnum;
import com.enation.app.javashop.core.member.model.vo.MemberReceiptVO;
import com.enation.app.javashop.core.member.model.vo.OrdinaryReceiptVO;
import com.enation.app.javashop.core.member.service.MemberReceiptManager;
import com.enation.app.javashop.core.member.service.ReceiptHistoryManager;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.exception.NoPermissionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会员发票控制器
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:14
 */
@RestController
@RequestMapping("/members/receipt")
@Api(description = "会员发票相关API")
public class MemberReceiptBuyerController {

    @Autowired
    private MemberReceiptManager memberReceiptManager;
    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;


    @ApiOperation(value = "查询当前会员发票列表", response = MemberReceipt.class)
    @GetMapping
    public Map list() {
        Map<String, Object> map = new HashMap<>(16);
        //查询普通发票列表
        List<MemberReceipt> list = this.memberReceiptManager.list(ReceiptTypeEnum.VATORDINARY.name());
        map.put(ReceiptTypeEnum.VATORDINARY.name(), list);
        return map;
    }


    @ApiOperation(value = "添加会员增值税普通发票", response = MemberReceipt.class)
    @PostMapping("ordinary")
    public MemberReceipt add(@Valid OrdinaryReceiptVO ordinaryReceiptVO) {
        MemberReceiptVO memberReceiptVO = new MemberReceiptVO(ordinaryReceiptVO);
        memberReceiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        return this.memberReceiptManager.add(memberReceiptVO);
    }

    @PutMapping(value = "/{id}/ordinary")
    @ApiOperation(value = "修改会员增值税普通发票", response = MemberReceipt.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public MemberReceipt edit(@Valid OrdinaryReceiptVO ordinaryReceiptVO, @PathVariable Integer id) {
        MemberReceiptVO memberReceiptVO = new MemberReceiptVO(ordinaryReceiptVO);
        memberReceiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        return this.memberReceiptManager.edit(memberReceiptVO, id);

    }

    @PutMapping(value = "/{id}/default")
    @ApiOperation(value = "设置会员发票为默认", response = MemberReceipt.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员发票主键,如果选择个人则设置此参数为0", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "receipt_type", value = "枚举，ELECTRO:电子普通发票，VATORDINARY：增值税普通发票，VATOSPECIAL：增值税专用发票", required = true, dataType = "String", paramType = "query", allowableValues = "ELECTRO,VATORDINARY,VATOSPECIAL")
    })
    public void setDefault(@PathVariable Integer id, @ApiIgnore @NotEmpty(message = "发票类型不能为空") String receiptType) {
        this.memberReceiptManager.setDefaultReceipt(receiptType, id);

    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除会员发票")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的会员发票主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.memberReceiptManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{order_sn}")
    @ApiOperation(value = "根据订单sn查询订单发票信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单sn", required = true, dataType = "String", paramType = "path")
    })
    public ReceiptHistory getReceiptByOrderSn(@PathVariable("order_sn") String orderSn) {
        ReceiptHistory receiptHistory = this.receiptHistoryManager.getReceiptHistory(orderSn);
        if (receiptHistory != null && receiptHistory.getMemberId().equals(UserContext.getBuyer().getUid())) {
            return receiptHistory;
        }
        throw new NoPermissionException("无权限");

    }
}