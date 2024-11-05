package com.travel.lpz.article.qo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.lpz.core.qo.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lpz
 * @title DestinationQuery
 * @date 2024/10/14 17:55
 * @description TODO
 */
@Getter
@Setter
public class StrategyQuery extends QueryObject {
    private Long destId;
    private Long themeId;
    private Long refid;
    private Integer type;
    private String orderBy;
}
