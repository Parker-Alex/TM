package com.tmall.web;

import com.tmall.pojo.Category;
import com.tmall.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PageController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 跳转到注册页面的方法
     * @param model
     * @return
     */
    @RequestMapping("/registerPage")
    public String rigister(Model model) {
        List<Category> categories = categoryService.list();
        model.addAttribute("cs", categories);
        return "fore/register";
    }

    /**
     * 添砖到注册成功页面的方法
     * @return
     */
    @RequestMapping("/registersuccess")
    public String registersuccess() {
        return "/fore/registerSuccess";
    }

    /**
     * 跳转到登录页面方法
     * @return
     */
    @RequestMapping("/loginpage")
    public String login() {
        return "fore/login";
    }
}
