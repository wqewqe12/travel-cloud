package com.travel.lpz.comment.qo;

import com.travel.lpz.core.qo.QueryObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentQuery extends QueryObject {

    private Long articleId;
}