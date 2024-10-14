package com.travel.lpz.core.qo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lpz
 * @title QueryObject
 * @date 2024/10/14 17:56
 * @description TODO
 */
@Getter
@Setter
public class QueryObject {
    private String keyword;
    private Integer current = 1;
    private Integer size = 10;
}
