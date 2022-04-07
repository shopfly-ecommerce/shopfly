/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.api.seller.promotion;

import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyCatDO;
import dev.shopflix.core.promotion.groupbuy.model.dos.GroupbuyGoodsDO;
import dev.shopflix.core.promotion.groupbuy.service.GroupbuyCatManager;
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
import java.util.List;

/**
 * 团购分类控制器
 *
 * @author Snow create in 2018/7/12
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/promotion/group-buy-cats")
@Api(description = "团购分类相关API")
@Validated
public class GroupbuyCatSellerController {

    @Autowired
    private GroupbuyCatManager groupbuyCatManager;

    @ApiOperation(value	= "查询团购分类列表", response = GroupbuyGoodsDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "parent_id",	value =	"父分类id", dataType = "int",	paramType =	"path"),
    })
    @GetMapping("/{parent_id}/children")
    public List<GroupbuyCatDO> list(@ApiIgnore @PathVariable("parent_id") Integer parentId)	{
        if(parentId == null){
            parentId = 0;
        }
        List<GroupbuyCatDO> list = this.groupbuyCatManager.getList(parentId);
        return list;
    }

    @ApiOperation(value	= "查询团购分类列表", response = GroupbuyCatDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "page_no",	value =	"页码",	dataType = "int",	paramType =	"query"),
            @ApiImplicitParam(name	= "page_size",	value =	"每页显示数量", dataType = "int",	paramType =	"query")
    })
    @GetMapping
    public Page list(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize)	{
        return	this.groupbuyCatManager.list(pageNo,pageSize);
    }


    @ApiOperation(value	= "添加团购分类", response = GroupbuyCatDO.class)
    @PostMapping
    public GroupbuyCatDO add(@Valid GroupbuyCatDO groupbuyCat)	{

        this.groupbuyCatManager.add(groupbuyCat);

        return	groupbuyCat;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value	= "修改团购分类", response = GroupbuyCatDO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"主键",	required = true, dataType = "int",	paramType =	"path")
    })
    public	GroupbuyCatDO edit(@Valid GroupbuyCatDO groupbuyCat, @PathVariable Integer id) {

        this.groupbuyCatManager.edit(groupbuyCat,id);

        return	groupbuyCat;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value	= "删除团购分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"要删除的团购分类主键",	required = true, dataType = "int",	paramType =	"path")
    })
    public	String	delete(@PathVariable Integer id) {
        this.groupbuyCatManager.delete(id);
        return "";
    }


    @GetMapping(value =	"/{id}")
    @ApiOperation(value	= "查询一个团购分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",	value = "要查询的团购分类主键",	required = true, dataType = "int",	paramType = "path")
    })
    public	GroupbuyCatDO get(@PathVariable	Integer	id)	{
        GroupbuyCatDO groupbuyCat = this.groupbuyCatManager.getModel(id);
        return	groupbuyCat;
    }

}