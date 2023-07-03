package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author a碟
 * @since 2021-11-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="两表查询", description="")
public class ReportingInformationDiscussPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "举报信息ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "举报人的ID")
    @TableField("reportIdFrom")
    private Integer reportIdFrom;

    @ApiModelProperty(value = "被举报的博文ID")
    @TableField("reportIdTo")
    private Integer reportIdTo;

    @ApiModelProperty(value = "举报原因")
    @TableField("reportReason")
    private String reportReason;

    @ApiModelProperty(value = "举报时间")
    @TableField("createTime")
    private Date createTime;

    @ApiModelProperty(value = "举报的补充信息")
    @TableField("reportInformation")
    private String reportInformation;

    @ApiModelProperty(value = "是否处理，1-已处理，0-未处理")
    @TableField("processStatus")
    private Integer processStatus;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;




}
