package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
 * @since 2021-11-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Feedback对象", description="")
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "反馈信息ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "反馈者ID")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "反馈内容(用户->后台)")
    @TableField("feedbackFromContent")
    private String feedbackFromContent;

    @ApiModelProperty(value = "回馈内容(后台->用户)")
    @TableField("feedbackToContent")
    private String FeedbackToContent;

    @ApiModelProperty(value = "反馈时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty(value = "状态（未读，已处理，删除三种状态）")
    private Integer state;


}
