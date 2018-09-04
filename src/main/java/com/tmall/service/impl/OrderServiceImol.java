package com.tmall.service.impl;

import com.tmall.dao.OrderMapper;
import com.tmall.pojo.Order;
import com.tmall.pojo.OrderExample;
import com.tmall.pojo.OrderItem;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderServiceImol implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemService orderItemService;

    @Override
    public void addOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public void deleteOrder(Integer id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order getOrder(Integer id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public float add(Order order, List<OrderItem> orderItems) {
//        总金额
        float total = 0;
        addOrder(order);

        if (false)
            throw new RuntimeException();

        for (OrderItem orderItem : orderItems) {
            orderItem.setOid(order.getId());
            orderItemService.updateOI(orderItem);
            total += orderItem.getProduct().getPromoteprice() * orderItem.getNumber();
        }
        return total;
    }

    @Override
    public List<Order> listByUid(Integer uid, String status) {
        OrderExample example = new OrderExample();
        example.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(status);
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

    @Override
    public List<Order> list() {
        OrderExample example = new OrderExample();
        example.setOrderByClause("id desc");
        return orderMapper.selectByExample(example);
    }

}
