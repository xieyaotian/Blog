package com.tian.blog.service;

import com.tian.blog.dao.pojo.SysUser;
import com.tian.blog.vo.Result;
import com.tian.blog.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(long id);

    /**
     * 根据账户密码去匹配对应的用户
     * @param account
     * @param password
     * @return
     */
    SysUser findUser(String account, String password);

    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long authorId);
}
