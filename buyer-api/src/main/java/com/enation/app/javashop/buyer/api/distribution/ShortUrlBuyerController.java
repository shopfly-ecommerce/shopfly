/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.buyer.api.distribution;

import com.enation.app.javashop.core.base.CachePrefix;
import com.enation.app.javashop.core.base.model.vo.SuccessMessage;
import com.enation.app.javashop.core.distribution.exception.DistributionErrorCode;
import com.enation.app.javashop.core.distribution.exception.DistributionException;
import com.enation.app.javashop.core.distribution.model.dos.ShortUrlDO;
import com.enation.app.javashop.core.distribution.service.ShortUrlManager;
import com.enation.app.javashop.framework.cache.Cache;
import com.enation.app.javashop.framework.context.UserContext;
import com.enation.app.javashop.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 短链接识别
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/23 上午8:35
 */
@RestController
@RequestMapping("/distribution/su")
@Api(description = "短链接api")
public class ShortUrlBuyerController {


    @Resource
    private ShortUrlManager shortUrlManager;
    @Autowired
    private Cache cache;

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 访问短链接 把会员id加入session中，并跳转页面
     *
     * @return
     */
    @GetMapping(value = "/visit")
    @ApiOperation("访问短链接 把会员id加入session中，并跳转页面")
    @ApiImplicitParam(name = "su", value = "短链接", required = true, paramType = "query", dataType = "String")
    public SuccessMessage visit(String su, @RequestHeader(required = false) String uuid) throws Exception {
        try {
            ShortUrlDO shortUrlDO = shortUrlManager.getLongUrl(su);
            if (shortUrlDO == null) {
                return null;
            }
            if (uuid != null) {
                cache.put(CachePrefix.DISTRIBUTION_UP.getPrefix()+uuid, getMemberId(shortUrlDO.getUrl()));
            }
            return new SuccessMessage(shortUrlDO.getUrl());
        } catch (Exception e) {
            logger.error("短连接验证出错", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());

        }

    }


    /**
     * url中提取member id
     *
     * @param url
     * @return
     */
    private Integer getMemberId(String url) {
        String pattern = "(member_id=)(\\d+)";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(url);
        if (m.find()) {
            return new Integer(m.group(2));
        }
        return 0;
    }


    @ApiOperation("生成短链接， 必须登录")
    @PostMapping(value = "/get-short-url")
    @ApiImplicitParam(name = "goods_id", value = "商品id", required = false, paramType = "query", dataType = "int")
    public SuccessMessage getShortUrl(@ApiIgnore Integer goodsId) {

        Buyer buyer = UserContext.getBuyer();
        // 没登录不能生成短链接
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        try {
            int memberId = buyer.getUid();
            ShortUrlDO shortUrlDO = this.shortUrlManager.createShortUrl(memberId, goodsId);
            SuccessMessage successMessage = new SuccessMessage();
            successMessage.setMessage("/distribution/su/visit?su=" + shortUrlDO.getSu());
            return successMessage;
        } catch (Exception e) {
            logger.error("生成短连接出错", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

}
