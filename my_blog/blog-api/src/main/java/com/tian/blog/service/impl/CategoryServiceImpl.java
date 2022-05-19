package com.tian.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.tian.blog.dao.mapper.CategroyMapper;
import com.tian.blog.dao.pojo.Category;
import com.tian.blog.dao.pojo.Tag;
import com.tian.blog.service.CategoryService;
import com.tian.blog.vo.CategoryVo;
import com.tian.blog.vo.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategroyMapper categroyMapper;

    @Override
    public Result findAll() {
        List<Category> categoryList = categroyMapper.selectList(new LambdaQueryWrapper<>());
        return Result.success(copyList(categoryList));
    }

    @Override
    public Result detail() {
        List<Category> categories = categroyMapper.selectList(null);
        return Result.success(copyList(categories));
    }

    @Override
    public Result categoriesDetailById(String id) {
        Category category = categroyMapper.selectById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return Result.success(categoryVo);
    }

    private List<CategoryVo> copyList(List<Category> categoryList) {
        List<CategoryVo> categoryVoList =new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVo categoryVo= new CategoryVo();
            BeanUtils.copyProperties(category,categoryVo);
            categoryVoList.add(categoryVo);
        }
        return categoryVoList;
    }
}
