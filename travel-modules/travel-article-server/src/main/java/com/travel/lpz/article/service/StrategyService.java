package com.travel.lpz.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.domain.StrategyContent;

import java.util.List;


/**
 * @author lpz
 * @title RegionService
 * @date 2024/10/8 20:06
 * @description TODO
 */
public interface StrategyService extends IService<Strategy> {


    List<StrategyCatalog> findGroupsByDestId(Long destId);

    StrategyContent getContentById(Long id);

    List<Strategy> findViewnumByDestId(Long destId);
}
