package com.preschool.pojo;

/*

Create Table

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `password` varchar(50) NOT NULL COMMENT '用户密码',
  `email` varchar(100) DEFAULT NULL COMMENT '用户邮箱',
  `username` varchar(50) NOT NULL COMMENT '用户昵称',
  `type` int NOT NULL COMMENT '用户类型，0-普通用户; 1-管理员;',
  `create_time` timestamp NOT NULL COMMENT '用户注册时间',
  `birthday` date DEFAULT NULL COMMENT '用户生日',
  `phonenumber` varchar(50) DEFAULT NULL COMMENT '用户手机号',
  `sex` int DEFAULT NULL COMMENT '用户性别，0-男，1-女',
  `signature` varchar(100) DEFAULT NULL COMMENT '个性签名',
  `portrait_id` int DEFAULT NULL COMMENT '用户头像id',
  PRIMARY KEY (`id`),
  KEY `portrait_id_fk` (`portrait_id`),
  CONSTRAINT `portrait_id_fk` FOREIGN KEY (`portrait_id`) REFERENCES `user_portrait` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

 */

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户实体类")
public class User implements Serializable {
    @ApiModelProperty("用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户手机号")
    private String phonenumber;
    @ApiModelProperty("用户密码")
    private String password;
    @ApiModelProperty("用户邮箱")
    private String email;
    @ApiModelProperty("用户昵称")
    private String username;
    @ApiModelProperty("用户类型，0-普通用户; 1-管理员")
    private int type;

    @ApiModelProperty("用户注册时间")
    private Timestamp createTime;
    @ApiModelProperty("用户修改时间")
    private Timestamp updateTime;
    @ApiModelProperty("用户生日")
    private String birthday;
    @ApiModelProperty("用户性别，0-男，1-女")
    private int sex;
    @ApiModelProperty("个性签名")
    private String signature;
    @ApiModelProperty("用户头像id")
    private Integer portrait_id;
    @ApiModelProperty("用户账号状态，0-正常，1-冻结")
    private Integer status;
    @ApiModelProperty("用户所在地")
    private String place;
    @ApiModelProperty("cc币数量")
    private Integer ccNum;
    @ApiModelProperty("用户头像")
    private String url;
    @ApiModelProperty("用户个人主页")
    private String selfIndex;
}
