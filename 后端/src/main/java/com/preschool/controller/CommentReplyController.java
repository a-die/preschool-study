package com.preschool.controller;


import com.preschool.pojo.CommentReply;
import com.preschool.pojo.CommentReplyUser;
import com.preschool.pojo.User;
import com.preschool.service.ReplyService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/comment-reply")
@CrossOrigin
public class CommentReplyController {
    @Autowired
    ReplyService replyService;

    /**
     * 获得某条评论下的所有回复
     * @param comment_id
     * @return java.util.List<com.preschool.pojo.Comment_reply>
     */
    @ApiOperation("获得某条评论下的所有回复")
    @PostMapping("/showReplies")
    public List<CommentReplyUser> showReplies(int comment_id){
        List<CommentReplyUser> replies=replyService.queryAllById(comment_id);
        return  replies;
    }

    /**
     * 在评论下面回复或是回复评论的回复
     * @param reply
     * @param request
     * @return void
     */
    @ApiOperation("在评论下面回复或是回复评论的回复")
    @PostMapping("/reply")
    public Result reply(CommentReply reply, HttpServletRequest request){
        //设置 谁回复（user_id_from）
//        reply.setUser_id_from(((User)request.getSession().getAttribute("user_logined")).getId());
        int i = replyService.addReply(reply);
        if(i>=1)return Result.ok("评论的回复或者回复的回复发送成功");
        else return Result.error("评论的回复或者回复的回复发送失败");
    }

    @ApiOperation("删除回复")
    @PostMapping("/deleteOneReply")
    public Result deleteOneReply(int id){
        int i = replyService.deleteOneReply(id);
        if(i>=1)return Result.ok("删除回复成功");
        else return Result.ok("删除回复失败");
    }

}

