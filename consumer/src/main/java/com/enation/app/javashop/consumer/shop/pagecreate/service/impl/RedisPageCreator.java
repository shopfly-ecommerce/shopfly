/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.consumer.shop.pagecreate.service.impl;

import com.enation.app.javashop.consumer.shop.pagecreate.service.PageCreator;
import com.enation.app.javashop.core.base.SettingGroup;
import com.enation.app.javashop.core.client.goods.GoodsClient;
import com.enation.app.javashop.core.client.system.SettingClient;
import com.enation.app.javashop.core.client.system.StaticsPageHelpClient;
import com.enation.app.javashop.core.pagecreate.model.PageCreatePrefixEnum;
import com.enation.app.javashop.core.system.model.TaskProgressConstant;
import com.enation.app.javashop.core.system.model.enums.ClientType;
import com.enation.app.javashop.core.system.model.vo.PageSetting;
import com.enation.app.javashop.core.system.service.ProgressManager;
import com.enation.app.javashop.framework.logs.Debugger;
import com.enation.app.javashop.framework.logs.Logger;
import com.enation.app.javashop.framework.logs.LoggerFactory;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 静态页向redis生成
 *
 * @author zh
 * @version v1.0
 * @since v6.4.0 2017年8月29日 上午11:45:20
 */
@Component
public class RedisPageCreator implements PageCreator {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProgressManager progressManager;

    @Autowired
    private SettingClient settingClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StaticsPageHelpClient staticsPageHelpClient;

    @Autowired
    private Debugger debugger;


    @Override
    public void createOne(String path, String type, String name) throws Exception {
        //生成消息
        String url = getUrl(path, type);
        //通过http 来获取html存储redis
        String html = this.getHTML(url, type);
        stringRedisTemplate.opsForValue().set(name, html);

    }

    @Override
    public void createAll() throws Exception {
        //生成商品信息页面
        this.createGoods();
        //生成商城首页
        this.createIndex();
        //生成帮助中心页面
        this.createHelp();
    }

    @Override
    public void createIndex() throws Exception {
        debugger.log("开始生成首页静态页");

        try {
            //页面名称
            String pageName = PageCreatePrefixEnum.INDEX.getPrefix();

            //生成静态页
            this.createOne(pageName, ClientType.PC.name(), "/" + ClientType.PC.name() + pageName);
//            this.createOne(pageName, ClientType.WAP.name(), "/" + ClientType.WAP.name() + pageName);
            progressManager.taskUpdate(TaskProgressConstant.PAGE_CREATE, "/");
            debugger.log("首页生成成功");

        } catch (Exception e) {
            debugger.log("首页生成静态页出错，异常如下：", StringUtil.getStackTrace(e));
            logger.error("首页生成静态页出错，异常如下：", e);
        }


    }

    @Override
    public void createGoods() throws Exception {

        //为了防止首页有相关商品，所以需要生成首页一次
        this.createIndex();
        //商品总数
        int goodsCount = goodsClient.queryGoodsCount();

        debugger.log("开始生成商品静态页，", "商品总数为:" + goodsCount);

        //100条查一次
        int pageSize = 100;
        int pageCount = 0;
        pageCount = goodsCount / pageSize;
        pageCount = goodsCount % pageSize > 0 ? pageCount + 1 : pageCount;
        ALL:
        for (int i = 1; i <= pageCount; i++) {

            //100条查一次
            List<Map> goodsList = this.goodsClient.queryGoodsByRange(i, pageSize);
            for (Map goods : goodsList) {
                if (checkProgress(TaskProgressConstant.PAGE_CREATE)){
                    break ALL;
                }
                int goodsId = Integer.valueOf(goods.get("goods_id").toString());
                try {
                    //商品页面名称
                    String pageName = PageCreatePrefixEnum.GOODS.getHandlerGoods(goodsId);
                    //生成静态页
                    this.createOne(pageName, ClientType.PC.name(), "/" + ClientType.PC.name() + pageName);
//                    this.createOne(pageName, ClientType.WAP.name(), "/" + ClientType.WAP.name() + pageName);
                    progressManager.taskUpdate(TaskProgressConstant.PAGE_CREATE, pageName);
                } catch (Exception e) {
                    debugger.log("为商品[" + goods.get("goods_name") + "],id[" + goodsId + "]生成静态页出错，异常如下：", StringUtil.getStackTrace(e));
                    logger.error("为商品[" + goods.get("goods_name") + "],id[" + goodsId + "]生成静态页出错，异常如下：", e);
                }


            }
        }

        debugger.log("生成完成");

    }

