package com.tian.blog.controller;

import com.tian.blog.service.CategoryService;
import com.tian.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result listCategory(){
        return categoryService.findAll();
    }
    @GetMapping("detail")
    public Result categoriesDetail(){
        return categoryService.detail();
    }
    @GetMapping("detail/{id}")
    public Result categoriesDetailById(@PathVariable("id") String id){
        return categoryService.categoriesDetailById(id);
    }

}
