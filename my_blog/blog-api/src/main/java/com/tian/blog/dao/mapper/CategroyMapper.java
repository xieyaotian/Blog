package com.tian.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tian.blog.dao.pojo.Category;
import com.tian.blog.vo.CategoryVo;
import org.springframework.stereotype.Repository;

@Repository
public interface CategroyMapper extends BaseMapper<Category> {
}
