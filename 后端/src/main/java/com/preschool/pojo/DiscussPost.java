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
CREATE TABLE `discuss_post` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int NOT NULL COMMENT '发表该博客的用户id',
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '内容',
  `type` int DEFAULT NULL COMMENT '0-普通，1-置顶',
  `status` int DEFAULT NULL COMMENT '0-正常，1-精华，2-拉黑',
  `create_time` timestamp NOT NULL COMMENT '创建时间',
  `comment_count` int DEFAULT NULL COMMENT '评论数量',
  `up_count` int DEFAULT NULL COMMENT '点赞数量',
  `btype` int DEFAULT NULL COMMENT '0-笔记，1-踩坑记录，2-提问',
  `subject` int DEFAULT NULL COMMENT '学科类型id',
  PRIMARY KEY (`id`),
  KEY `user_id_fk` (`user_id`),
  KEY `subject_fk` (`subject`),
  CONSTRAINT `subject_fk` FOREIGN KEY (`subject`) REFERENCES `subject` (`id`),
  CONSTRAINT `user_id_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3

 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("笔记/踩坑记录/提问实体类")
public class DiscussPost implements Serializable {
    @ApiModelProperty("笔记/踩坑记录/提问的id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("发表该博客的用户id")
    private Integer user_id;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("内容")
    private String content;
    @ApiModelProperty("0-普通，1-置顶")
    private int type;
    @ApiModelProperty("0-正常，1-精华，2-拉黑")
    private int status;
    @ApiModelProperty("创建时间")
    private Timestamp create_time;
    @ApiModelProperty("修改时间")
    private Timestamp update_time;
    @ApiModelProperty("评论数量")
    private int comment_count;
    @ApiModelProperty("点赞数量")
    private int up_count;
    @ApiModelProperty("0-笔记，1-踩坑记录，2-提问")
    private int btype;
    @ApiModelProperty("学科类型")
    private int subject;
    @ApiModelProperty("所对应的专栏id，非笔记类默认值为0")
    private Integer column_id;
    @ApiModelProperty("博文审核情况 0-审核通过，1-审核未通过")
    private Integer identify;
    @ApiModelProperty("该博文的链接，方便后台核查举报信息")
    private String url;
    @ApiModelProperty("1-免费，0-付费")
    private int isFree;
    @ApiModelProperty("需要的cc币数量")
    private int price;
}
