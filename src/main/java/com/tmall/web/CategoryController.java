package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyService;
import com.tmall.util.ImageUtil;
import com.tmall.util.Page;
import com.tmall.util.UploadedImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
//@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @RequestMapping("/category_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Category> categoryList = categoryService.list();
        int total = (int) new PageInfo<>(categoryList).getTotal();
        page.setTotal(total);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("page", page);
        return "admin/listCategory";
    }

    @RequestMapping("/category_add")
    public String add(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.addCategory(category);
//        得到文件夹
        File imageFile = new File(session.getServletContext().getRealPath("img/category"));
//        创建文件
        File file = new File(imageFile, category.getId()+".jpg");
//        判断文件夹是否存在
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage image = ImageUtil.change2jpg(file);
        ImageIO.write(image, "jpg", file);
        return "redirect:/category_list";
    }

    @RequestMapping("/category_delete")
    public String delete(Integer id, HttpSession session) {
        List<Property> propertyList = propertyService.list(id);
        for (Property p :
                propertyList) {
            propertyService.deleteProperty(p.getId());
        }
        List<Product> productList = productService.list(id);
        for (Product p :
                productList) {
//            productService.deleteProduct(p.getId());
            productController.delete(p.getId(), session);
        }
        categoryService.deleteCategory(id);
        File imageFile = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFile, id+".jpg");
        file.delete();
        return "redirect:/category_list";
    }

    @RequestMapping("/category_edit")
    public String edit(Integer id, Model model) {
        Category category = categoryService.getCategory(id);
        model.addAttribute("category", category);
        return "admin/editCategory";
    }

    @RequestMapping("/category_update")
    public String update(Category category, HttpSession session, UploadedImageFile uploadedImageFile) throws IOException {
        categoryService.updateCategory(category);
        MultipartFile image = uploadedImageFile.getImage();
        if (image != null && !image.isEmpty()) {
            File imageFile = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFile, category.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img, "jpg", file);
        }
        return "redirect:/category_list";
    }
}
