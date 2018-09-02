package com.tmall.service;

import com.tmall.pojo.Order;
import com.tmall.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

    void addOI(OrderItem orderItem);

    OrderItem getOI(Integer id);

    void deleteOI(Integer id);

    void updateOI(OrderItem orderItem);

    List<OrderItem> listByUid(Integer uid);

    void setProduct(OrderItem orderItem);

    void setProducts(List<OrderItem> orderItems);

    void fillOrder(Order order);

    void fillOrders(List<Order> orders);
}
