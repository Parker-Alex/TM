package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.tmall.enums.OrderEnum;
import com.tmall.pojo.*;
import com.tmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.attribute.standard.RequestingUserName;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderService orderService;

    /**
     * 展示主页面方法
     * @param model
     * @return
     */
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

    /**
     * 用户注册方法
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/foreregister")
    public String foreregister(Model model, User user) {
        String name = user.getName();
//        判断用户名是否存在
        if (!userService.isExist(name)) {
            model.addAttribute("msg", "用户名已被使用，请重新输入!");
            model.addAttribute("username", name);
            return "fore/register";
        }
        userService.addUser(user);
        return "redirect:/registersuccess";
    }

    /**
     * 用户登录方法
     * @param model
     * @param name
     * @param password
     * @param session
     * @return
     */
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

    /**
     * 用户注销方法
     * @param session
     * @return
     */
    @RequestMapping("/forelogout")
    public String forelogout(HttpSession session) {
        session.removeAttribute("userinfo");
        return "redirect:/home";
    }

    /**
     * 展示产品相关信息方法
     * @param model
     * @param pid
     * @return
     */
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
//        通过产品id得到订单项列表
        List<OrderItem> orderItems = orderItemService.listByPid(pid);
//        为属性值添加属性
        propertyValueService.fill(propertyValues);
//        设置产品的评论数
        product.setCommentnum(comments.size());
//        设置产品的成交数
        product.setDealnum(orderItems.size());
