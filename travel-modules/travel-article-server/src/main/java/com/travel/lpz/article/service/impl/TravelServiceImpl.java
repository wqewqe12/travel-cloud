package com.travel.lpz.article.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.Travel;
import com.travel.lpz.article.domain.TravelContent;
import com.travel.lpz.article.feign.UserInfoFeignService;
import com.travel.lpz.article.mapper.TravelContentMapper;
import com.travel.lpz.article.mapper.TravelMapper;
import com.travel.lpz.article.qo.TravelQuery;
import com.travel.lpz.article.service.TravelService;
import com.travel.lpz.article.vo.TravelRange;
import com.travel.lpz.auth.untils.AuthenticationUntils;
import com.travel.lpz.core.untils.R;
import com.travel.lpz.user.dto.UserInfoDTO;
import com.travel.lpz.user.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
@Slf4j
public class TravelServiceImpl extends ServiceImpl<TravelMapper, Travel> implements TravelService {


    private final UserInfoFeignService userInfoFeignService;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final TravelContentMapper travelContentMapper;

    public TravelServiceImpl(UserInfoFeignService userInfoFeignService, ThreadPoolExecutor threadPoolExecutor, TravelContentMapper travelContentMapper) {
        this.userInfoFeignService = userInfoFeignService;
        this.threadPoolExecutor = threadPoolExecutor;
        this.travelContentMapper = travelContentMapper;
    }

    @Override
    public Page<Travel> pageList(TravelQuery query) {
        QueryWrapper<Travel> wrapper = Wrappers.<Travel>query()
                .eq(query.getDestId() != null, "dest_id", query.getDestId());
        if (query.getTravelTimeRange() != null) {
            TravelRange time = query.getTravelTimeRange();
            wrapper.between("MONTH(travel_time)", time.getMin(), time.getMax());
        }
        if (query.getCostRange() != null) {
            TravelRange cost = query.getCostRange();
            wrapper.between("avg_consume", cost.getMin(), cost.getMax());
        }
        if (query.getDayRange() != null) {
            TravelRange days = query.getDayRange();
            wrapper.between("day", days.getMin(), days.getMax());
        }
        wrapper.orderByDesc(query.getOrderBy());

        //游客：查看已公开的游记
        //用户：查看已公开的和自己的游记
        LoginUser user = AuthenticationUntils.getUser();
        if (user == null ){
            wrapper.eq("is_public", Travel.ISPUBLIC_YES)
                    .eq("state",Travel.STATE_RELEASE);
        }else {
            //author_id = #{author_id} or (ispublic =1 and state = 2)
            wrapper.and(w -> {
                w.eq("author_id",user.getId())
                        .or(ww->{
                            ww.eq("is_public", Travel.ISPUBLIC_YES)
                                    .eq("state",Travel.STATE_RELEASE);
                        });
            });
        }
        Page<Travel> page = super.page(new Page<>(query.getCurrent(), query.getSize()), wrapper);
        List<Travel> records = page.getRecords();
        //创建计数器，等待所有线程完成
        //并行查询用户信息
        CountDownLatch latch = new CountDownLatch(records.size());
        for (Travel travel : records) {
            threadPoolExecutor.execute(() -> {
                try {
                    R<UserInfoDTO> result = userInfoFeignService.getById(travel.getId());
                    if (result.getCode() != R.CODE_SUCCESS) {
                        log.warn("[游记服务]查询用户作者异常，返回异常服务:{}", JSON.toJSONString(result));
                        latch.countDown();
                        return;
                    }
                    travel.setAuthor(result.getData());
                }catch (Exception e){
                    e.printStackTrace();
                }
                finally{
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return page;
    }
    //重写getById方法，添加用户信息查询
    @Override
    public Travel getById(Serializable id) {
        Travel travel = super.getById(id);
        if (travel == null){
           return null;
        }
        TravelContent content = travelContentMapper.selectById(id);
        travel.setContent(content);
        R<UserInfoDTO> result = userInfoFeignService.getById(travel.getAuthorId());
        UserInfoDTO dto = result.getAndCheck();
        travel.setAuthor(dto);
        return travel;
    }
}