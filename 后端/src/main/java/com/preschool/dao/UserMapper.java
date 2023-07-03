package com.preschool.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.Children;
import com.preschool.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
        * 根据手机号和密码判断用户是否正确登陆
        * @param phonenumber
    	* @param password
        * @return com.preschool.pojo.User
    */
    User queryByPhoneAndPassword(String phonenumber,String password);
    /**
        * 添加一个用户
        * @param user
        * @return void
    */
    void addUser(User user);

    /**
        * 判断用户手机号是否出现过
        * @param phonenumber
        * @return com.preschool.pojo.User
    */
    User queryByPhone(String phonenumber);

    /**
        * 根据用户id查到用户信息
        * @param id
        * @return com.preschool.pojo.User
    */
    User queryById(int id);

    /**
        * 更新个人信息
        * @param user
        * @return void
    */
    int updateUser(User user);

    /**
        * 修改用户账户状态
        * @param id
        * @return void
    */
    int updateStatus(int id);

    /**
        * @param
        * @return int
    */
    int getUserNum();

    /**
        * 按地区分组统计
        * @param
        * @return java.util.List<com.preschool.pojo.Children>
    */

    List<Children> queryGroup();
}
