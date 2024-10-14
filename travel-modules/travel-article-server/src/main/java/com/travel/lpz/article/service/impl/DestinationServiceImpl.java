package com.travel.lpz.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.mapper.DestinationMapper;
import com.travel.lpz.article.qo.DestinationQuery;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.article.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
public class DestinationServiceImpl extends ServiceImpl<DestinationMapper,Destination> implements DestinationService{

    private final RegionService regionService;

    public DestinationServiceImpl(RegionService regionService) {
        this.regionService = regionService;
    }


    @Override
    public List<Destination> getDestinationByRegionId(Long regionId) {
        //基于区域查询id查询区域对象
        Region region = regionService.getById(regionId);
        if (region ==null){
            return Collections.emptyList();
        }
        //基于区域对象，得到目的地id集合
        List<Long> ids = region.parseRefIds();
        if (ids.size() == 0){
            return Collections.emptyList();
        }
        //基于目的地id集合查询目的地对象集合，返回
        return super.listByIds(ids);
    }

    @Override
    public Page<Destination> pageList(DestinationQuery query) {
        QueryWrapper<Destination> wrapper = new QueryWrapper<>();
        //queryid为null，直接返回所有parentid = null 的数据
        //不为null根据parentid查询
        wrapper.isNull(query.getParentId() == null ,"parent_id");
        wrapper.eq(query.getParentId() != null,"parent_id", query.getParentId());
//        wrapper.like(!StringUtils.isEmpty(query.getKeyword()),"name",query.getKeyword());//关键字查询
        wrapper.like(query.getKeyword()!= null,"name",query.getKeyword());//关键字查询
        return super.page(new Page<>(query.getCurrent(), query.getSize()),wrapper);
    }

    @Override
    public List<Destination> findToasts(Long destId) {
        List<Destination> list = new ArrayList<>();
        while (destId != null){
            Destination dest =super.getById(destId);
            if (dest == null){
                break;
            }
            list.add(dest);
            destId = dest.getParentId();
        }
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<Destination> findDestsByRid(Long rid) {
        List<Destination> destinations = new ArrayList<>();
        QueryWrapper<Destination> wrapper = new QueryWrapper<Destination>().eq("parent_id",1);
        if (rid < 0){
             destinations = list(wrapper);
        }
        else {
            Region region = regionService.getById(rid);
            if (region == null) {
                return Collections.emptyList();
            }
           destinations = super.listByIds(region.parseRefIds());
        }
        //查询目的地下的下一级目的地
        for (Destination destination : destinations) {
            //删除warrper条件
            wrapper.clear();
            List<Destination>  children = list(wrapper.eq("parent_id",destination.getId()).last("limit 10"));
            destination.setChildren(children);
        }
        return destinations;
    }
}
