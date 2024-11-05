package com.travel.lpz.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyRank;

import java.util.List;


public interface StrategyRankMapper extends BaseMapper<StrategyRank> {

    void batchInsert(List<StrategyRank> strategyRanks);
}
