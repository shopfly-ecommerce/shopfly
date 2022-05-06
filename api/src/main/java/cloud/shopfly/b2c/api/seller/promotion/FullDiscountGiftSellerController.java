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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.fulldiscount.model.dos.FullDiscountGiftDO;
import cloud.shopfly.b2c.core.promotion.fulldiscount.service.FullDiscountGiftManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.NoPermissionException;
import cloud.shopfly.b2c.framework.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 满优惠赠品控制器
 *
 * @author Snow
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-30 17:34:46
 */
@RestController
@RequestMapping("/seller/promotion/full-discount-gifts")
@Api(description = "满优惠赠品相关API")
@Validated
public class FullDiscountGiftSellerController {

    @Autowired
    private FullDiscountGiftManager fullDiscountGiftManager;


    @ApiOperation(value = "查询满优惠赠品列表", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "keyword", value = "关键字查询", dataType = "String", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, String keyword) {

        return this.fullDiscountGiftManager.list(pageNo, pageSize, keyword);
    }


    @ApiOperation(value = "添加满优惠赠品", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gift_name", value = "赠品名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_price", value = "赠品金额", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "gift_img", value = "赠品图片", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "actual_store", value = "库存", required = true, dataType = "int", paramType = "query"),
    })
    @PostMapping
    public FullDiscountGiftDO add(@ApiIgnore @NotEmpty(message = "请填写赠品名称") String giftName,
                                  @ApiIgnore @NotNull(message = "请填写赠品金额") Double giftPrice,
                                  @ApiIgnore @NotEmpty(message = "请上传赠品图片") String giftImg,
                                  @ApiIgnore @NotNull(message = "请填写库存") Integer actualStore) {

        FullDiscountGiftDO giftDO = new FullDiscountGiftDO();
        giftDO.setGiftName(giftName);
        giftDO.setGiftPrice(giftPrice);
        giftDO.setGiftImg(giftImg);
        giftDO.setActualStore(actualStore);

        //设置赠品可用库存(添加时可用库存=实际库存)
        giftDO.setEnableStore(actualStore);

        //添加赠品创建时间
        giftDO.setCreateTime(DateUtil.getDateline());

        //设置赠品类型(1.0版本赠品类型默认为0：普通赠品)
        giftDO.setGiftType(0);

        //默认不禁用
        giftDO.setDisabled(0);

        this.fullDiscountGiftManager.add(giftDO);

        return giftDO;
    }

    @PutMapping(value = "/{gift_id}")
    @ApiOperation(value = "修改满优惠赠品", response = FullDiscountGiftDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gift_id", value = "赠品id", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "gift_name", value = "赠品名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_name", value = "赠品名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "gift_price", value = "赠品金额", required = true, dataType = "double", paramType = "query"),
            @ApiImplicitParam(name = "gift_img", value = "赠品图片", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "actual_store", value = "库存", required = true, dataType = "int", paramType = "query"),
    })
    public FullDiscountGiftDO edit(@ApiIgnore @PathVariable("gift_id") Integer giftId,
                                   @ApiIgnore @NotEmpty(message = "请填写赠品名称") String giftName,
                                   @ApiIgnore @NotNull(message = "请填写赠品金额") Double giftPrice,
                                   @ApiIgnore @NotEmpty(message = "请上传赠品图片") String giftImg,
                                   @ApiIgnore @NotNull(message = "请填写库存") Integer actualStore) {

        this.fullDiscountGiftManager.verifyAuth(giftId);
        FullDiscountGiftDO giftDO = new FullDiscountGiftDO();
        giftDO.setGiftId(giftId);
        giftDO.setGiftName(giftName);
        giftDO.setGiftPrice(giftPrice);
        giftDO.setGiftImg(giftImg);
        giftDO.setActualStore(actualStore);

        //设置赠品可用库存(添加时可用库存=实际库存)
        giftDO.setEnableStore(actualStore);

        //添加赠品创建时间
        giftDO.setCreateTime(DateUtil.getDateline());

        //设置赠品类型(1.0版本赠品类型默认为0：普通赠品)
        giftDO.setGiftType(0);

        //默认不禁用
        giftDO.setDisabled(0);

        this.fullDiscountGiftManager.edit(giftDO, giftId);

        return giftDO;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除满优惠赠品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的满优惠赠品主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.fullDiscountGiftManager.verifyAuth(id);
        this.fullDiscountGiftManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个满优惠赠品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的满优惠赠品主键", required = true, dataType = "int", paramType = "path")
    })
    public FullDiscountGiftDO get(@PathVariable Integer id) {

        FullDiscountGiftDO fullDiscountGift = this.fullDiscountGiftManager.getModel(id);
        //验证越权操作
        if (fullDiscountGift == null) {
            throw new NoPermissionException("无权操作");
        }
        return fullDiscountGift;
    }

    @ApiOperation(value = "查询满优惠赠品集合", response = FullDiscountGiftDO.class)
    @GetMapping(value = "/all")
    public List<FullDiscountGiftDO> listAll() {
        return this.fullDiscountGiftManager.listAll();
    }

}
