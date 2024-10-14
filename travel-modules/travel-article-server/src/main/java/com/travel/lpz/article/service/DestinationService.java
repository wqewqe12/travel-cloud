package com.travel.lpz.article.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.article.domain.Region;
import com.travel.lpz.article.qo.DestinationQuery;

import java.util.List;

/**
 * @author lpz
 * @title RegionService
 * @date 2024/10/8 20:06
 * @description TODO
 */
public interface DestinationService extends IService<Destination> {

    List<Destination> getDestinationByRegionId(Long id);

    Page<Destination> pageList(DestinationQuery query);

    List<Destination> findToasts(Long destId);

    List<Destination> findDestsByRid(Long rid);
}
