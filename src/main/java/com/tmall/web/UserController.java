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

    /**
     * 显示用户列表方法
     * @param model
     * @param page
     * @return
     */
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

    /**
     * 删除用户方法，但以用户id为外键的表太多，删除用户需要删除跟该用户相关的表的内容，所以不添加删除功能
     * @param id
     * @return
     */
//    @RequestMapping("/user_delete")
//    public String userDelete(Integer id) {
//        userService.deleteUser(id);
//        return "redirect:/user_list";
//    }
}
