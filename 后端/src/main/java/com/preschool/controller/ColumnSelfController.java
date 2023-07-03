package com.preschool.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.preschool.pojo.ColumnSelf;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.DiscussPostUser;
import com.preschool.pojo.User;
import com.preschool.service.ColumnSelfService;
import com.preschool.service.DiscussPostService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-10-30
 */
@RestController
@RequestMapping("/column-self")
@CrossOrigin
public class ColumnSelfController {
    @Autowired
    ColumnSelfService columnSelfService;

    @Autowired
    DiscussPostService discussPostService;
    /**
     * 展示个人所创建的所有专栏
     * @return java.util.List<com.preschool.pojo.Column_self>
     */
    @ApiOperation("展示个人所创建的所有专栏")
    @PostMapping("/showAll")
    public List<ColumnSelf> showPersonalColumns(int id){
        List<ColumnSelf> columns = columnSelfService.queryByUserId(id);
        return columns;
    }

    /**
     * 展示某一专栏下的博文
     * @param column_id
     * @return java.util.List<com.preschool.pojo.Discuss_post>
     */
    @ApiOperation("展示某一专栏下的博文")
    @PostMapping("/showPosts")
    public List<DiscussPost> showColumnPosts(int column_id){
        List<DiscussPost> discuss_posts = columnSelfService.queryByColumnId(column_id);
        return discuss_posts;
    }

    /**
     * 新建专栏
     * @param column_self
     */
    @ApiOperation("新建专栏")
    @PostMapping("/add")
    public Result addColumn(ColumnSelf column_self){
        int i = columnSelfService.addColumn(column_self);
        if(i>=1)return Result.ok("新建专栏成功");
        else return Result.error("新建专栏失败");
    }

    /**
     * 修改专栏
     * @param column_self
     * @return void
     */
    @ApiOperation("修改专栏")
    @PostMapping("/update")
    public Result updateColumn(ColumnSelf column_self){
        int i = columnSelfService.updateColumn(column_self);
        if(i>=1)return Result.ok("修改成功");
        else return Result.error("修改失败");
    }

    /**
     * 仅删除专栏，同时需要将该专栏下的所有文章的专栏号修改成-1，即没有专栏
     * @param id
     * @return void
     */
    @ApiOperation("仅删除专栏")
    @PostMapping("/delColumn")
    public Result delColumn(int id){
        int i = columnSelfService.deleteColumn(id);
        if(i>=1)return Result.ok("删除成功");
        else return Result.error("删除失败");
    }

    /**
     * 清空某专栏，将该专栏下的所有文章的专栏号修改成-1，即没有专栏
     * @param column_id
     * @return void
     */
    @ApiOperation("仅删除专栏下的博文（清空某专栏）")
    @PostMapping("/delPosts")
    public Result delPosts(int column_id){
        int i = columnSelfService.deletePosts(column_id);
        if(i>=1)return Result.ok("删除成功");
        else return Result.error("删除失败");
    }

    /**
     * 删除专栏的同时删除博文
     * @param id
     * @return void
     */
    @ApiOperation("删除专栏的同时删除博文")
    @PostMapping("/delBoth")
    public Result delBoth(int id){
        int i = columnSelfService.deleteBoth(id);
        if(i>=1)return Result.ok("删除成功");
        else return Result.error("删除失败");
    }

    @ApiOperation("根据id查询专栏信息")
    @PostMapping("/getColumnSelfById")
    public Result getColumnSelfById(int id){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        return Result.ok(columnSelfService.listByMap(map));
    }
    @ApiOperation("给专栏添加文章")
    @PostMapping("/addArticlesForColumn")
    public Result addArticlesForColumn(String articles, int columnId, HttpServletRequest request){
        JSONArray jsonArray = JSON.parseArray(articles);
        for(int i=0;i<jsonArray.size();i++){
            Map<String, Object> map = new HashMap<>();
            map.put("id",jsonArray.getInteger(i));
            DiscussPost discussPost = discussPostService.queryOneById(jsonArray.getInteger(i));
            discussPost.setColumn_id(columnId);
            discussPostService.updateOnePost(discussPost);
        }

        return Result.ok();
    }
}

