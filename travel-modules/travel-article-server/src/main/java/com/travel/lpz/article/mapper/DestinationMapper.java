package com.travel.lpz.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.lpz.article.domain.Destination;
import com.travel.lpz.user.domain.UserInfo;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DestinationMapper extends BaseMapper<Destination> {
    List<Destination> selectHotListByRid( Long rid,  List<Long> ids);
}
