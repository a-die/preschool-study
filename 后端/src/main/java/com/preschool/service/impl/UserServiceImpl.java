package com.preschool.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.preschool.dao.UserMapper;
import com.preschool.pojo.User;
import com.preschool.service.UserService;
import com.preschool.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
        * @param phonenumber
    	* @param password
        * @return com.preschool.pojo.User
    */
    
    @Override
    public User queryByPhoneAndPassword(String phonenumber, String password) {
        return userMapper.queryByPhoneAndPassword(phonenumber,password);
    }

    /**
        * @param user
        * @return void
    */
    
    @Override
    public int addUser(User user) {
        user.setPortrait_id(0);
        user.setType(0);
        user.setStatus(0);
        //md5 加密处理
        MD5 md5 = new MD5();
        user.setPassword(md5.getMD5ofStr(user.getPassword()));
        int insert = userMapper.insert(user);
        return insert;
    }

    /**
        * @param phonenumber
        * @return com.preschool.pojo.User
    */
    @Override
    public User queryByPhone(String phonenumber) {
        return userMapper.queryByPhone(phonenumber);
    }

    @Override
    public User queryById(int id) {
        return userMapper.queryById(id);
    }

    @Override
    public int updateUser(User user) {
        int i = userMapper.updateUser(user);
        return i;
    }

    @Override
    public int deleteUser(int id) {
        int i = userMapper.deleteById(id);
        return i;
    }

    @Override
    public List<User> queryAll() {
        return userMapper.selectList(null);
    }

    @Override
    public int updateStatus(int id) {
        int i = userMapper.updateStatus(id);
        return i;
    }

    @Override
    public List<User>showUsersByPage(int page,int size){
        Page<User> now= new Page<>(page,size);
        userMapper.selectPage(now, null);

        List<User> records = now.getRecords();
        now.getRecords().forEach(System.out::println);
        return records;
    }

    @Override
    public List<User> getUserListByName(String username) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("username",username);
        return userMapper.selectList(wrapper);
    }

    @Override
    public int updateSomeById(int id,String username,String phonenumber,String email) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPhonenumber(phonenumber);
        user.setEmail(email);
        int i = userMapper.updateById(user);
        return i;
    }

    @Override
    public int getUserNum() {
        return userMapper.getUserNum();
    }

    @Override
    public int updateStatus(int id, int status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        int i = userMapper.updateById(user);
        return i;
    }


}
