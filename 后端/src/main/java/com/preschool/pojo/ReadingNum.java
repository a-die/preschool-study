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
 * @since 2021-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ReadingNum对象", description="")
public class ReadingNum implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "阅读量表主键Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户Id")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "阅读量")
    private Integer num;


}
