package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Order;
import com.tmall.pojo.User;
import com.tmall.service.OrderItemService;
import com.tmall.service.OrderService;
import com.tmall.service.UserService;
import com.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private UserService userService;

    @RequestMapping("/order_list")
    public String orderlist(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Order> orders = orderService.list();
        orderItemService.fillOrders(orders);
        for (Order order : orders) {
            User user = userService.getUser(order.getUid());
            order.setUser(user);
        }
        int total = (int) new PageInfo<>(orders).getTotal();
        page.setTotal(total);
        model.addAttribute("os", orders);
        model.addAttribute("page", page);
        return "admin/listOrder";
    }
}
