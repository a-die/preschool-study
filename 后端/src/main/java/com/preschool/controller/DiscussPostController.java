package com.preschool.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.DiscussPostUser;
import com.preschool.pojo.SupportBlog;
import com.preschool.pojo.User;
import com.preschool.service.DiscussPostService;
import com.preschool.service.SupportBlogService;
import com.preschool.service.UserService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/discussPost")
@CrossOrigin
public class DiscussPostController {
    @Autowired
    DiscussPostService discussPostService;
    @Autowired
    SupportBlogService supportBlogService;
    @Autowired
    UserService userService;

    /**
        * 随便看看，显示某博客类型下所有博客信息
        * @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    @ApiOperation("随便看看，显示某博客类型下所有博客信息")
    @PostMapping("/showAll")
    //@ApiParam("0-笔记，1-踩坑记录，2-提问")
    public List<DiscussPostUser> showAllPosts(int btype){
        List<DiscussPostUser> discuss_posts=discussPostService.queryByBtype(btype);
        return discuss_posts;
    }

//    @ApiOperation("根据学科id获得相应的文章")
//    public Result getAllDiscussPostBySubject(int subject){
//
//    }

    /**
        * 显示某学科下相应的博客信息
        * @param subject
    	* @param btype
        * @return java.util.List<com.preschool.pojo.Discuss_post>
    */
    @ApiOperation("显示某学科下相应的博客信息")
    @PostMapping("/showPosts")
    public List<DiscussPostUser> showPosts(@ApiParam("学科类型id")int subject, @ApiParam("0-笔记，1-踩坑记录，2-提问") int btype){
        List<DiscussPostUser> discuss_posts = discussPostService.queryBySubjectAndBtype(subject, btype);
        return discuss_posts;
    }

    /**
        * 获取某博文类型下的所有博文数
        * @param btype
        * @return int
    */
    @ApiOperation("获取某博文类型下的所有博文数")
    @PostMapping("/getPostNumByBtype")
    public int queryCountByBtype(int btype){
        return discussPostService.queryCountByBtype(btype);
    }

    @ApiOperation("获得某博文类型下的所有评论及回复")
    @PostMapping("/getCommentAndReplyNumByBtype")
    public int getCommentAndReplyNumByBtype(int btype){
        return discussPostService.queryCommentAndReplyCount(btype);
    }

    @PostMapping("/calculateTheSimilarity")
    public Result calculateTheSimilarity(String content){
        if(!discussPostService.calculateTheSimilarity(content))return Result.error("存在抄袭嫌疑,请修改后重新提交");
        else return Result.ok("正常");
    }

    /**
        * 添加博文
        * @param discuss_post
        * @return void
    */
    @ApiOperation("添加博文")
    @PostMapping("/addPost")
    @Transient
    public Result addPost(@ApiParam("一条博文记录") DiscussPost discuss_post){
        System.out.println(discuss_post);
        if(!discussPostService.calculateTheSimilarity(discuss_post.getContent()))return Result.error(123,"存在抄袭嫌疑,请修改后重新提交");
        if (discussPostService.checkLegal(discuss_post.getContent())){//没有敏感词汇，自动过审
            discuss_post.setIdentify(0);
        }
        discussPostService.addDiscussPost(discuss_post);
        User user = userService.getById(discuss_post.getUser_id());
        user.setCcNum(user.getCcNum()+1);
        if(userService.updateById(user))return Result.ok();
        return Result.error();
    }

    /**
        * 查看某一条博文
        * @param id
        * @return com.preschool.pojo.Discuss_post
    */
    @ApiOperation("查看某一条博文")
    @PostMapping("/showOnePost")
    public DiscussPostUser showOnePost(int id){
        DiscussPostUser discuss_post = discussPostService.queryById(id);
        return discuss_post;
    }

    /**
        * 修改博文
        * @param discuss_post
        * @return void
    */
    @ApiOperation("修改博文")
    @PostMapping("/updateOnePost")
    public Result updateOnePost(DiscussPost discuss_post){
        int i = discussPostService.updateOnePost(discuss_post);

        if(i>=1)return Result.ok("修改成功");
        else return Result.error("修改失败");
    }

    /**
        * 删除博文
        * @param id
        * @return void
    */
    @ApiOperation("删除博文")
    @PostMapping("/deleteOnePost")
    public Result deleteOnePost(int id){
        int i = discussPostService.deleteOnePost(id);
        if(i>=1)return Result.ok("删除成功");
        else return Result.error("删除失败");
    }


    /**
        *
        * @param id
    	* @param column_id
        * @return void
    */
    @ApiOperation("添加博文至某专栏")
    @PostMapping("/addToColumn")
    public Result addToColumn(int id,int column_id){
        int i = discussPostService.addPostToColumn(id, column_id);
        if(i>=1)return Result.ok("添加成功");
        else return Result.error("添加失败");
    }


    /*
     * @param DiscussPostId
     * @param userId
     * @param op
     * @return com.preschool.utils.entity.Result
     */
    @ApiOperation("点赞或取消点赞，同时更新点赞表")
    @PostMapping("/updateUpNum")
    @Transient
    public Result up(int discussPostId,int userId,int op){
        DiscussPost discuss_post = discussPostService.queryByDiscussPostId(discussPostId);
        discuss_post.setUp_count(discuss_post.getUp_count()+op);


        SupportBlog supportBlog = new SupportBlog();
        supportBlog.setDiscussPostId(discussPostId);
        supportBlog.setUserId(userId);

        if(op==1)supportBlogService.save(supportBlog);
        else{
            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("discussPostId",discussPostId);
            columnMap.put("userId",userId);
            supportBlogService.removeByMap(columnMap);
        }


        int i = discussPostService.updateOnePost(discuss_post);
        if(i>=1)return Result.ok("点赞数量更新成功");
        else return Result.error("点赞数量更新失败");
    }


