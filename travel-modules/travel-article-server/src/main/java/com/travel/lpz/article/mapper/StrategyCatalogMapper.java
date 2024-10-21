package com.travel.lpz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.lpz.article.domain.StrategyCatalog;
import com.travel.lpz.article.vo.StrategyCatalogGroup;

import java.util.List;


public interface StrategyCatalogMapper extends BaseMapper<StrategyCatalog> {

    List<StrategyCatalogGroup> selectGroupList();
}
