package com.travel.lpz.comment.controller;

import com.travel.lpz.auth.anno.RequireLogin;
import com.travel.lpz.comment.domain.StrategyComment;
import com.travel.lpz.comment.qo.CommentQuery;
import com.travel.lpz.comment.service.StrategyCommentService;
import com.travel.lpz.core.untils.R;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lpz
 * @title StrategyCommentController
 * @date 2024/10/30 23:34
 * @description TODO
 */
@RestController
@RequestMapping("/strategies/comments")
public class StrategyCommentController {
    private final StrategyCommentService strategyCommentService;
    public StrategyCommentController(StrategyCommentService strategyCommentService) {
        this.strategyCommentService = strategyCommentService;
    }
    @RequireLogin
    @PostMapping("/save")
    public R<?> save(StrategyComment comment) {
        strategyCommentService.save(comment);
        return R.success();
    }


    @PostMapping("/query")
    public R<Page<StrategyComment>> query(CommentQuery qo) {
        Page<StrategyComment> page = strategyCommentService.page(qo);
        return R.success(page);
    }
    @RequireLogin
    @PostMapping("/likes")
    public R<?> likes(String cid) {
        strategyCommentService.doLike(cid);
        return R.success();
    }



}
