/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system.impl;

import com.enation.app.javashop.core.base.service.SensitiveWordsManager;
import com.enation.app.javashop.core.client.system.SensitiveWordsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fk
 * @version v2.0
 * @Description: 敏感词
 * @date 2018/8/10 15:30
 * @since v7.0.0
 */
@Service
@ConditionalOnProperty(value="javashop.product", havingValue="stand")
public class SensitiveWordsClientDefaultImpl implements SensitiveWordsClient {

    @Autowired
    private SensitiveWordsManager sensitiveWordsManager;

    @Override
    public List<String> listWords() {

        return sensitiveWordsManager.listWords();
    }
}
