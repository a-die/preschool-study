package com.preschool.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/*
CREATE TABLE `comment_reply` (
  `id` int NOT NULL COMMENT '评论的回复的id',
  `user_id_from` int DEFAULT NULL COMMENT '谁回复',
  `user_id_to` int DEFAULT NULL COMMENT '回复谁',
  `content` text COMMENT '回复的内容',
  `create_time` timestamp NULL DEFAULT NULL COMMENT '回复时间',
  `comment_id` int DEFAULT NULL COMMENT '所属评论的id',
  PRIMARY KEY (`id`),
  KEY `user_id_fk1` (`user_id_from`),
  KEY `user_id_fk2` (`user_id_to`),
  KEY `comment_id_fk` (`comment_id`),
  CONSTRAINT `comment_id_fk` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`),
  CONSTRAINT `user_id_fk1` FOREIGN KEY (`user_id_from`) REFERENCES `user` (`id`),
  CONSTRAINT `user_id_fk2` FOREIGN KEY (`user_id_to`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("评论的回复实体类")
public class CommentReplyUser implements Serializable {
    @ApiModelProperty("评论的回复的id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("谁回复")
    private int user_id_from;
    @ApiModelProperty("回复谁")
    private int user_id_to;
    @ApiModelProperty("回复的内容")
    private String content;
    @ApiModelProperty("回复时间")
    private Timestamp create_time;
    @ApiModelProperty("所属评论的id")
    private Integer comment_id;
    @ApiModelProperty("回复人姓名")
    private String usernameFrom;
    @ApiModelProperty("被回复人姓名")
    private String usernameTo;
    @ApiModelProperty("回复人头像")
    private String userUrlFrom;
    @ApiModelProperty("被回复人头像")
    private String userUrlTo;
}
