package com.tian.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tian.blog.dao.mapper.ArticleMapper;
import com.tian.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {
    @Async("taskExecutor")
    public void updateViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        article.setViewCounts(viewCounts+1);
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getId, article.getId());
        queryWrapper.eq(Article::getViewCounts, viewCounts);
        articleMapper.update(article, queryWrapper);


    }
}