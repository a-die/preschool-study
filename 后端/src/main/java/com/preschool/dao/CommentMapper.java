package com.preschool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.Comment;

import com.preschool.pojo.*;

import com.preschool.pojo.CommentUser;
import com.preschool.utils.entity.Result;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    /**
        * 添加一条评论记录
        * @param comment
        * @return void
    */
    void addComment(Comment comment);

    /**
        * 根据某一博文id查询该博文下的所有评论(发表时间由早到晚排序)
        * @param discussPostId
        * @return java.util.List<com.preschool.pojo.Comment>
    */
    List<CommentUser> queryAllById(int discussPostId);

    /**
        * 根据id查到某一条评论
        * @param id
        * @return com.preschool.pojo.Comment
    */
    Comment queryById(int id);

    List<Map<String,Object>>ShowAllCommentsByPage(Page<Comment> now);

    int getCommentNum();

    List<Map<String,Object>>showCommentsByUsername(String username);

    /**
     * 按月统计评论数量
     * @param
     * @return java.util.List<com.preschool.pojo.Trend>
     */
    List<CountPerMonth> queryCountPerMonth();

    int getCommentNumByDiscussPostId(int discussPostId);
}
