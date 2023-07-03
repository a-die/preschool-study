package com.preschool.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.Comment;
import com.preschool.pojo.CommentUser;
import com.preschool.pojo.User;
import com.preschool.service.CommentService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Autowired
    CommentService commentService;
    /**
     * 评论
     * @param comment
     * @param request
     * @return void
     */
    @ApiOperation("评论")
    @PostMapping("/comment")
    public Result comment(Comment comment, HttpServletRequest request){
        //设置 谁评论（userIdFrom）
//        comment.setUserIdFrom(((User)request.getSession().getAttribute("user_logined")).getId());
        int i = commentService.addComment(comment);
        if(i>=1)return Result.ok("评论成功");
        else return Result.error("评论失败");
    }

    /**
     * 获得某一博文下所有评论
     * @param discussPostId
     * @return java.util.List<com.preschool.pojo.Comment>
     */
    @ApiOperation("获得某一博文下所有评论")
    @PostMapping("/showComments")
    public List<CommentUser> showComments(int discussPostId){
        List<CommentUser> comments = commentService.queryAllById(discussPostId);
        return comments;
    }


    /*
        * @param null
        * @return 
    */
    @ApiOperation("删除评论")
    @PostMapping("/deleteOneComment")
    public Result deleteOneComment(int id){
        int i = commentService.deleteOneComment(id);
        if(i>=1)return Result.ok("删除评论成功");
        else return Result.error("删除评论失败");
    }

    @PostMapping("/getCommentNumByUserId")
    public Result getCommentNumByUserId(int userId){
        return Result.ok(commentService.getCommentNumByUserId(userId));
    }

    /*=================管理端新增的接口请求========================*/

    /*
        * @param null
        * @return 
    */
    
    @ApiOperation("获得所有博文下的评论,分页查询")
    @PostMapping("/ShowAllCommentsByPage")
    public List<Map<String,Object>>showAllCommentsByPage(int page, int size){
        Page<Comment> now = new Page<>(page,size);
        List<Map<String,Object>> comments = commentService.ShowAllCommentsByPage(now);
        return comments;
    }

    /*
        * @param null
        * @return 
    */
    
    @ApiOperation("获得所有博文下的评论的数量")
    @GetMapping("/getCommentNum")
    public int getCommentNum(){
        return commentService.getCommentNum();
    }

    /*
        * @param null
        * @return 
    */
    
    @ApiOperation("根据用户名模糊查找对应的回复信息")
    @PostMapping("/showCommentsByUsername")
    public List<Map<String,Object>>showCommentsByUsername(String username){
        List<Map<String, Object>> maps = commentService.showCommentsByUsername(username);
        return maps;
    }

}

