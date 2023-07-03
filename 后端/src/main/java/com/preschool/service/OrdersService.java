package com.preschool.service;

import com.preschool.pojo.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.preschool.pojo.OrdersDiscussPostUser;
import com.preschool.pojo.ReportingInformation;
import com.preschool.utils.entity.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author a碟
 * @since 2021-12-11
 */
public interface OrdersService extends IService<Orders> {
    public List<OrdersDiscussPostUser>getAllOrders();
    public List<OrdersDiscussPostUser> getOrdersByUserId(int userId);
    List<Orders> getAll(int user_id);

    int clearAll(int user_id);

    boolean updateReaded(int id);
}
