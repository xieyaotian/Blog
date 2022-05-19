package com.tian.blog.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tian.blog.dao.pojo.SysUser;
import com.tian.blog.dao.pojo.Tag;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private String title;

    private String summary;

    private int commentCounts;

    private int viewCounts;

    private int weight;
    /**
     * 创建时间
     */
    private String createDate;

    private String author;

    private ArticleBodyVo body;

    private List<TagVo> tags;

    private CategoryVo category;
    //private List<CategoryVo> categorys;

}