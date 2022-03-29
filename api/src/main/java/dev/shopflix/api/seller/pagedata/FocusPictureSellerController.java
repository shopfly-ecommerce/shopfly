/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.pagedata;

import dev.shopflix.core.pagedata.model.FocusPicture;
import dev.shopflix.core.pagedata.service.FocusPictureManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 焦点图控制器
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-05-21 15:23:23
 */
@RestController
@RequestMapping("/seller/focus-pictures")
@Api(description = "焦点图相关API")
public class FocusPictureSellerController {

    @Autowired
    private FocusPictureManager focusPictureManager;


    @ApiOperation(value = "查询焦点图列表", response = FocusPicture.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "client_type", value = "客户端类型APP/WAP/PC", dataType = "string", paramType = "query"),
    })
    @GetMapping
    public List list(@ApiIgnore String clientType) {

        return this.focusPictureManager.list(clientType);
    }


    @ApiOperation(value = "添加焦点图", response = FocusPicture.class)
    @PostMapping
    public FocusPicture add(@Valid FocusPicture cmsFocusPicture) {

        this.focusPictureManager.add(cmsFocusPicture);

        return cmsFocusPicture;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value = "修改焦点图", response = FocusPicture.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "int", paramType = "path")
    })
    public FocusPicture edit(@Valid FocusPicture cmsFocusPicture, @PathVariable Integer id) {

        this.focusPictureManager.edit(cmsFocusPicture, id);

        return cmsFocusPicture;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "删除焦点图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要删除的焦点图主键", required = true, dataType = "int", paramType = "path")
    })
    public String delete(@PathVariable Integer id) {

        this.focusPictureManager.delete(id);

        return "";
    }


    @GetMapping(value = "/{id}")
    @ApiOperation(value = "查询一个焦点图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的焦点图主键", required = true, dataType = "int", paramType = "path")
    })
    public FocusPicture get(@PathVariable Integer id) {

        FocusPicture cmsFocusPicture = this.focusPictureManager.getModel(id);

        return cmsFocusPicture;
    }

}
