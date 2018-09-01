package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.tmall.pojo.*;
import com.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ForeController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PropertyValueService propertyValueService;

    @RequestMapping("/home")
    public String home(Model model) {
        List<Category> categoryList = categoryService.list();
        productService.fillAll(categoryList);
        productService.fillByRow(categoryList);
        for (Category c : categoryList) {
            for (Product p : c.getProducts()) {
                List<Picture> pictures = pictureService.list(p.getId());
                p.setPictures(pictures);
            }
        }
        model.addAttribute("cs",categoryList);
        return "fore/home";
    }

    @RequestMapping("/foreregister")
    public String foreregister(Model model, User user) {
        String name = user.getName();
        if (!userService.isExist(name)) {
            model.addAttribute("msg", "用户名已被使用，请重新输入!");
            model.addAttribute("username", name);
            return "fore/register";
        }
        userService.addUser(user);
        return "redirect:/registersuccess";
    }

    @RequestMapping("/forelogin")
    public String forelogin(Model model, @RequestParam("name") String name,
                            @RequestParam("password") String password,
                            HttpSession session) {
        User user = userService.getByNameAndPw(name, password);
        if (user == null) {
            model.addAttribute("msg", "用户不存在");
            return "fore/login";
        }
        session.setAttribute("userinfo", user);
        return "redirect:/home";
    }

    @RequestMapping("/forelogout")
    public String forelogout(HttpSession session) {
        session.removeAttribute("userinfo");
        return "redirect:/home";
    }

    @RequestMapping("/foreproduct")
    public String foreproduct(Model model, Integer pid) {
//        通过产品id得到产品
        Product product = productService.getProduct(pid);
//        通过产品id得到产品对应的图片列表
        List<Picture> pictures = pictureService.list(pid);
//        通过产品id得到评论列表
        List<Comment> comments = commentService.listByPid(pid);
//        通过产品id得到属性值列表
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
//        为属性值添加属性
        propertyValueService.fill(propertyValues);
//        设置产品的评论数
        product.setCommentnum(comments.size());
//        将图片列表放入产片中
        product.setPictures(pictures);
        model.addAttribute("p", product);
        model.addAttribute("comments", comments);
        model.addAttribute("pvs", propertyValues);
        return "fore/product";
    }

    /**
     * @ResponseBody 将数据写入response的body数据区中
     * @param session
     * @return
     */
    @RequestMapping("/forecheckLogin")
    @ResponseBody
    public String forecheckLogin(HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        if (user != null) {
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/foreloginAjax")
    @ResponseBody
    public String foreloginAjax(@RequestParam("name") String name,
                                @RequestParam("password") String password,
                                HttpSession session) {
        User user = userService.getByNameAndPw(name, password);
        if (user != null) {
            session.setAttribute("userinfo", user);
            return "success";
        }
        return "fail";
    }

    @RequestMapping("/forecategory")
    public String foreCategory(@RequestParam("cid") Integer id, Model model) {
        Category category = categoryService.getCategory(id);
        List<Product> products =productService.list(id);
        for (Product p :
                products) {
            List<Comment> comments = commentService.listByPid(p.getId());
            List<Picture> pictures = pictureService.list(p.getId());
            p.setPictures(pictures);
            p.setCommentnum(comments.size());
        }
        category.setProducts(products);
        model.addAttribute("c", category);
        return "fore/category";
    }

    @RequestMapping("/foresearch")
    public String foreSearch(Model model, String keyword) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        for (Product p : products) {
            List<Comment> comments = commentService.listByPid(p.getId());
            List<Picture> pictures = pictureService.list(p.getId());
            p.setCommentnum(comments.size());
            p.setPictures(pictures);
        }
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

}
