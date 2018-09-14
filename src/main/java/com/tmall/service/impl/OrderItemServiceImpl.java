package com.tmall.service.impl;

import com.tmall.dao.OrderItemMapper;
import com.tmall.pojo.Order;
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

    /**
     * 通过用户id得到订单项列表
     * @param uid
     * @return
     */
    @Override
    public List<OrderItem> listByUid(Integer uid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andUidEqualTo(uid);
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProducts(orderItems);
        return orderItems;
    }

    /**
     * 设置订单项的产品属性
     * @param orderItem
     */
    @Override
    public void setProduct(OrderItem orderItem) {
        Product product = productService.getProduct(orderItem.getPid());
        product.setStock(product.getStock() - orderItem.getNumber());
        productService.updateProduct(product);
        orderItem.setProduct(product);
    }

    @Override
    public void setProducts(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            setProduct(orderItem);
        }
    }

    /**
     * 通过订单id得到对应订单项列表后，设置订单的订单项列表属性,总金额，和订单项总数
     * @param order
     */
    @Override
    public void fillOrder(Order order) {
        float total = 0;
        int orderItemNum = 0;
//        通过订单id查找对应的订单项列表,并为订单项列表填充产品信息
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andOidEqualTo(order.getId());
        example.setOrderByClause("id desc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(example);
        setProducts(orderItems);
//        循环遍历订单项列表,得到订单的总金额和订单项总数
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromoteprice() * orderItem.getNumber();
            orderItemNum += orderItem.getNumber();
        }
//        设置订单属性
        order.setTotal(total);
        order.setOrderItemNum(orderItemNum);
        order.setOrderItems(orderItems);
    }

    @Override
    public void fillOrders(List<Order> orders) {
        for (Order order : orders) {
            fillOrder(order);
        }
    }

    @Override
    public List<OrderItem> listByPid(Integer pid) {
        OrderItemExample example = new OrderItemExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        return orderItemMapper.selectByExample(example);
    }


}
