package com.tmall.web;

import com.tmall.pojo.Category;
import com.tmall.pojo.Picture;
import com.tmall.pojo.Product;
import com.tmall.service.CategoryService;
import com.tmall.service.PictureService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/picture_list")
    public String list(Integer pid, Model model) {
        Product product = productService.getProduct(pid);
        Category category = categoryService.getCategory(product.getCid());
        product.setCategory(category);
        List<Picture> pictureList = pictureService.list(pid);
        model.addAttribute("ps", pictureList);
        model.addAttribute("product", product);
        return "admin/listPicture";
    }

    @RequestMapping("/picture_add")
    public String add(Picture picture, HttpSession session, @RequestParam("image") MultipartFile file) throws IOException {
        pictureService.addPicture(picture);
        String filePath = session.getServletContext().getRealPath("img/product/"+picture.getPid());
//        File pictureFile = new File(filePath, (picture.getId()%5==0?5:picture.getId()%5) + ".jpg");
        File pictureFile = new File(filePath, picture.getId() + ".jpg");
        if (!pictureFile.getParentFile().exists()){
            pictureFile.getParentFile().mkdirs();
        }
        file.transferTo(pictureFile);
        return "redirect:/picture_list?pid=" + picture.getPid();
    }

    @RequestMapping("/picture_delete")
    public String delete(Integer id, HttpSession session) {
        Picture picture = pictureService.getPicture(id);
        pictureService.deletePicture(id);
        String filePath = session.getServletContext().getRealPath("img/product/" + picture.getPid());
        File file = new File(filePath, picture.getId() + ".jpg");
        file.delete();
        return "redirect:/picture_list?pid=" + picture.getPid();
    }
}
