/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.base.plugin.express;

import com.enation.app.javashop.core.base.model.vo.ConfigItem;
import com.enation.app.javashop.core.base.model.vo.RadioOption;
import com.enation.app.javashop.core.base.plugin.express.util.HttpRequest;
import com.enation.app.javashop.core.base.plugin.express.util.MD5;
import com.enation.app.javashop.core.client.system.LogiCompanyClient;
import com.enation.app.javashop.core.system.model.vo.ExpressDetailVO;
import com.enation.app.javashop.framework.util.JsonUtil;
import com.enation.app.javashop.framework.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * showapi 快递实现
 *
 * @author zh
 * @version v7.0
 * @date 18/7/11 下午3:52
 * @since v7.0
 */
@Component
public class Kuaidi100Plugin implements ExpressPlatform {

    @Autowired
    private LogiCompanyClient logiCompanyClient;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @Override
    public List<ConfigItem> definitionConfigItem() {
        List<ConfigItem> list = new ArrayList<>();
        ConfigItem codeItem = new ConfigItem();
        codeItem.setName("code");
        codeItem.setText("公司代码");
        codeItem.setType("text");

        ConfigItem secretItem = new ConfigItem();
        secretItem.setName("id");
        secretItem.setText("id");
        secretItem.setType("text");

        ConfigItem typeItem = new ConfigItem();
        typeItem.setName("user");
        typeItem.setText("用户类型");
        typeItem.setType("radio");
        //组织用户类型可选项
        List<RadioOption> options = new ArrayList<>();
        RadioOption radioOption = new RadioOption();
        radioOption.setLabel("普通用户");
        radioOption.setValue(0);
        options.add(radioOption);
        radioOption = new RadioOption();
        radioOption.setLabel("企业用户");
        radioOption.setValue(1);
        options.add(radioOption);
        typeItem.setOptions(options);

        list.add(codeItem);
        list.add(secretItem);
        list.add(typeItem);
        return list;
    }

    @Override
    public String getPluginId() {
        return "kuaidi100Plugin";
    }

    @Override
    public String getPluginName() {
        return "快递100";
    }

    @Override
    public Integer getIsOpen() {
        return 0;
    }

    @Override
    public ExpressDetailVO getExpressDetail(String abbreviation, String num, Map config) {
        String url = "";
        //获取快递平台参数
        Integer user = new Double(StringUtil.toDouble(config.get("user"), false)).intValue();
        String code = StringUtil.toString(config.get("code"));
        String id = StringUtil.toString(config.get("id"));
        HashMap<String, String> parms = new HashMap<String, String>(16);
        //根据不同的用户类型调取不同的查询接口
        if (user.equals(1)) {
            url = "http://poll.kuaidi100.com/poll/query.do";
            String param = "{\"com\":\"" + abbreviation + "\",\"num\":\"" + num + "\"}";
            String sign = MD5.encode(param + id + code);
            parms.put("param", param);
            parms.put("sign", sign);
            parms.put("customer", code);
        } else {
            url = "http://api.kuaidi100.com/api?id=" + id + "&nu=" + num + "&com=" + abbreviation + "&muti=1&order=asc";
        }
        try {
            String content = HttpRequest.postData(url, parms, "utf-8").toString();
            Map map = JsonUtil.toMap(content);
            ExpressDetailVO expressDetailVO = new ExpressDetailVO();
            expressDetailVO.setData((List<Map>) map.get("data"));
            expressDetailVO.setCourierNum(map.get("nu").toString());
            expressDetailVO.setName(logiCompanyClient.getLogiByCode(map.get("com").toString()).getName());
            return expressDetailVO;
        } catch (Exception e) {
            logger.error("快递查询错误" + e);
            e.printStackTrace();
        }
        return null;
    }
}
