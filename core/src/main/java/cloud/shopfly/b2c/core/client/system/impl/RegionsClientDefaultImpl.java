/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.system.impl;

import cloud.shopfly.b2c.core.client.system.RegionsClient;
import cloud.shopfly.b2c.core.member.model.vo.RegionVO;
import cloud.shopfly.b2c.core.system.model.dos.Regions;
import cloud.shopfly.b2c.core.system.service.RegionsManager;
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
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
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
