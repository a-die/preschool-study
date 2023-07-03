package com.preschool.pojo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
CREATE TABLE `column_self` (
  `id` int NOT NULL COMMENT '专栏id，使用javaUUID获得',
  `name` varchar(100) DEFAULT NULL COMMENT '专栏名字',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  KEY `user_id_column_fk` (`user_id`),
  CONSTRAINT `user_id_column_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("个人的专栏")
public class ColumnSelf implements Serializable {
    @ApiModelProperty("专栏id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("专栏名字")
    private String name;
    @ApiModelProperty("该专栏的创建者id")
    private Integer user_id;
}
