package com.travel.lpz.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.domain.StrategyContent;
import com.travel.lpz.article.domain.Travel;
import com.travel.lpz.article.qo.StrategyQuery;
import com.travel.lpz.article.qo.TravelQuery;
import com.travel.lpz.article.vo.StrategyCondition;

import java.util.List;


/**
 * @author lpz
 * @title RegionService
 * @date 2024/10/8 20:06
 * @description TODO
 */
public interface TravelService extends IService<Travel> {


    Page<Travel> pageList(TravelQuery query);
}
