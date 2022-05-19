package com.tian.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tian.blog.dao.dos.Archives;
import com.tian.blog.dao.mapper.ArticleBodyMapper;
import com.tian.blog.dao.mapper.ArticleMapper;
import com.tian.blog.dao.mapper.ArticleTagMapper;
import com.tian.blog.dao.mapper.CategroyMapper;
import com.tian.blog.dao.pojo.*;
import com.tian.blog.service.ArticleService;
import com.tian.blog.service.SysUserService;
import com.tian.blog.service.TagService;
import com.tian.blog.service.ThreadService;
import com.tian.blog.utils.UserThreadLocal;
import com.tian.blog.vo.*;
import com.tian.blog.vo.params.ArticleBodyParam;
import com.tian.blog.vo.params.ArticleParam;
import com.tian.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategroyMapper categroyMapper;
    @Autowired
    private ThreadService threadService;
    @Override
    public Result listArticle(PageParams pageParams) {
        Page<Article> page = new Page<>(pageParams.getPage(),pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(page,pageParams.getCategoryId(),pageParams.getTagId(),pageParams.getYear(),pageParams.getMonth());
        return Result.success(copyList(articleIPage.getRecords(),true,true,false,false));

//        /**
//         * 1.分页查询article数据库表
//         */
//        Page<Article> page=new Page<>(pageParams.getPage(),pageParams.getPageSize());
//        //查询条件构造器
//        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
//        //根据日期和是否置顶进行倒叙排列
//        queryWrapper.orderByDesc(Article::getWeight,Article::getCreateDate);
//        if (pageParams.getCategoryId() != null) {
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        List<Long> articleIdList = new ArrayList<>();
//        if (pageParams.getTagId() != null) {
//            LambdaQueryWrapper<ArticleTag> wrapper=new LambdaQueryWrapper<>();
//            wrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(wrapper);
//            for (ArticleTag articleTag : articleTags) {
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size()>0) queryWrapper.in(Article::getId,articleIdList);
//        }
//        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
//        List<Article> records = articlePage.getRecords();
//        List<ArticleVo> articleVoList = copyList(records,true,true,false,false);
//        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        //select id,title from article order by viewCounts desc limit #{limit}
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles, false, false,false,false));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle);
        queryWrapper.last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles,false,false,false,false));
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList =articleMapper.listArchives();
        return Result.success(archivesList);
    }

    @Override
    public Result findArticleById(Long Id) {
        /**
         * 1.返回ArticlaVo 根据articlaid寻找article
         * 2.根据bodyid 和categoryid去做关联查询
         */
        System.out.println(Id);
        Article article = articleMapper.selectById(Id);
        if(article==null) return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), "找不到文章");
        ArticleVo articleVo = copy(article, true, true,true,true);
        threadService.updateViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    @Override
    public Result publish(ArticleParam articleParam) {
        SysUser sysUser = UserThreadLocal.get();

        Article article = new Article();
        article.setAuthorId(sysUser.getId());
        article.setCategoryId(articleParam.getCategory().getId());
        article.setCreateDate(System.currentTimeMillis());
        article.setCommentCounts(0);
        article.setSummary(articleParam.getSummary());
        article.setTitle(articleParam.getTitle());
        article.setViewCounts(0);
        article.setBodyId(-1L);
        articleMapper.insert(article);

        //Tags
        List<TagVo> tags = articleParam.getTags();
        if(tags!=null){
            for (TagVo tag : tags) {
                ArticleTag articleTag =new ArticleTag();
                articleTag.setArticleId(article.getId());
                articleTag.setTagId(tag.getId());
                articleTagMapper.insert(articleTag);
            }
        }

        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(article.getId());
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        articleBodyMapper.insert(articleBody);

        article.setBodyId(articleBody.getId());
        articleMapper.updateById(article);

        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        return Result.success(articleVo);
    }

    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article record : records) {
            articleVoList.add(copy(record,isTag,isAuthor,isBody,isCategory));
        }
        return articleVoList;
    }
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if(isTag){
            Long articleId=article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));

        }
        if(isAuthor){
            Long authorId=article.getAuthorId();
            articleVo.setAuthor(sysUserService.findUserById(authorId).getNickname());
        }
        if (isBody){
            ArticleBody articleBody = articleBodyMapper.selectById(article.getBodyId());
            ArticleBodyVo articleBodyVo =new ArticleBodyVo();
            articleBodyVo.setContent(articleBody.getContent());
            articleVo.setBody(articleBodyVo);
        }
        if(isCategory){
            Category category = categroyMapper.selectById(article.getCategoryId());
            CategoryVo categoryVo=new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }
}
