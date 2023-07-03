package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.sql.Timestamp;

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
 * @since 2021-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Orders对象", description="")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单Id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单所有者的Id")
    @TableField("userId")
    private Integer userId;

    @ApiModelProperty(value = "订单新增的时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "订单中所购买的博文的Id")
    @TableField("discussPostId")
    private Integer discussPostId;

    @ApiModelProperty(value = "所花费的CC币数量")
    private Integer price;

    @ApiModelProperty(value = "通知状态")
    private Integer readed;


}
