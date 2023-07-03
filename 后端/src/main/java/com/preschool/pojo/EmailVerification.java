package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

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
 * @since 2021-12-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="EmailVerification对象", description="")
public class EmailVerification implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户email")
    private String email;

    @ApiModelProperty(value = "验证码")
    private String code;

    @ApiModelProperty(value = "验证码获得的时间")
    private Date createTime;

}
