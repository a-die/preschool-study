package com.preschool.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.SupportBlog;
import com.preschool.pojo.User;

import java.util.List;

public interface UserService extends IService<User> {

    /**
        * @param phonenumber
    	* @param password
        * @return com.preschool.pojo.User
    */
    User queryByPhoneAndPassword(String phonenumber,String password);

    /**
        * @param user
        * @return void
    */
    int addUser(User user);

    /**
        * @param phonenumber
        * @return com.preschool.pojo.User
    */
    User queryByPhone(String phonenumber);

    /**
        * @param id
        * @return com.preschool.pojo.User
    */
    User queryById(int id);

    /**
     * @param user
     * @return void
     */
    int updateUser(User user);

    /**
        * @param id
        * @return void
    */
    int deleteUser(int id);

    /**
        * @param
        * @return java.util.List<com.preschool.pojo.User>
    */
    List<User> queryAll();

    /**
        * @param id
        * @return void
    */
    int updateStatus(int id);

    List<User>showUsersByPage(int page,int size);

    List<User>getUserListByName(String username);

    int updateSomeById(int id,String username,String phonenumber,String email);

    int getUserNum();

    int updateStatus(int id,int status);
}
