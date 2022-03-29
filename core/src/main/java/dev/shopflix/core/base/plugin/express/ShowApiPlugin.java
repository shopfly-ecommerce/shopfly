/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.express;

import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.plugin.express.util.HttpRequest;
import dev.shopflix.core.system.model.vo.ExpressDetailVO;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import com.show.api.util.ShowApiUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * showapi 快递实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/11 下午3:52
 * @since v7.0
 */
@Component
public class ShowApiPlugin implements ExpressPlatform {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem appidItem = new ConfigItem();
        appidItem.setName("appid");
        appidItem.setText("appid");
        appidItem.setType("text");

        ConfigItem secretItem = new ConfigItem();
        secretItem.setName("app_secret");
        secretItem.setText("密钥");
        secretItem.setType("text");

        list.add(appidItem);
        list.add(secretItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "showApiPlugin";
    }

    @Override
    public String getPluginName() {
        return "showapi快递";
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config) {
        //获取快递平台参数
        String appid = StringUtil.toString(config.get("appid"));
        String appSecret = StringUtil.toString(config.get("app_secret"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new Date());
        String md5Secret = shouquan(abbreviation, num, appid, appSecret, time);
        String url = "http://route.showapi.com/64-19?com=" + abbreviation + "&nu=" + num + "&showapi_appid=" + appid + "&showapi_timestamp=" + time + "&showapi_sign=" + md5Secret;
        try {
            String content = HttpRequest.postData(url, null, "utf-8").toString();
            if ("0".equals(JsonUtil.toMap(content).get("showapi_res_code").toString())) {
                Map map = (Map) JsonUtil.toMap(content).get("showapi_res_body");
                ExpressDetailVO expressDetailVO = new ExpressDetailVO();
                expressDetailVO.setData((List<Map>) map.get("data"));
                expressDetailVO.setName(map.get("expTextName").toString());
                expressDetailVO.setCourierNum(map.get("mailNo").toString());
                return expressDetailVO;
            } else {
                logger.error("快递接口物流查询失败");
            }
        } catch (Exception e) {
            logger.error("物流查询失败" + e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 授权
     *
     * @param com       快递公司简称
     * @param nu        单号
     * @param appid     appid
     * @param appSecret appSecret
     * @param time      时间
     * @return
     */
    private static String shouquan(String com, String nu, String appid, String appSecret, String time) {
        try {
            Map params = new HashMap(16);
            params.put("com", com);
            params.put("nu", nu);
            params.put("showapi_appid", appid);
            params.put("showapi_timestamp", time);

            String code = ShowApiUtils.signRequest(params, appSecret, false);
            return code.toLowerCase();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