    private boolean checkProgress(String id) {
        if(progressManager.getProgress(id) == null ){
            debugger.log("停止生成静态页");
            return true;
        }
        return false;
    }


    @Override
    public void createHelp() throws Exception {
        //帮助中心页面
        int helpCount = staticsPageHelpClient.count();

        debugger.log("开始生成帮助页，", "总数为:" + helpCount);

        // 100条查一次
        int pageSize = 100;
        int pageCount = 0;
        pageCount = helpCount / pageSize;
        pageCount = helpCount % pageSize > 0 ? pageCount + 1 : pageCount;
        ALL:
        for (int i = 1; i <= pageCount; i++) {

            //获取数据
            List<Map> list = this.staticsPageHelpClient.helpList(i, pageSize);
            for (Map map : list) {
                if (checkProgress(TaskProgressConstant.PAGE_CREATE)){
                    break ALL;
                }
                int articleId = StringUtil.toInt(map.get("id"), false);
                String pageName = (PageCreatePrefixEnum.HELP.getHandlerHelp(articleId));
                try {
                    //生成静态页
                    String name = "/" + ClientType.PC.name() + pageName;
                    this.createOne(pageName, ClientType.PC.name(), name);
                    progressManager.taskUpdate(TaskProgressConstant.PAGE_CREATE, "正在生成[" + pageName + "]");
                } catch (Exception e) {
                    debugger.log("帮助[" + pageName + "]生成静态页出错，异常如下：", StringUtil.getStackTrace(e));
                    logger.error("为商品[" + pageName + "]生成静态页出错", e);
                }


            }
        }

        debugger.log("生成完成");

    }

    @Override
    public void deleteGoods(String name) throws Exception {

        this.stringRedisTemplate.delete(name);

    }

    /**
     * 传入url 返回对应页面的html
     *
     * @param url  页面的url
     * @param type 客户端类型
     * @return 返回对应页面的html
     * @throws ClientProtocolException
     * @throws IOException
     */
    private String getHTML(String url, String type) throws IOException {

        String html = "";

        int i = 0;

        html = getHTML(url, type, i);


        return html;
    }

    private String getHTML(String url, String type, int times) throws IOException {
        String html;
        // socket超时  connect超时
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000)
                .setConnectTimeout(50000)
                .build();
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Client-Type", type);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        int status = response.getStatusLine().getStatusCode();

        if (status != 200) {
            html = EntityUtils.toString(response.getEntity(), "utf-8");

            debugger.log("生成静态页出错：静态页渲染服务返回" + status, "uri:" + url);
            debugger.log(html);
            if (times >= 3) {
                return ("create error ,return code is :" + status + ",url is :" + url);
            } else {
                times++;
                debugger.log(times + "次重试...");
                html = getHTML(url, type, times);

                return html;
            }

        } else {

            if (times > 0) {
                debugger.log("重试成功");
            }
            html = EntityUtils.toString(response.getEntity(), "utf-8");
        }

        return html;
    }


    public static void main(String[] args) throws IOException {
        for (int i=0;i<10;i++){
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(50000)
                    .setConnectTimeout(50000)
                    .build();
            CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
            HttpGet httpGet = new HttpGet("http://192.168.2.103:3000/goods/261");
            httpGet.setHeader("Client-Type", ClientType.PC.name());
            CloseableHttpResponse response = httpClient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            System.out.println(status);
            if (status != 200) {
                String html = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(html);
            }
        }


    }

    /**
     * 获取url
     *
     * @param path 路径
     * @param type 客户端类型
     * @return url
     */
    private String getUrl(String path, String type) {
        String pageSettingJson = settingClient.get(SettingGroup.PAGE);
        PageSetting pageSetting = JsonUtil.jsonToObject(pageSettingJson, PageSetting.class);

        String staticPageAddress = pageSetting.getStaticPageAddress();

        if (type.equals(ClientType.WAP.name())) {
            staticPageAddress = pageSetting.getStaticPageWapAddress();
        }

        if (staticPageAddress.endsWith("/")) {
            staticPageAddress = staticPageAddress.substring(0, staticPageAddress.length() - 1);
        }

        return staticPageAddress + path;
    }

}
