package com.preschool.controller;


import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.Orders;
import com.preschool.pojo.User;
import com.preschool.service.DiscussPostService;
import com.preschool.service.OrdersService;
import com.preschool.service.UserService;
import com.preschool.utils.entity.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.beans.Transient;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author a碟
 * @since 2021-12-11
 */
@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrdersController {
    @Autowired
    OrdersService ordersService;
    @Autowired
    UserService userService;
    @Autowired
    DiscussPostService discussPostService;

    @ApiOperation("获得所有的订单详细信息")
    @PostMapping("/getAllOrders")
    public Result getAllOrders(){
        return Result.ok(ordersService.getAllOrders());
    }

    @ApiOperation("增加一条新订单")
    @PostMapping("/addOrders")
    @Transient
    public Result addOrders(int userId,int discussPostId){
        //增加订单的同时，需要先判断用户的cc币是否足够，然后扣除购买者的cc币，增加被购买者的cc币，需要提醒被购买者
        User user = userService.queryById(userId);
        DiscussPost discussPost = discussPostService.queryByDiscussPostId(discussPostId);

        //判断cc币是否足够
        if(user.getCcNum()<discussPost.getPrice()){
            return Result.error(201,"cc币不足");
        }
        //购买者扣除cc币
        user.setCcNum(user.getCcNum()-discussPost.getPrice());
        userService.updateById(user);

        //被购买者增加cc币
        user = userService.queryById(discussPost.getUser_id());
        user.setCcNum(user.getCcNum()+discussPost.getPrice());
        userService.updateById(user);

        //提醒被购买者

        //添加订单
        Orders orders = new Orders();
        orders.setUserId(userId);
        orders.setDiscussPostId(discussPostId);
        orders.setPrice(discussPost.getPrice());
        if(ordersService.save(orders))return Result.ok();
        return Result.error();
    }

    @PostMapping("/getOrdersByUserId")
    @ApiOperation("根据用户id获得订单详细信息")
    public Result getOrdersByUserId(int userId){
        return Result.ok(ordersService.getOrdersByUserId(userId));
    }

    @PostMapping("/getAll")
    public Result getAll(int user_id){
        return Result.ok(ordersService.getAll(user_id));
    }

    @PostMapping("/clearAll")
    public Result clearAll(int user_id){
        if(ordersService.clearAll(user_id)>=1)
            return Result.ok("删除通知成功");
        else
            return Result.error("删除通知失败");
    }

    @PostMapping("/updateReaded")
    public Result updateReaded(int id){
        if(ordersService.updateReaded(id))return Result.ok();
        return Result.error();
    }


}

