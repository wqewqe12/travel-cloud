package com.travel.lpz.article.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.mapper.StrategyCatalogMapper;
import com.travel.lpz.article.service.StrategyCatalogService;
import com.travel.lpz.article.vo.StrategyCatalogGroup;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
public class StrategyCatalogServiceImpl extends ServiceImpl<StrategyCatalogMapper, StrategyCatalog> implements StrategyCatalogService {
    @Override
    public List<StrategyCatalogGroup> findGroupList() {
        return getBaseMapper().selectGroupList();
    }

}
