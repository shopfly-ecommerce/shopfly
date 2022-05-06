/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.api.seller.system;

import cloud.shopfly.b2c.core.system.model.dos.ShipTemplateDO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateSellerVO;
import cloud.shopfly.b2c.core.system.model.vo.ShipTemplateVO;
import cloud.shopfly.b2c.core.system.service.ShipTemplateManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 运费模版控制器
 *
 * @author zjp
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
@RestController
@RequestMapping("/seller/shops/ship-templates")
@Api(description = "运费模版相关API")
@Validated
public class ShipTemplateSellerController {

    @Autowired
    private ShipTemplateManager shipTemplateManager;


    @ApiOperation(value = "查询运费模版列表", response = ShipTemplateVO.class)
    @GetMapping
    public List<ShipTemplateSellerVO> list() {
        return this.shipTemplateManager.getStoreTemplate();
    }


    @ApiOperation(value = "添加运费模版", response = ShipTemplateDO.class)
    @PostMapping
    public ShipTemplateDO add(@Valid @RequestBody ShipTemplateSellerVO shipTemplate) {

        return this.shipTemplateManager.save(shipTemplate);
    }

    @PutMapping(value = "/{template_id}")
    @ApiOperation(value = "修改运费模版", response = ShipTemplateDO.class)
    @ApiImplicitParam(name = "template_id", value = "模版id", required = true, dataType = "int", paramType = "path")
    public ShipTemplateDO edit(@Valid @RequestBody ShipTemplateSellerVO shipTemplate, @ApiIgnore @PathVariable("template_id") Integer templateId) {
        shipTemplate.setId(templateId);
        return this.shipTemplateManager.edit(shipTemplate);
    }


    @DeleteMapping(value = "/{template_id}")
    @ApiOperation(value = "删除运费模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "template_id", value = "要删除的运费模版主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@ApiIgnore @PathVariable("template_id") Integer templateId) {
        this.shipTemplateManager.delete(templateId);
        return null;
    }

    @GetMapping(value = "/{template_id}")
    @ApiOperation(value = "查询一个运费模版")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "template_id", value = "要查询的运费模版主键", required = true, dataType = "int", paramType = "path")
    })
    public ShipTemplateSellerVO get(@ApiIgnore @PathVariable("template_id") Integer templateId) {

        ShipTemplateSellerVO shipTemplate = this.shipTemplateManager.getFromDB(templateId);

        return shipTemplate;
    }

}
