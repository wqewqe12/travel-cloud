package com.travel.lpz.comment.service.impl;

import com.travel.lpz.auth.untils.AuthenticationUntils;
import com.travel.lpz.comment.domain.StrategyComment;
import com.travel.lpz.comment.qo.CommentQuery;
import com.travel.lpz.comment.repository.StrategyCommentRepository;
import com.travel.lpz.comment.service.StrategyCommentService;
import com.travel.lpz.core.exception.BusinessException;
import com.travel.lpz.user.vo.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author lpz
 * @title TravelCommentService
 * @date 2024/10/30 22:09
 * @description TODO
 */
@Service
public class StrategyCommentServiceImpl implements StrategyCommentService {
    private final StrategyCommentRepository strategyCommentRepository;
    private final MongoTemplate mongoTemplate;

    public StrategyCommentServiceImpl(StrategyCommentRepository strategyCommentRepository, MongoTemplate mongoTemplate) {
        this.strategyCommentRepository = strategyCommentRepository;
        this.mongoTemplate = mongoTemplate;
    }


    //分页查询
    @Override
    public Page<StrategyComment> page(CommentQuery qo) {
        if (qo.getArticleId() == null){
            throw new BusinessException("文章ID不能为空");
        }
        //1.拼接查询条件,mongoDB
        Criteria criteria = Criteria.where("strategyId").is(qo.getArticleId());
        //2.创建查询对象，关联条件
        Query query = new Query(criteria);
        query.addCriteria(criteria);
        //统计总数
        long total = mongoTemplate.count(query, StrategyComment.class);
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
        List<StrategyComment> list = mongoTemplate.find(query, StrategyComment.class);
        return new PageImpl<>(list,request, total);
    }

    @Override
    public void save(StrategyComment comment) {
        //已经贴上@RequireLogin注解,获取当前登录用户信息
        LoginUser user = AuthenticationUntils.getUser();
        //userId,nickname,city,level,headImgUrl
        comment.setUserId(user.getId());
        comment.setNickname(user.getNickname());
        comment.setCity(user.getCity());
        comment.setLevel(user.getLevel());
        comment.setHeadImgUrl(user.getHeadImgUrl());
        comment.setCreateTime(new Date());
        // 保存到 mongodb
        strategyCommentRepository.save(comment);
    }

    @Override
    public void doLike(String cid) {
        //查评论
        Optional<StrategyComment> optional = strategyCommentRepository.findById(cid);
        if (optional.isPresent()){
            StrategyComment strategyComment = optional.get();
            //获取当前登录
            LoginUser user = AuthenticationUntils.getUser();
            //判断是否已经点赞
            if (strategyComment.getThumbuplist().contains(user.getId())){
                //点赞数 -1
                strategyComment.setThumbupnum(strategyComment.getThumbupnum()-1);
                List<Long> thumbuplist = strategyComment.getThumbuplist();
                thumbuplist.remove(user.getId());
            }
            else {
                strategyComment.setThumbupnum(strategyComment.getThumbupnum()+1);
                List<Long> thumbuplist = strategyComment.getThumbuplist();
                thumbuplist.add(user.getId());
            }
            //重新将对象保存到mongoDB
            strategyCommentRepository.save(strategyComment);
        }

    }
}
