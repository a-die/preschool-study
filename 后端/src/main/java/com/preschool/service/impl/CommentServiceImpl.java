package com.preschool.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.CommentMapper;
import com.preschool.dao.ReplyMapper;
import com.preschool.pojo.Comment;
import com.preschool.pojo.CommentUser;
import com.preschool.pojo.DiscussPost;
import com.preschool.service.CommentService;
import com.preschool.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>  implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    DiscussPostService discussPostService;
    @Autowired
    ReplyMapper replyMapper;
    /**
        * @param comment
        * @return void
    */
    @Override
    public int addComment(Comment comment) {
        commentMapper.insert(comment);
        //commentMapper.addComment(comment);
        //评论数+1
        DiscussPost discuss_post = discussPostService.queryByDiscussPostId(comment.getDiscussPostId());
        discuss_post.setComment_count(discuss_post.getComment_count()+1);
        int i = discussPostService.updateOnePost(discuss_post);
        return i;
    }

    /**
        * @param discussPostId
        * @return java.util.List<com.preschool.pojo.Comment>
    */
    @Override
    public List<CommentUser> queryAllById(int discussPostId) {
        return commentMapper.queryAllById(discussPostId);
    }

    /**
        * @param id
        * @return com.preschool.pojo.Comment
    */
    @Override
    public Comment queryById(int id) {
        return commentMapper.queryById(id);
    }

    /**
        * @param id
        * @return void
    */
    @Override
    public int deleteOneComment(int id) {
        replyMapper.deleteByCommentId(id);//先删除评论的回复，外键约束
        int i = commentMapper.deleteById(id);
        return i;
    }

    @Override
    public List<Map<String,Object>> ShowAllCommentsByPage(Page<Comment> now) {
        List<Map<String, Object>> maps = commentMapper.ShowAllCommentsByPage(now);
        return maps;
    }

    @Override
    public int getCommentNum() {
        int commentNum = commentMapper.getCommentNum();
        return commentNum;
    }

    @Override
    public List<Map<String, Object>> showCommentsByUsername(String username) {
        return commentMapper.showCommentsByUsername(username);
    }

    @Override
    public int getCommentNumByUserId(int userId) {
        //先获得该用户的所有文章
        int num=0;
        List<DiscussPost> discussPosts = discussPostService.queryByUserId(userId, 0);
        for (DiscussPost discussPost : discussPosts) {
            //根据博文计算该用户的评论数量
            int num_per = commentMapper.getCommentNumByDiscussPostId(discussPost.getId());
            num+=num_per;
        }
        return num;
    }


}
