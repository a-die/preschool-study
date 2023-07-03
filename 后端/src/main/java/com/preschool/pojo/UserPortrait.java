package com.preschool.pojo;
/*
CREATE TABLE `user_portrait` (
  `id` int NOT NULL COMMENT '头像id，使用java UUid获得',
  `url` varchar(200) NOT NULL COMMENT '头像图片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("用户头像实体类")
public class UserPortrait implements Serializable {
    @ApiModelProperty("用户头像id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("用户头像地址")
    private String url;
}
