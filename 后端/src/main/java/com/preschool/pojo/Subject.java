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

CREATE TABLE `subject` (
  `id` int NOT NULL COMMENT '学科类型id',
  `name` varchar(100) NOT NULL COMMENT '学科名称',
  `category` int DEFAULT NULL COMMENT '所属大类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("学科类型实体类")
public class Subject implements Serializable {
    @ApiModelProperty("学科类型id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("学科名称id")
    private String name;
    @ApiModelProperty("所属大类")
    private int category;
}
