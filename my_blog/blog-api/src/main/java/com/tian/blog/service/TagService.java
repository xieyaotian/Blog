package com.tian.blog.service;

import com.tian.blog.vo.Result;
import com.tian.blog.vo.TagVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TagService {
    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);

    Result findAll();

    Result findDetailById(String id);
}
