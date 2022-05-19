package com.tian.blog.service.impl;

import com.tian.blog.dao.mapper.TagMapper;
import com.tian.blog.dao.pojo.Tag;
import com.tian.blog.service.TagService;
import com.tian.blog.vo.Result;
import com.tian.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {

        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    @Override
    public Result hots(int limit) {
        /**
         * 1.标签所拥有文章最多
         * 2.查询 根据tagid进行分组技术，从大到小排列
         */
        List<TagVo> tagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        return Result.success(tagIds);

    }

    @Override
    public Result findAll() {
        List<Tag> tagList = tagMapper.selectList(null);
        return Result.success(copyList(tagList));
    }

    @Override
    public Result findDetailById(String id) {
        Tag tag = tagMapper.selectById(id);
        return Result.success(copy(tag));
    }


    private List<TagVo> copyList(List<Tag> tags) {
        List<TagVo> tagVoList =new ArrayList<>();
        for (Tag tag : tags) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    private TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }
}
