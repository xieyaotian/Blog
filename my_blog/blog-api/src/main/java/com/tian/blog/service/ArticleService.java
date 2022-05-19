package com.tian.blog.service;

import com.tian.blog.dao.pojo.Article;
import com.tian.blog.vo.Result;
import com.tian.blog.vo.params.ArticleBodyParam;
import com.tian.blog.vo.params.ArticleParam;
import com.tian.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热文章n条
     * @param limit
     * @return
     */
    Result hotArticle(int limit);

    /**
     * 最新文章n条
     * @param limit
     * @return
     */
    Result newArticle(int limit);

    /**
     * 文章归档 根据年月日
     * @return
     */
    Result listArchives();

    /**
     * 根据文章id查询文章详细内容
     * @param Id
     * @return
     */
    Result findArticleById(Long Id);

    /**
     * 发布文章
     * @param articleParam
     * @return
     */
    Result publish(ArticleParam articleParam);
}
