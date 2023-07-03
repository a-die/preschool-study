package com.preschool.service.impl;

import com.preschool.dao.DiscussPostMapper;
import com.preschool.pojo.DiscussPost;
import com.preschool.pojo.Orders;
import com.preschool.dao.OrdersMapper;
import com.preschool.pojo.OrdersDiscussPostUser;
import com.preschool.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author a碟
 * @since 2021-12-11
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

    @Autowired
    OrdersMapper ordersMapper;
    @Autowired
    DiscussPostMapper discussPostMapper;

    @Override
    public List<OrdersDiscussPostUser> getAllOrders() {
        return ordersMapper.getAllOrders();
    }

    @Override
    public List<OrdersDiscussPostUser> getOrdersByUserId(int userId) {
        return ordersMapper.getOrdersByUserId(userId);
    }

    @Override
    public List<Orders> getAll(int user_id) {
        return ordersMapper.getAll(user_id);
    }

    @Override
    public int clearAll(int user_id) {
        List<DiscussPost> discussPosts = discussPostMapper.queryByUserId(user_id, 0);
        for(DiscussPost discussPost:discussPosts){
            ordersMapper.clearSomePosts(discussPost.getId());
        }
        return 1;
    }

    @Override
    public boolean updateReaded(int id) {
        Orders orders = ordersMapper.selectById(id);
        orders.setReaded(1);
        return ordersMapper.updateById(orders) >= 1;
    }
}
