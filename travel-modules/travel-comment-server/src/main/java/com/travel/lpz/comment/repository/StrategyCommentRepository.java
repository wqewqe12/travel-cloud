package com.travel.lpz.comment.mapper;

import com.travel.lpz.comment.domain.StrategyComment;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author lpz
 * @title StrategyComment
 * @date 2024/10/30 22:08
 * @description TODO
 */
public interface StrategyCommentRepository extends MongoRepository<StrategyComment ,String> {
    StrategyComment findByStrategyId(String strategyId);
}
