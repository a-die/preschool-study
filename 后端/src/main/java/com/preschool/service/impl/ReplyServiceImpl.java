package com.preschool.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.ReplyMapper;
import com.preschool.pojo.Comment;
import com.preschool.pojo.CommentReply;
import com.preschool.pojo.CommentReplyUser;
import com.preschool.pojo.DiscussPost;
import com.preschool.service.CommentService;
import com.preschool.service.DiscussPostService;
import com.preschool.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImpl extends ServiceImpl<ReplyMapper, CommentReply>  implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    DiscussPostService discussPostService;
    @Autowired
    CommentService commentService;

    /**
        * @param comment_id
        * @return java.util.List<com.preschool.pojo.Comment_reply>
    */
    @Override
    public List<CommentReplyUser> queryAllById(int comment_id) {
        return replyMapper.queryAllById(comment_id);
    }

    /**
        * @param reply
        * @return void
    */
    @Override
    public int addReply(CommentReply reply) {
        replyMapper.insert(reply);
        //评论数+1
        //找到该回复所属评论
        Comment comment = commentService.queryById(reply.getComment_id());
        //根据评论的属性Discuss_post_id找到对应博客
        DiscussPost discuss_post = discussPostService.queryByDiscussPostId(comment.getDiscussPostId());
        discuss_post.setComment_count(discuss_post.getComment_count()+1);
        int i = discussPostService.updateOnePost(discuss_post);
        return i;
    }

    @Override
    public int deleteOneReply(int id) {
        int i = replyMapper.deleteById(id);
        return i;
    }

}
