package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.User;
import com.tmall.service.UserService;
import com.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user_list")
    public String userlist(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> users = userService.list();
        int total = (int) new PageInfo<>(users).getTotal();
        page.setTotal(total);
        model.addAttribute("us", users);
        model.addAttribute("page", page);
        return "admin/listUser";
    }
}
