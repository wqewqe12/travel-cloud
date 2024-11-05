package com.travel.lpz.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.domain.StrategyRank;
import com.travel.lpz.article.vo.StrategyCondition;

import java.util.List;


public interface StrategyMapper extends BaseMapper<Strategy> {
    List<StrategyRank> selectStrategyRankByAbroad(Integer abroad);

    List<StrategyRank> selectStrategyRankHotList();
}
