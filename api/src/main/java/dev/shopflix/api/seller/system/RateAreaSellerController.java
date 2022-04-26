/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.system;

import dev.shopflix.core.system.model.dos.RateAreaDO;
import dev.shopflix.core.system.model.dos.ShipTemplateDO;
import dev.shopflix.core.system.model.vo.RateAreaVO;
import dev.shopflix.core.system.model.vo.ShipTemplateSellerVO;
import dev.shopflix.core.system.model.vo.ShipTemplateVO;
import dev.shopflix.core.system.service.RateAreaManager;
import dev.shopflix.core.system.service.ShipTemplateManager;
import dev.shopflix.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
@RequestMapping("/seller/shops/rate-area")
@Api(description = "区域相关API")
@Validated
public class RateAreaSellerController {

    @Autowired
    private RateAreaManager rateAreaManager;


    @ApiOperation(value = "查询区域列表", response = RateAreaDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "每页显示数量", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", value = "区域名称", required = true, dataType = "String", paramType = "query")
    })
    @GetMapping
    public Page list(@ApiIgnore @NotNull(message = "页码不能为空") Integer pageNo, @ApiIgnore @NotNull(message = "每页数量不能为空") Integer pageSize, String name){
        return this.rateAreaManager.list(name,pageNo,pageSize);
    }


    @ApiOperation(value = "添加区域", response = RateAreaDO.class)
    @PostMapping
    public void add(@Valid @RequestBody RateAreaVO rateAreaVO) {

        this.rateAreaManager.add(rateAreaVO);
    }

    @PutMapping(value = "/{rate_area_id}")
    @ApiOperation(value = "修改区域", response = RateAreaDO.class)
    @ApiImplicitParam(name = "rate_area_id", value = "区域id", required = true, dataType = "int", paramType = "path")
    public void edit(@Valid @RequestBody RateAreaVO rateAreaVO, @ApiIgnore @PathVariable("rate_area_id") Integer rateAreaId) {
        rateAreaVO.setId(rateAreaId);
        this.rateAreaManager.edit(rateAreaVO);
    }


    @DeleteMapping(value = "/{rate_area_id}")
    @ApiOperation(value = "删除区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rate_area_id", value = "要删除的区域主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@ApiIgnore @PathVariable("rate_area_id") Integer rateAreaId) {
        this.rateAreaManager.delete(rateAreaId);
        return null;
    }

    @GetMapping(value = "/{rate_area_id}")
    @ApiOperation(value = "查询一个区域")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "rate_area_id", value = "要查询的区域主键", required = true, dataType = "int", paramType = "path")
    })
    public RateAreaVO get(@ApiIgnore @PathVariable("rate_area_id") Integer rateAreaId) {

        RateAreaVO rateAreaVO = this.rateAreaManager.getFromDB(rateAreaId);

        return rateAreaVO;
    }

}
