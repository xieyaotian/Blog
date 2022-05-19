package com.tian.blog.controller;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.tian.blog.service.TagService;
import com.tian.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    @GetMapping("hot")
    public Result hot(){
        int limit =6;
        return tagService.hots(limit);
    }

    @GetMapping
    public Result findAll(){
        return tagService.findAll();
    }
    @GetMapping("detail")
    public Result detail(){
        return tagService.findAll();
    }
    @GetMapping("detail/{id}")
    public Result findDetailById(@PathVariable("id")String id){
        return tagService.findDetailById(id);
    }
}
