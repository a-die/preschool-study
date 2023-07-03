package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/*
CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '评论id',
  `user_id_from` int NOT NULL COMMENT '谁评论',
  `content` text NOT NULL COMMENT '评论内容',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  `discuss_post_id` int DEFAULT NULL COMMENT '评论的博客/提问/坑点分享的id',
  PRIMARY KEY (`id`),
  KEY `discuss_post_id_fk` (`discuss_post_id`),
  CONSTRAINT `discuss_post_id_fk` FOREIGN KEY (`discuss_post_id`) REFERENCES `discuss_post` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论实体类")
public class Comment implements Serializable {
    @ApiModelProperty("评论id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("评论的博客/提问/坑点分享的id")
    private Integer discussPostId ;
    @ApiModelProperty("谁评论")
    private int userIdFrom;
    @ApiModelProperty("评论内容")
    private String content;
    @ApiModelProperty("发表时间")
    private Timestamp create_time;
}
