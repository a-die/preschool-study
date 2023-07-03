package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

//三表连查所用的实体类
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="FavoriteBlog对象", description="")
public class FavoriteBlogDiscussPostUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏的博文表的主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "收藏的博文ID")
    @TableField("discussPostId")
    private Integer discussPostId;

    @ApiModelProperty(value = "用户ID")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("0-笔记，1-踩坑记录，2-提问")
    private int btype;

    @ApiModelProperty("学科类型")
    private int subject;

    @ApiModelProperty("用户昵称")
    private String username;


}
