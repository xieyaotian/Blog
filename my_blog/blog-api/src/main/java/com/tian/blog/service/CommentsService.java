package com.tian.blog.service;

import com.tian.blog.vo.Result;
import com.tian.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章id 查询所有的评论列表
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    /**
     * 文章评论
     * @param commentParam
     * @return
     */
    Result comment(CommentParam commentParam);
}
