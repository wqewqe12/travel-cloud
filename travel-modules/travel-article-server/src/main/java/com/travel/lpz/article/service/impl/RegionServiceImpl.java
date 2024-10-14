package com.travel.lpz.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.mapper.RegionMapper;
import com.travel.lpz.article.service.RegionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
public class RegionServiceImpl extends ServiceImpl<RegionMapper, Region> implements RegionService {
    @Override
    public List<Region> findHotList() {
        //查询热门，排序
        return list(
                new QueryWrapper<Region>()
                    .eq("ishot",Region.STATE_HOT)
                    .orderByAsc("seq")
        );
    }
}
