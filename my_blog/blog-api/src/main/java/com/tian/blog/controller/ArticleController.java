package com.tian.blog.controller;

import com.tian.blog.common.aop.LogAnnotation;
import com.tian.blog.common.cache.Cache;
import com.tian.blog.service.ArticleService;
import com.tian.blog.vo.Result;
import com.tian.blog.vo.params.ArticleBodyParam;
import com.tian.blog.vo.params.ArticleParam;
import com.tian.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//Json数据交互
@RestController
@RequestMapping("articles")

public class ArticleController {
    @Autowired //自动装配，将spring容器中的bean自动和我们需要的bean组装在一起
    private ArticleService articleService;
    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    @LogAnnotation(module = "文章",operation = "获取文章列表")
    @Cache(expire = 5*60*1000,name = "list_article")
    public Result listArticle(@RequestBody PageParams pageParams){ //@RequestBody 可以将Json字符串的值赋予到PageParams 对应的属性上
        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("/hot")
    public Result hotArticle(){
        int limit=3;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("/new")
    public Result newArticle(){
        int limit=3;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     * @return
     */
    @PostMapping("/listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 根据id查询文章详情
     * @param Id
     * @return
     */
    @PostMapping("/view/{id}")
    public Result findArticleById(@PathVariable("id") Long Id){
        return articleService.findArticleById(Id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
