package com.travel.lpz.comment.service.impl;

import com.travel.lpz.comment.domain.StrategyComment;
import com.travel.lpz.comment.domain.TravelComment;
import com.travel.lpz.comment.qo.CommentQuery;
import com.travel.lpz.comment.repository.StrategyCommentRepository;
import com.travel.lpz.comment.repository.TravelCommentRepository;
import com.travel.lpz.comment.service.TravelCommentService;
import com.travel.lpz.core.exception.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpz
 * @title TravelCommentService
 * @date 2024/10/30 22:09
 * @description TODO
 */
@Service
public class TravelCommentServiceImpl implements TravelCommentService {
    private final TravelCommentRepository travelCommentRepository;
    private final MongoTemplate mongoTemplate;


    public TravelCommentServiceImpl(TravelCommentRepository travelCommentRepository, MongoTemplate mongoTemplate) {
        this.travelCommentRepository = travelCommentRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<TravelComment> page(CommentQuery qo) {
        if (qo.getArticleId() == null){
            throw new BusinessException("文章ID不能为空");
        }
        //1.拼接查询条件,mongoDB
        Criteria criteria = Criteria.where("travelId").is(qo.getArticleId());
        //2.创建查询对象，关联条件
        Query query = new Query(criteria);
        query.addCriteria(criteria);
        //统计总数
        long total = mongoTemplate.count(query, TravelComment.class);
        if (total == 0){
            return Page.empty();
        }
        //3.设置分页参数
        //PageRequest.of(page, size)以及(page, size ,sort)
        PageRequest request = PageRequest.of(qo.getCurrent() -1 , qo.getSize());
//        query.with(request);
        //计算分页
//        int skip = (qo.getCurrent()-1) * qo.getSize();
        //mogodb传统写法
        query.skip(request.getOffset()).limit(qo.getSize());
        //4.按照时间排序
        query.with(Sort.by(Sort.Direction.DESC,"createTime"));
        //5.分页查询
        List<TravelComment> list = mongoTemplate.find(query, TravelComment.class);
        return new PageImpl<>(list,request, total);
    }
}
