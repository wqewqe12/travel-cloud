package com.travel.lpz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.vo.StrategyCondition;

import java.util.List;


public interface StrategyMapper extends BaseMapper<Strategy> {

    List<StrategyCatalog> selectGroupsByDestId(Long destId);

    List<StrategyCondition> selectDestCondition(int abroad);

    List<StrategyCondition> selectThemeCondition();
}
