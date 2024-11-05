package com.travel.lpz.comment.controller;

import com.travel.lpz.comment.service.StrategyCommentService;
import com.travel.lpz.comment.service.TravelCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lpz
 * @title StrategyCommentController
 * @date 2024/10/30 23:34
 * @description TODO
 */
@RestController
@RequestMapping("/travels/comments")
public class TravelCommentController {
    private final TravelCommentService travelCommentService;
    public TravelCommentController(TravelCommentService travelCommentService) {
        this.travelCommentService = travelCommentService;
    }


}
