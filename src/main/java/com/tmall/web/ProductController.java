package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Picture;
import com.tmall.pojo.Product;
import com.tmall.service.CategoryService;
import com.tmall.service.PictureService;
import com.tmall.service.ProductService;
import com.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PictureController pictureController;

    @RequestMapping("product_list")
    public String list(Integer cid, Model model, Page page) {
        Category c = categoryService.getCategory(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Product> ps = productService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        model.addAttribute("c", c);
        model.addAttribute("productList", ps);
        model.addAttribute("page", page);
        return "admin/listProduct";
    }

    @RequestMapping("/product_add")
    public String add(Product product) {
        product.setCreatedate(new Date());
        productService.addProduct(product);
        return "redirect:/product_list?cid=" + product.getCid();
    }

    @RequestMapping("/product_delete")
    public String delete(Integer id, HttpSession session) {
        List<Picture> pictureList = pictureService.list(id);
        for (Picture c :
                pictureList) {
//            pictureService.deletePicture(c.getId());
            pictureController.delete(c.getId(), session);
        }
        File file = new File(session.getServletContext().getRealPath("img/product/" + id));
        file.delete();
        Product product = productService.getProduct(id);
        productService.deleteProduct(id);
        return "redirect:/product_list?cid=" + product.getCid();
    }

    @RequestMapping("/product_edit")
    public String edit(Integer id, Model model) {
        Product product = productService.getProduct(id);
        Category category = categoryService.getCategory(product.getCid());
        product.setCategory(category);
        model.addAttribute("p", product);
        return "admin/editProduct";
    }

    @RequestMapping("/product_update")
    public String update(Product product) {
        product.setCreatedate(new Date());
        productService.updateProduct(product);
        return "redirect:/product_list?cid=" + product.getCid();
    }
}
