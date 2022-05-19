package com.tian.blog.controller;

import com.tian.blog.service.SysUserService;
import com.tian.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/currentUser")
    public Result currentUser(@RequestHeader("Authorization") String token){ //拿到Header 头部信息
        return sysUserService.findUserByToken(token);
    }
}
