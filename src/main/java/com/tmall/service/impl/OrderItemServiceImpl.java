package com.tmall.service.impl;

import com.tmall.dao.OrderItemMapper;
import com.tmall.pojo.OrderItem;
import com.tmall.pojo.OrderItemExample;
import com.tmall.pojo.Product;
import com.tmall.service.OrderItemService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ProductService productService;

    @Override
    public void addOI(OrderItem orderItem) {
        orderItemMapper.insertSelective(orderItem);
    }

    @Override
    public OrderItem getOI(Integer id) {
        OrderItem orderItem = orderItemMapper.selectByPrimaryKey(id);
        setProduct(orderItem);
        return orderItem;
    }

    @Override
    public void deleteOI(Integer id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateOI(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public List<OrderItem> listByUid(Integer uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProducts(orderItems);
        return orderItems;
    }

    @Override
    public void setProduct(OrderItem orderItem) {
        Product product = productService.getProduct(orderItem.getPid());
        orderItem.setProduct(product);
    }

    @Override
    public void setProducts(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }
}
