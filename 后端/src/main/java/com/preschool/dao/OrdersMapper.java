package com.preschool.dao;

import com.preschool.pojo.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.preschool.pojo.OrdersDiscussPostUser;
import com.preschool.pojo.ReportingInformation;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author a碟
 * @since 2021-12-11
 */
@Repository
public interface OrdersMapper extends BaseMapper<Orders> {
    List<OrdersDiscussPostUser> getAllOrders();
    List<OrdersDiscussPostUser> getOrdersByUserId(int userId);
    List<Orders> getAll(int user_id);

    int clearAll(int user_id);

    int clearSomePosts(Integer id);
}
