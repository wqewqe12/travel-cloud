package com.travel.lpz.article.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.StrategyRank;
import com.travel.lpz.article.mapper.StrategyRankMapper;
import com.travel.lpz.article.service.StrategyRankService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
public class StrategyRankServiceImpl extends ServiceImpl<StrategyRankMapper, StrategyRank> implements StrategyRankService {

    @Override
    public List<StrategyRank> selectRankByType(int type) {
        //根据当前时间查询最新的10条
        QueryWrapper<StrategyRank> wrapper = new QueryWrapper<StrategyRank>()
                .eq("type",type)
                .orderByDesc("statis_time","statisnum")
                .last("limit 10");
        return list(wrapper);
    }
}
