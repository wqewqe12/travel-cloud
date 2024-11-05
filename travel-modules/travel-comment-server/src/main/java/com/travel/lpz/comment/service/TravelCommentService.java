package com.travel.lpz.comment.service;


import com.travel.lpz.comment.domain.TravelComment;
import com.travel.lpz.comment.qo.CommentQuery;
import org.springframework.data.domain.Page;

/**
 * @author lpz
 * @title StrategyCommentService
 * @date 2024/10/30 22:09
 * @description TODO
 */
public interface TravelCommentService {
    Page<TravelComment> page(CommentQuery qo);
}
