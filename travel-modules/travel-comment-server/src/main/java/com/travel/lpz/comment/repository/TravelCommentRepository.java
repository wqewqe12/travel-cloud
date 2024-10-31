package com.travel.lpz.comment.mapper;

import com.travel.lpz.comment.domain.StrategyComment;
import com.travel.lpz.comment.domain.TravelComment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lpz
 * @title TravelComment
 * @date 2024/10/30 22:08
 * @description TODO
 */
public interface TravelCommentRepository extends MongoRepository<TravelComment,String> {
    public TravelComment findByStrategyId(String strategyId);
}
