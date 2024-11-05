package com.travel.lpz.comment.service;

import com.travel.lpz.comment.domain.StrategyComment;
import com.travel.lpz.comment.qo.CommentQuery;
import com.travel.lpz.core.qo.QueryObject;
import org.springframework.data.domain.Page;

/**
 * @author lpz
 * @title StrategyCommentService
 * @date 2024/10/30 22:09
 * @description TODO
 */
public interface StrategyCommentService {

    Page<StrategyComment> page(CommentQuery cq);

    void save(StrategyComment comment);

    void doLike(String cid);
}
