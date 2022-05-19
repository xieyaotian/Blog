package com.tian.blog.service;

import com.tian.blog.vo.Result;

public interface CategoryService {
    Result findAll();

    Result detail();

    Result categoriesDetailById(String id);
}
