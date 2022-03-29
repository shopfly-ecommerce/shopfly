/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.client.system.impl;

import com.enation.app.javashop.core.client.system.RegionsClient;
import com.enation.app.javashop.core.member.model.vo.RegionVO;
import com.enation.app.javashop.core.system.model.dos.Regions;
import com.enation.app.javashop.core.system.service.RegionsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version v7.0
 * @Description: 地区Client默认实现
 * @Author: zjp
 * @Date: 2018/7/27 11:18
 */
@Service
@ConditionalOnProperty(value = "javashop.product", havingValue = "stand")
public class RegionsClientDefaultImpl implements RegionsClient {

    @Autowired
    private RegionsManager regionsManager;

    @Override
    public List<Regions> getRegionsChildren(Integer regionId) {
        return regionsManager.getRegionsChildren(regionId);
    }

    @Override
    public Regions getModel(Integer id) {
        return regionsManager.getModel(id);
    }


    @Override
    public List<RegionVO> getRegionByDepth(Integer depth) {
        return regionsManager.getRegionByDepth(depth);
    }
}
