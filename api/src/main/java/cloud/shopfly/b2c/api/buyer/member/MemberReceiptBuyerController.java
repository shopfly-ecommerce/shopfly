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
package cloud.shopfly.b2c.api.buyer.member;

import cloud.shopfly.b2c.core.member.model.dos.MemberReceipt;
import cloud.shopfly.b2c.core.member.model.dos.ReceiptHistory;
import cloud.shopfly.b2c.core.member.model.enums.ReceiptTypeEnum;
import cloud.shopfly.b2c.core.member.model.vo.MemberReceiptVO;
import cloud.shopfly.b2c.core.member.model.vo.OrdinaryReceiptVO;
import cloud.shopfly.b2c.core.member.service.MemberReceiptManager;
import cloud.shopfly.b2c.core.member.service.ReceiptHistoryManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
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
 * Member invoice controller
 *
 * @author zh
 * @version v7.0.0
 * @since v7.0.0
 * 2018-07-20 20:48:14
 */
@RestController
@RequestMapping("/members/receipt")
@Api(description = "Member invoice correlationAPI")
public class MemberReceiptBuyerController {

    @Autowired
    private MemberReceiptManager memberReceiptManager;
    @Autowired
    private ReceiptHistoryManager receiptHistoryManager;


    @ApiOperation(value = "Query the list of current member invoices", response = MemberReceipt.class)
    @GetMapping
    public Map list() {
        Map<String, Object> map = new HashMap<>(16);
        // Query the list of general invoices
        List<MemberReceipt> list = this.memberReceiptManager.list(ReceiptTypeEnum.VATORDINARY.name());
        map.put(ReceiptTypeEnum.VATORDINARY.name(), list);
        return map;
    }


    @ApiOperation(value = "Add member VAT general invoice", response = MemberReceipt.class)
    @PostMapping("ordinary")
    public MemberReceipt add(@Valid OrdinaryReceiptVO ordinaryReceiptVO) {
        MemberReceiptVO memberReceiptVO = new MemberReceiptVO(ordinaryReceiptVO);
        memberReceiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        return this.memberReceiptManager.add(memberReceiptVO);
    }

    @PutMapping(value = "/{id}/ordinary")
    @ApiOperation(value = "Revise general VAT invoice of member", response = MemberReceipt.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "A primary key", required = true, dataType = "int", paramType = "path")
    })
    public MemberReceipt edit(@Valid OrdinaryReceiptVO ordinaryReceiptVO, @PathVariable Integer id) {
        MemberReceiptVO memberReceiptVO = new MemberReceiptVO(ordinaryReceiptVO);
        memberReceiptVO.setReceiptType(ReceiptTypeEnum.VATORDINARY.name());
        return this.memberReceiptManager.edit(memberReceiptVO, id);

    }

    @PutMapping(value = "/{id}/default")
    @ApiOperation(value = "Set membership invoice as default", response = MemberReceipt.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Member invoice master key,Set this parameter to if individuals are selected0", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "receipt_type", value = "Enumeration,ELECTRO:Electronic general invoice,VATORDINARY：VAT general invoice,VATOSPECIAL：VAT special invoice", required = true, dataType = "String", paramType = "query", allowableValues = "ELECTRO,VATORDINARY,VATOSPECIAL")
    })
    public void setDefault(@PathVariable Integer id, @ApiIgnore @NotEmpty(message = "The invoice type cannot be empty") String receiptType) {
        this.memberReceiptManager.setDefaultReceipt(receiptType, id);

    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete member invoice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "Member invoice primary key to delete", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.memberReceiptManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{order_sn}")
    @ApiOperation(value = "According to the ordersnQuery the invoice information of an order")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "The ordersn", required = true, dataType = "String", paramType = "path")
    })
    public ReceiptHistory getReceiptByOrderSn(@PathVariable("order_sn") String orderSn) {
        ReceiptHistory receiptHistory = this.receiptHistoryManager.getReceiptHistory(orderSn);
        if (receiptHistory != null && receiptHistory.getMemberId().equals(UserContext.getBuyer().getUid())) {
            return receiptHistory;
        }
        throw new NoPermissionException("Without permission");

    }
}