    @ApiOperation("按照审核状态展示个人发表的所有博文")
    @PostMapping("/showAllPosts")
    /*
         按照审核状态展示个人发表的所有博文
        * @param id
        * @return java.util.List<com.preschool.pojo.DiscussPost>
    */
    public List<DiscussPost> showPersonalPosts(int id,int identify){
        List<DiscussPost> discuss_posts = discussPostService.queryByUserId(id,identify);
        return discuss_posts;
    }

    @ApiOperation("展示个人发表的某博文类型下的所有博文")
    @PostMapping("/showSomePosts")
    /*
          展示个人发表的某博文类型下的所有博文
        * @param id
    	* @param btype
        * @return java.util.List<com.preschool.pojo.DiscussPost>
    */
    public List<DiscussPost> showPersonalPostsByBtype(int id,int btype){
//        int id=((User) request.getSession().getAttribute("user_logined")).getId();
        List<DiscussPost> discuss_posts = discussPostService.queryByUserIdAndBtype(id,btype);
        return discuss_posts;
    }

    /**
     * 清空个人所有文章
     * @param user_id
     * @return void
     */
    @ApiOperation("清空个人所有文章")
    @PostMapping("/clearAll")
    public Result clearAllPosts(int user_id){
        int i = discussPostService.deleteUserAllPosts(user_id);
        if(i>=1)return Result.ok("清空成功");
        else return Result.error("清空失败");
    }

    /**
     * 清空个人某一博文类型下的所有文章
     * @param user_id
     * @param btype
     * @return void
     */
    @ApiOperation("清空个人某一博文类型下的所有文章")
    @PostMapping("/clearSome")
    public Result clearSomePosts(int user_id,int btype){
        int i = discussPostService.deleteUserSomePosts(user_id, btype);
        if(i>=1)return Result.ok("清空成功");
        else return Result.error("清空失败");
    }

    @ApiOperation("根据算法推荐文章")
    @PostMapping("/getDiscussPostCommended")
    public List<DiscussPost> getDiscussPostCommended(int userId){
        return discussPostService.getDiscussPostCommended(userId);
    }

    @ApiOperation("根据专栏id查找文章")
    @PostMapping("/getDiscussPostByColumnId")
    public Result getDiscussPostByColumnId(int columnId){
        return Result.ok(discussPostService.getDiscussPostByColumnId(columnId));
    }

    @ApiOperation("根据博文类型 标题和内容 模糊搜索博文")
    @PostMapping("/getDiscussPostFuzzyByBtype")
    public Result getDiscussPostFuzzyByBtype(int btype,String content){
        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        //根据标题模糊查
        wrapper.like("title",content)
                .eq("btype",btype);
        List<DiscussPost> list = discussPostService.list(wrapper);
        wrapper.clear();
        //根据内容模糊查
        wrapper.like("content",content)
                .eq("btype",btype);
        List<DiscussPost>list1 = discussPostService.list(wrapper);
        list.addAll(list1);
        //去重
        list = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DiscussPost::getId))), ArrayList::new)
        );
        return Result.ok(list);

    }

    @ApiOperation("根据标题和内容 模糊搜索博文")
    @PostMapping("/getDiscussPostFuzzy")
    public Result getDiscussPostFuzzy(String content){
        QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
        //根据标题模糊查
        wrapper.like("title",content);
        List<DiscussPost> list = discussPostService.list(wrapper);
        wrapper.clear();
        //根据内容模糊查
        wrapper.like("content",content);
        List<DiscussPost> list1 = discussPostService.list(wrapper);
        list.addAll(list1);
        list = list.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(DiscussPost::getId))), ArrayList::new)
        );

        return Result.ok(list);
    }

    //获得用户个人的已经审核通过的文章数量
    @PostMapping("/getBlogNumByUserId")
    public Result getBlogNumByUserId(int userId){
        return Result.ok(discussPostService.getBlogNumByUserId(userId));
    }

    /*=================管理端新增的接口请求========================*/
    //管理端所用的接口

    @ApiOperation("获得未审核的博文")
    @PostMapping("/getBlogByIdentify")
    public List<HashMap<String, Object>> getBlogByIdentify(int page,int size,int identify){
        Page<DiscussPost>now = new Page<>(page,size);
        return discussPostService.getBlogByIdentify(now,identify);
    }

    @PostMapping("/getBlogNumByIdentify")
    public int getBlogNumByIdentify(int identify){
        return discussPostService.getBlogNumByIdentify(identify);
    }

    @PostMapping("/getDiscussPostListByTitle")
    public List<HashMap<String,Object>> getDiscussPostListByTitle(String title,int identify){
        return discussPostService.getDiscussPostListByTitle(title,identify);
    }

    //通过审核，即通过id更新审核一个字段即可
    @PostMapping("/setIdentify")
    public Result setIdentify(Integer id,Integer identify){
        int i = discussPostService.setIdentify(id, identify);
        if(i>=1)return Result.ok("通过审核");
        else return Result.error("通过审核失败");
    }

    // 更新博文置顶状态
    @PostMapping("/updateBlogType")
    public Result updateBlogType(int id,int type){
        int i = discussPostService.updateBlogType(id, type);
        if(i>=1)return Result.ok("博文置顶状态更新成功");
        else return Result.error("博文置顶状态更新失败");
    }

    @ApiOperation("获取所有博文总数")
    @GetMapping("/getDiscussPostNum")
    public int getDiscussPostNum(){
        return discussPostService.getDiscussPostNum();
    }
}
