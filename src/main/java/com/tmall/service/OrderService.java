package com.tmall.service;

import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderService {

    void addOrder(Order order);

    void deleteOrder(Integer id);

    void updateOrder(Order order);

    Order getOrder(Integer id);

    float add(Order order, List<OrderItem> orderItems);

    List<Order> listByUid(Integer uid, String status);

    List<Order> list();
}
