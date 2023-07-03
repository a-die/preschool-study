package com.preschool.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.CommentReply;
import com.preschool.pojo.CommentReplyUser;

import java.util.List;

public interface ReplyService extends IService<CommentReply> {
    /**
        * @param comment_id
        * @return java.util.List<com.preschool.pojo.Comment_reply>
    */
    List<CommentReplyUser> queryAllById(int comment_id);

    /**
        * @param reply
        * @return void
    */
    int addReply(CommentReply reply);

    /*
        * @param null
        * @return
    */
    int deleteOneReply(int id);
}
