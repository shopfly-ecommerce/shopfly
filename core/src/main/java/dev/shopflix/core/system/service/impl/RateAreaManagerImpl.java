/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import dev.shopflix.core.base.CachePrefix;
import dev.shopflix.core.client.system.RegionsClient;
import dev.shopflix.core.member.model.vo.RegionVO;
import dev.shopflix.core.system.model.dos.RateAreaDO;
import dev.shopflix.core.system.model.vo.AreaVO;
import dev.shopflix.core.system.model.vo.RateAreaVO;
import dev.shopflix.core.system.service.RateAreaManager;
import dev.shopflix.framework.cache.Cache;
import dev.shopflix.framework.database.DaoSupport;
import dev.shopflix.framework.database.Page;
import dev.shopflix.framework.util.DateUtil;
import dev.shopflix.framework.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 区域服务
 * @author cs
 * @version v7.0.0
 * @since v7.0.0
 * 2018-03-28 21:44:49
 */
@Service
public class RateAreaManagerImpl implements RateAreaManager {

    @Autowired
    private DaoSupport daoSupport;


    @Override
    public Page list(String name, Integer pageNo, Integer pageSize) {

        StringBuffer sqlBuffer = new StringBuffer("select id,name,create_time from es_rate_area  ");
        List<Object> term = new ArrayList<>();
        if (!StringUtil.isEmpty(name)){
            sqlBuffer.append(" where name like ? ");
            term.add("%"+name+"%");
        }
        sqlBuffer.append(" order by create_time desc");
        return daoSupport.queryForPage(sqlBuffer.toString(),pageNo,pageSize, RateAreaDO.class,term.toArray());
    }

    @Override
    public RateAreaDO add(RateAreaVO rateAreaVO) {

        RateAreaDO rateAreaDO = new RateAreaDO();
        rateAreaDO.setName(rateAreaVO.getName());
        rateAreaDO.setCreateTime(DateUtil.getDateline());

        //获取地区id
        List<AreaVO> areas = rateAreaVO.getAreas();
        rateAreaDO.setAreaJson(JSON.toJSONString(areas));

        StringBuffer areaIdBuffer = new StringBuffer(",");
        StringBuffer areaBuffer = new StringBuffer(",");
        for (AreaVO vo:areas) {
            areaIdBuffer.append(vo.getCode()).append(",");
            areaBuffer.append(vo.getName()).append(",");
            if (vo.getChildren()!=null&&vo.getChildren().size()>0){
                for (AreaVO child:vo.getChildren()) {
                    areaIdBuffer.append(child.getCode()).append(",");
                    areaBuffer.append(child.getName()).append(",");
                }
            }
        }
        rateAreaDO.setAreaId(areaIdBuffer.toString());
        rateAreaDO.setArea(areaBuffer.toString());
        this.daoSupport.insert(rateAreaDO);
        int lastId = daoSupport.getLastId("es_rate_area");
        rateAreaDO.setId(lastId);
        return rateAreaDO;
    }

    @Override
    public RateAreaDO edit(RateAreaVO rateAreaVO) {
        Integer id = rateAreaVO.getId();
        //删除区域
        this.delete(id);
        return this.add(rateAreaVO);
    }

    @Override
    public void delete(Integer rateAreaId) {
        this.daoSupport.execute("delete from es_rate_area where id = ?", rateAreaId);
    }

    @Override
    public RateAreaVO getFromDB(Integer rateAreaId) {
        RateAreaDO rateAreaDO = daoSupport.queryForObject(RateAreaDO.class, rateAreaId);

        RateAreaVO rateAreaVO = new RateAreaVO(rateAreaDO);


        return rateAreaVO;
    }
}