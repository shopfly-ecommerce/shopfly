/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.promotion;

import com.enation.app.javashop.core.promotion.PromotionErrorCode;
import com.enation.app.javashop.core.promotion.seckill.model.dto.SeckillQueryParam;
import com.enation.app.javashop.core.promotion.seckill.model.vo.TimeLineVO;
import com.enation.app.javashop.core.promotion.seckill.service.SeckillGoodsManager;
import com.enation.app.javashop.core.promotion.seckill.service.SeckillRangeManager;
import com.enation.app.javashop.framework.database.Page;
import com.enation.app.javashop.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 限时抢购相关API
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/seckill")
@Api(description = "限时抢购相关API")
@Validated
public class SeckillBuyerController {

    @Autowired
    private SeckillGoodsManager seckillApplyManager;

    @Autowired
    private SeckillRangeManager seckillRangeManager;

    @ApiOperation(value = "读取秒杀时刻")
    @ResponseBody
    @GetMapping(value = "/time-line")
    public List<TimeLineVO> readTimeLine() {
        List<TimeLineVO> timeLineVOList = this.seckillRangeManager.readTimeList();
        return timeLineVOList;
    }


    @ApiOperation(value = "根据参数读取限时抢购的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "range_time", value = "时刻", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query")
    })
    @GetMapping("/goods-list")
    public Page goodsList(@ApiIgnore Integer rangeTime, @ApiIgnore Integer pageSize, @ApiIgnore Integer pageNo) {

        if (rangeTime == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "时刻不能为空");
        }

        if (rangeTime > 24) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "时刻必须是0~24的整数");
        }

        SeckillQueryParam param = new SeckillQueryParam();
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        List list = this.seckillApplyManager.getSeckillGoodsList(rangeTime, pageNo, pageSize);
        long dataTotal = 0;
        if (list != null && !list.isEmpty()) {
            dataTotal = list.size();
        }

        Page page = new Page();
        page.setData(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setDataTotal(dataTotal);
        return page;

    }


}
