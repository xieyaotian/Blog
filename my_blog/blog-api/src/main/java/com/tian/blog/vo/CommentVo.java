package com.tian.blog.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.tian.blog.dao.pojo.SysUser;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo  {
//    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private UserVo author;

    private String content;

    private List<CommentVo> childrens;

    private String createDate;

    private Integer level;

    private UserVo toUser;
}