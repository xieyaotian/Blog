package com.tian.blog.service;

import com.tian.blog.dao.pojo.SysUser;
import com.tian.blog.vo.Result;
import com.tian.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional //开启事务功能，自动回滚
public interface LoginService {
    /**
     * 登录功能
     * @param loginParam
     * @return
     */
    Result login(LoginParam loginParam);

    /**
     * 退出登录
     * @param token
     * @return
     */
    Result logout(String token);

    /**
     * 注册
     * @param loginParam
     * @return
     */
    Result register(LoginParam loginParam);

    /**
     * 检查token是否合法
     * @param token
     * @return
     */
    SysUser checkToken(String token);
}