//        将图片列表放入产片中
        product.setPictures(pictures);
        model.addAttribute("p", product);
        model.addAttribute("comments", comments);
        model.addAttribute("pvs", propertyValues);
        return "fore/product";
    }

    /**
     * 验证是否登录方法
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

    /**
     * 模态框用户登录方法
     * @param name
     * @param password
     * @param session
     * @return
     */
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

    /**
     * 展示分类信息方法
     * @param id
     * @param model
     * @return
     */
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

    /**
     * 根据关键字查找产品方法
     * @param model
     * @param keyword
     * @return
     */
    @RequestMapping("/foresearch")
    public String foreSearch(Model model, String keyword) {
        PageHelper.offsetPage(0, 20);
        List<Product> products = productService.search(keyword);
        for (Product p : products) {
            List<Comment> comments = commentService.listByPid(p.getId());
            List<Picture> pictures = pictureService.list(p.getId());
            List<OrderItem> orderItems = orderItemService.listByPid(p.getId());
            p.setCommentnum(comments.size());
            p.setDealnum(orderItems.size());
            p.setPictures(pictures);
        }
        model.addAttribute("ps", products);
        return "fore/searchResult";
    }

    /**
     * 立即购买产品方法
     * @param pid
     * @param num
     * @param session
     * @return
     */
    @RequestMapping("/forebuyone")
    public String foreBuyOne(Integer pid, Integer num, HttpSession session) {
        Product product = productService.getProduct(pid);
        User user = (User) session.getAttribute("userinfo");
        List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
//        标识是否已有该产品的订单
        boolean having = false;
        int orderItemId = 0;
        for (OrderItem orderItem : orderItems) {
//            intValue() 将Integer类型对象转化成int类型
            if (orderItem.getPid().intValue() == product.getId().intValue()) {
                orderItem.setNumber(orderItem.getNumber() + num);
                orderItemService.updateOI(orderItem);
                orderItemId = orderItem.getId();
                having = true;
                break;
            }
        }
//        如果没有该产品的订单，就生成新的订单
        if (!having) {
            OrderItem orderItem = new OrderItem();
            orderItem.setNumber(num);
            orderItem.setUid(user.getId());
            orderItem.setPid(product.getId());
            orderItemService.addOI(orderItem);
            orderItemId = orderItem.getId();
        }
        return "redirect:forebuy?oiid=" + orderItemId;
    }

    /**
     * 购买后跳转到提交订单信息页面方法
     * @param model
     * @param oiid
     * @param session
     * @return
     */
    @RequestMapping("/forebuy")
    public String foreBuy(Model model, String[] oiid, HttpSession session) {
        List<OrderItem> orderItems = new ArrayList<>();
//        总金额
        float total = 0;
        for (String oiId : oiid) {
            int id = Integer.parseInt(oiId);
            OrderItem orderItem = orderItemService.getOI(id);
            total += orderItem.getProduct().getPromoteprice() * orderItem.getNumber();
            orderItems.add(orderItem);
        }
        session.setAttribute("ois", orderItems);
        model.addAttribute("total", total);
        return "fore/buy";
    }

    /**
     * 添加购物车方法
     * @param pid
     * @param num
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/foreaddCart")
    @ResponseBody
    public String foreAddCart(Integer pid, Integer num, Model model, HttpSession session) {
        Product product = productService.getProduct(pid);
        User user = (User) session.getAttribute("userinfo");
        boolean having = false;
        List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
        for (OrderItem oi : orderItems) {
            if (oi.getProduct().getId().intValue() == product.getId().intValue()) {
                oi.setNumber(oi.getNumber() + num);
                orderItemService.updateOI(oi);
                having = true;
                break;
            }
        }
        if (!having) {
            OrderItem orderItem = new OrderItem();
            orderItem.setUid(user.getId());
            orderItem.setPid(product.getId());
            orderItem.setNumber(num);
            orderItemService.addOI(orderItem);
        }
        return "success";
    }

    /**
     * 查看购物车方法
     * @param model
     * @param session
     * @return
     */
    @RequestMapping("/forecart")
    public String foreCart(Model model, HttpSession session) {
        forecheckLogin(session);
        User user = (User) session.getAttribute("userinfo");
        List<OrderItem> orderItems = orderItemService.listByUid(user.getId());
        model.addAttribute("ois", orderItems);
        return "fore/cart";
    }

    /**
     * 删除订单方法
     * @param oiid
     * @param session
     * @return
     */
    @RequestMapping("/foredeleteOrderItem")
    @ResponseBody
    public String foreDeleteOrderItem(Integer oiid, HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        if (user == null)
            return "fail";
        orderItemService.deleteOI(oiid);
        return "success";
    }

    /**
     * 创建订单方法
     * @param model
     * @param order
     * @param session
     * @return
     */
    @RequestMapping("/forecreateOrder")
    public String forecreateOrder(Model model, Order order, HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        order.setOrdercode(orderCode);
        order.setUid(user.getId());
        order.setCreatedate(new Date());
        if (order.getStatus().equals(OrderEnum.WAIT_PAY.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.WAIT_PAY.getMsg());
        List<OrderItem> orderItems = (List<OrderItem>) session.getAttribute("ois");
        float total = orderService.add(order, orderItems);
        return "redirect:/pay?oid=" + order.getId() + "&total=" + total;
    }

    /**
     * 确认支付方法
     * @param model
     * @param oid
     * @return
     */
    @RequestMapping("/forepayed")
    public String forepayed(Model model, Integer oid) {
        Order order = orderService.getOrder(oid);
        if (order.getStatus().equals(OrderEnum.WAIT_DELIVERY.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.WAIT_DELIVERY.getMsg());
        order.setPaydate(new Date());
        orderService.updateOrder(order);
        model.addAttribute("o", order);
        return "fore/payed";
    }

    /**
     * 查看购买记录方法
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("/forebought")
    public String forebought(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userinfo");
//        找到当前用户下没有被删除的订单列表
        List<Order> orders = orderService.listByUid(user.getId(), OrderEnum.DELETE.getMsg());
        orderItemService.fillOrders(orders);
        model.addAttribute("os", orders);
        return "fore/bought";
    }

    /**
     * 删除订单方法
     * @param oid
     * @return
     */
    @RequestMapping("/foredeleteOrder")
    @ResponseBody
    public String foredeleteOrder(Integer oid) {
        Order order = orderService.getOrder(oid);
        if (order.getStatus().equals(OrderEnum.DELETE.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.DELETE.getMsg());
        orderService.updateOrder(order);
        return "success";
    }

    /**
     * 提醒发货方法
     * @param id
     * @return
     */
    @RequestMapping("/foredelivery")
    @ResponseBody
    public String foredelivery(Integer id) {
        Order order = orderService.getOrder(id);
        if (order.getStatus().equals(OrderEnum.WAIT_CONFIRM.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.WAIT_CONFIRM.getMsg());
        order.setDeliverydate(new Date());
        orderService.updateOrder(order);
        return "success";
    }

    /**
     * 跳转到收货后确认支付页面方法
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/foreconfirmPay")
    public String foreconfirmPay(Integer id, Model model) {
        Order order = orderService.getOrder(id);
        orderService.updateOrder(order);
        orderItemService.fillOrder(order);
        model.addAttribute("o", order);
        return "fore/confirmPay";
    }

    /**
     * 确认收货方法
     * @param id
     * @return
     */
    @RequestMapping("/foreorderConfirmed")
    public String foreorderConfirmed(Integer id, Model model) {
        Order order = orderService.getOrder(id);
        if (order.getStatus().equals(OrderEnum.WAIT_COMMENT.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.WAIT_COMMENT.getMsg());
        order.setConfirmdate(new Date());
        orderService.updateOrder(order);
        model.addAttribute("o", order);
        return "fore/orderConfirmed";
    }

    /**
     * 跳转到评论产品方法
     * @param oid
     * @param model
     * @return
     */
    @RequestMapping("/forecomment")
    public String forecomment(Integer oid, Model model) {
        Order order = orderService.getOrder(oid);
        orderItemService.fillOrder(order);
        Product product = order.getOrderItems().get(0).getProduct();
        List<Comment> comments = commentService.listByPid(product.getId());
        List<OrderItem> orderItems = orderItemService.listByPid(product.getId());
        product.setCommentnum(comments.size());
        product.setDealnum(orderItems.size());
        model.addAttribute("p", product);
        model.addAttribute("o", order);
        model.addAttribute("comments", comments);
        return "fore/comment";
    }

    /**
     * 添加评论方法
     * @param oid
     * @param pid
     * @param content
     * @param session
     * @return
     */
    @RequestMapping("/foredocomment")
    @Transactional
    public String foredocomment(@RequestParam("oid") Integer oid,
                                @RequestParam("pid") Integer pid,
                                @RequestParam("content") String content,
                                HttpSession session, Model model) {
        Order order = orderService.getOrder(oid);
        if (order.getStatus().equals(OrderEnum.FINISH.getMsg())) {
            throw new RuntimeException();
        }
        order.setStatus(OrderEnum.FINISH.getMsg());
        orderService.updateOrder(order);
        User user = (User) session.getAttribute("userinfo");
        Comment comment = new Comment();
        comment.setCreatetime(new Date());
        comment.setUid(user.getId());
        comment.setPid(pid);
        comment.setContent(content);
        commentService.addComment(comment);
        return "redirect:/forecomment?oid=" + oid + "&showonly=true";
    }

}
