package com.travel.lpz.article.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.lpz.article.domain.*;
import com.travel.lpz.article.mapper.StrategyContentMapper;
import com.travel.lpz.article.mapper.StrategyMapper;
import com.travel.lpz.article.qo.StrategyQuery;
import com.travel.lpz.article.service.DestinationService;
import com.travel.lpz.article.service.StrategyCatalogService;
import com.travel.lpz.article.service.StrategyService;
import com.travel.lpz.article.service.StrategyThemeService;
import com.travel.lpz.article.untils.QiNiuUntils;
import com.travel.lpz.article.vo.StrategyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * @author lpz
 * @title RegionServiceImpl
 * @date 2024/10/8 20:09
 * @description TODO
 */
@Service
public class StrategyServiceImpl extends ServiceImpl<StrategyMapper, Strategy> implements StrategyService {

    private final QiNiuUntils qiNiuUntils;
    private final DestinationService destinationService;
    private final StrategyCatalogService strategyCatalogService;
    private final StrategyThemeService strategyThemeService;
    private final StrategyContentMapper strategyContentMapper;

    public StrategyServiceImpl(QiNiuUntils qiNiuUntils, DestinationService destinationService, StrategyCatalogService strategyCatalogService, StrategyThemeService strategyThemeService, StrategyContentMapper strategyContentMapper) {
        this.qiNiuUntils = qiNiuUntils;
        this.destinationService = destinationService;
        this.strategyCatalogService = strategyCatalogService;
        this.strategyThemeService = strategyThemeService;
        this.strategyContentMapper = strategyContentMapper;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveOrUpdate(Strategy entity) {
        //1.完成图片上传，得到url，重新设置到cover属性中
        ///images/default/wKgEaVyK_4SAdEn8AA10khckiyI39.jpeg
        // 2. 补充分类名称
        StrategyCatalog catalog = strategyCatalogService.getById(entity.getCatalogId());
        entity.setCatalogName(catalog.getName());
        // 3. 根据分类中的目的地id/名称设置到攻略对象中
        entity.setDestId(catalog.getDestId());
        entity.setDestName(catalog.getDestName());
        // 4. 基于目的地判断是否是国外
        List<Destination> toasts = destinationService.findToasts(catalog.getDestId());
        Destination dest = toasts.get(0);
        if (dest.getId() == 1) {
            entity.setIsabroad(Strategy.ABROAD_NO);
        } else {
            entity.setIsabroad(Strategy.ABROAD_YES);
        }
        // 5. 查询主题, 设置主题名称
        StrategyTheme theme = strategyThemeService.getById(entity.getThemeId());
        entity.setThemeName(theme.getName());

        // 判断是更新还是新增
        if (entity.getId() == null) {
            // 6. 设置创建时间
            entity.setCreateTime(new Date());
            // 7. 设置各种数量为0
            entity.setViewnum(0);
            entity.setSharenum(0);
            entity.setThumbsupnum(0);
            entity.setReplynum(0);
            entity.setFavornum(0);
            // 8. 重新设置默认状态, 覆盖前端提交的值
            entity.setState(Strategy.STATE_NORMAL);
            // 9. 保存攻略对象, 得到攻略自增的 id
            boolean save = super.save(entity);
            // 10. 将攻略 id 设置到内容对象中, 并保存内容对象
            StrategyContent content = entity.getContent();
            content.setId(entity.getId());
            return save && strategyContentMapper.insert(content) > 0;
        }

        // 更新操作
        boolean ret = super.updateById(entity);
        StrategyContent content = entity.getContent();
        content.setId(entity.getId());
        int row = strategyContentMapper.updateById(entity.getContent());
        return ret && row > 0;
    }

    @Override
    public Strategy getById(Serializable id) {
        Strategy strategy = super.getById(id);
        StrategyContent content = strategyContentMapper.selectById(id);
        strategy.setContent(content);
        return strategy;
    }
@Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(Strategy entity) {
        return saveOrUpdate(entity);
    }

    @Override
    public List<StrategyCatalog> findGroupsByDestId(Long destId) {

        return getBaseMapper().selectGroupsByDestId(destId);
    }

    @Override
    public StrategyContent getContentById(Long id) {
        return strategyContentMapper.selectById(id);
    }

    @Override
    public List<Strategy> findViewnumByDestId(Long destId) {
        //查询指定目的地，浏览前三的攻略
        QueryWrapper<Strategy> wrapper = new QueryWrapper<Strategy>()
                .eq("dest_id", destId)
                .orderByDesc("viewnum")
                .last("limit 3");
        return list(wrapper);
    }

    @Override
    public Page<Strategy> pageList(StrategyQuery qo) {
        if ((qo.getType() != null && qo.getType() != -1) && (qo.getRefid() != null && qo.getRefid() != -1)) {
            if (qo.getType() == 3) {
                qo.setThemeId(qo.getRefid());
            }else {
                qo.setDestId(qo.getRefid());
            }
        }
        QueryWrapper<Strategy> wrapper = new QueryWrapper<Strategy>()
                .eq(qo.getDestId() != null, "dest_id", qo.getDestId())
                .eq(qo.getThemeId() != null, "theme_id", qo.getThemeId())
                .orderByDesc(!StringUtils.isEmpty(qo.getOrderBy()),qo.getOrderBy());
        return super.page(new Page<>(qo.getCurrent(),qo.getSize()),wrapper);
    }

    @Override
    public List<StrategyCondition> findDestCondition(int abroad) {
        return getBaseMapper().selectDestCondition(abroad);
    }

    @Override
    public List<StrategyCondition> findThemeCondition() {
        return getBaseMapper().selectThemeCondition();
    }
}
