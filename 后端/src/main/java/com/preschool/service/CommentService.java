package com.preschool.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.Comment;
import com.preschool.pojo.CommentUser;

import java.util.List;
import java.util.Map;

public interface CommentService extends IService<Comment> {
    /**
     * @param comment
     * @return void
     */
    int addComment(Comment comment);

    /**
        * @param discussPostId
        * @return java.util.List<com.preschool.pojo.Comment>
    */
    List<CommentUser> queryAllById(int discussPostId);

    /**
        * @param id
        * @return com.preschool.pojo.Comment
    */
    Comment queryById(int id);

    /**
        * @param id
        * @return void
    */
    int deleteOneComment(int id);

    List<Map<String,Object>>ShowAllCommentsByPage(Page<Comment> now);

    int getCommentNum();

    List<Map<String,Object>>showCommentsByUsername(String username);

    int getCommentNumByUserId(int userId);
}
