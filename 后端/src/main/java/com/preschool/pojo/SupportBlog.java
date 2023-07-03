package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author a碟
 * @since 2021-12-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SupportBlog对象", description="")
public class SupportBlog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "点赞的博文表的主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "点赞的博文ID")
    @TableField("discussPostId")
    private Integer discussPostId;

    @ApiModelProperty(value = "用户ID")
    @TableField("userId")
    private Integer userId;


}
