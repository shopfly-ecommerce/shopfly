/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.base.plugin.express;

import com.alibaba.fastjson.JSON;
import com.easypost.EasyPost;
import com.easypost.exception.EasyPostException;
import com.easypost.model.Tracker;
import dev.shopflix.core.base.model.vo.ConfigItem;
import dev.shopflix.core.base.model.vo.RadioOption;
import dev.shopflix.core.base.plugin.express.util.HttpRequest;
import dev.shopflix.core.base.plugin.express.util.MD5;
import dev.shopflix.core.client.system.LogiCompanyClient;
import dev.shopflix.core.system.model.vo.ExpressDetailVO;
import dev.shopflix.framework.util.JsonUtil;
import dev.shopflix.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EasyPost 快递实现
 *
 * @author cs
 * @version v7.2.3
 * @date 22/4/26 下午3:52
 * @since v7.0
 */
@Component
public class EasyPostPlugin implements ExpressPlatform {

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem keyItem = new ConfigItem();
        keyItem.setName("apikey");
        keyItem.setText("apikey");
        keyItem.setType("text");
        list.add(keyItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "EasyPostPlugin";
    }

    @Override
    public String getPluginName() {
        return "EasyPost";
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config) {
        EasyPost.apiKey = config.get("apikey").toString();
        Map<String, Object> trackerMap = new HashMap();
        trackerMap.put("tracking_code",num);
        trackerMap.put("carrier", abbreviation);

        try {
            Tracker tracker = Tracker.create(trackerMap);
            ExpressDetailVO expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setData(tracker.getTrackingDetails());
            expressDetailVO.setCourierNum(tracker.getTrackingCode());
            expressDetailVO.setName(tracker.getCarrier());
            return expressDetailVO;
        } catch (EasyPostException e) {
            e.printStackTrace();
            return null;
        }
    }
}
