package com.travel.lpz.data.service.impl;

import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.data.mapper.StrategyMapper;
import com.travel.lpz.data.service.StrategyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements StrategyService {
}