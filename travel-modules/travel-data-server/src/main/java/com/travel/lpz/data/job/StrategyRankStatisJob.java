package com.travel.lpz.data.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.travel.lpz.article.domain.Strategy;
import com.travel.lpz.article.domain.StrategyRank;
import com.travel.lpz.data.mapper.StrategyMapper;
import com.travel.lpz.data.mapper.StrategyRankMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author lpz
 * @title StrategyRankStatisJob
 * @date 2024/10/22 20:05
 * @description TODO
 */
@Component
@Slf4j
public class StrategyRankStatisJob {


    private final StrategyMapper strategyMapper;
    private final StrategyRankMapper strategyRankMapper;

    public StrategyRankStatisJob(StrategyMapper strategyMapper, StrategyRankMapper strategyRankMapper) {
        this.strategyMapper = strategyMapper;
        this.strategyRankMapper = strategyRankMapper;
    }
    @Transactional(rollbackFor = Exception.class)
//    @Scheduled(cron = "0 */10 * * * *")
    @Scheduled(cron = "0/30 * * * * *")
    public void statisBank(){
        //时间同步
        log.info("[攻略排行统计] 排行数据统计开始 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Date now = new Date();
        //查询国内攻略排行
        this.doStatis(now ,StrategyRank.TYPE_CHINA , ()-> strategyMapper.selectStrategyRankByAbroad(Strategy.ABROAD_NO));
        //查询国外攻略排行
        this.doStatis(now ,StrategyRank.TYPE_ABROAD , ()-> strategyMapper.selectStrategyRankByAbroad(Strategy.ABROAD_YES));
        //热门攻略排行
        this.doStatis(now ,StrategyRank.TYPE_HOT , () -> strategyMapper.selectStrategyRankHotList());
        //删除这次时间·之前的所有数据。
        strategyRankMapper.delete(new QueryWrapper<StrategyRank>()
                .lt("UNIX_TIMESTAMP(statis_time)", (now.getTime() - 500) / 1000));
        log.info("[攻略排行统计] 排行数据统计结束，删除旧数据 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void doStatis(Date now , Integer type , Supplier<List<StrategyRank>> rankSupplier){
        List<StrategyRank> strategyRanks = rankSupplier.get();
        log.info("[攻略排行统计] 排行数据统计: type={} ,ranks={}",type,strategyRanks.size());
        for (StrategyRank rank : strategyRanks) {
            rank.setType(type);
            rank.setStatisTime(now);
        }
        //保存到排名表
        strategyRankMapper.batchInsert(strategyRanks);
    }
}
