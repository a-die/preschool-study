package com.preschool.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.preschool.pojo.User;
import com.preschool.service.UserService;
import com.preschool.utils.MD5;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;
    
    /**
        * 判断账号密码是否正确,如果登录成功，通过手机查询到当前登录用户保存到Session域中
        * @param phonenumber
    	* @param password
    	* @param response
        * @return int
    */
    @PostMapping("/login")
    @ApiOperation("判断账号密码是否正确,如果登录成功，通过手机查询到当前登录用户保存到Session域中")
    public Result login(@ApiParam("电话")String phonenumber,@ApiParam("密码") String password, HttpServletResponse response,HttpServletRequest request) throws IOException {
        MD5 md5 = new MD5();

        User user=userService.queryByPhoneAndPassword(phonenumber,md5.getMD5ofStr(password));
        if(user==null) return Result.error("账号或密码错误");
        User user_logined=userService.queryByPhone(phonenumber);
//        request.getSession().setAttribute("user_logined",user_logined);
        return Result.ok(user_logined);
    }
    
    /**
        * 添加一个用户
        * @param user
        * @return void
    */
    @PostMapping("/register")
    @ApiOperation("注册")
    public Result register(@ApiParam("注册的用户信息")User user){
        int i = userService.addUser(user);
        if(i==1)return Result.ok();
        else return Result.error("注册失败");
    }


    /**
        * 用于前端检查手机号是否被注册过
        * @param phonenumber
        * @return int
    */
    @PostMapping("/checkPhone")
    @ApiOperation("用于前端检查手机号是否被注册过")
    public Result checkPhone(@ApiParam("用户手机号")String phonenumber){
        User user = userService.queryByPhone(phonenumber);
        if(user==null) return Result.ok();
        return Result.error("该账号已被注册");
    }

    /**
     * 展示用户个人信息
     * @param id
     * @return com.preschool.pojo.User
     */
    @ApiOperation("展示用户个人信息")
    @PostMapping("/information")
    public User showInformation(int id){
        return userService.queryById(id);
    }


    /**
     * 修改用户个人信息
     * @param user
     * @return void
     */
    @ApiOperation("修改用户个人信息")
    @PostMapping("/update")
    public Result updateInformation(User user){
        int i = userService.updateUser(user);
        if(i>=1)return Result.ok();
        else return Result.error("修改失败");
    }

    @PostMapping("/updateSelfIndex")
    public Result updateSelfIndex(int id,String selfIndex){
        User user = userService.queryById(id);
        user.setSelfIndex(selfIndex);
        if (userService.updateUser(user)>=1)return Result.ok();
        return Result.error();
    }

    /**
        * @param request
        * @return com.preschool.utils.entity.Result
    */
    @ApiOperation("注销账户")
    @PostMapping("/logout")
    public Result logOut(HttpServletRequest request){
        int id=((User) request.getSession().getAttribute("user_logined")).getId();
        int i = userService.deleteUser(id);
        if(i>=1)return Result.ok("删除成功");
        else return Result.error("删除失败");
    }

    @ApiOperation("展示所有用户信息")
    @GetMapping("/showAll")
    public List<User> showAll(){
        return userService.queryAll();
    }

    @ApiOperation("冻结用户")
    @PostMapping("/freeze")
    public Result freezeUser(int id){
        int i = userService.updateStatus(id);
        if(i==1)return Result.ok("成功冻结");
        else return Result.error("冻结失败");
    }

    @ApiOperation("发表文章增加CC币")
    @PostMapping("/addCCNum")
    /*
        发表文章增加CC币,用来测试
        * @param UserId
        * @return com.preschool.utils.entity.Result
    */
    public Result addCCNum(int userId){
        User user = userService.getById(userId);
        user.setCcNum(user.getCcNum()+1);
        if(userService.updateById(user))return Result.ok();
        return Result.error();
    }


    /*=================管理端新增的接口请求========================*/
    //管理端所用的接口
    @PostMapping("/showAllByPage")
    public List<User>showUsersByPage(int page,int size){

        return userService.showUsersByPage(page, size);
    }

    @PostMapping("/updateSomeById")
    public Result updateSomeById(int id,String username,String phonenumber,String email){
        int i = userService.updateSomeById(id, username, phonenumber, email);
        if(i>=1)return Result.ok("修改成功");
        else return Result.error("修改失败");
    }

    @PostMapping("/getUserListByName")
    public List<User>getUserListByName(String username){
        return userService.getUserListByName(username);
    }

   /**
       * 获取用户总数
       * @param
       * @return int
   */
    @ApiOperation("获取用户总数")
    @GetMapping("/getUserNum")
    public int getUserNum(){
        return userService.getUserNum();
    }

    @PostMapping("/updateStatus")
    public Result updateStatus(int id,int status){
        int i = userService.updateStatus(id, status);
        if(i>=1)return Result.ok("帐号状态更新成功");
        else return Result.error("帐号状态更新失败");
    }
}
