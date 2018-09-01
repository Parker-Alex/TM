package com.tmall.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tmall.pojo.Category;
import com.tmall.pojo.Property;
import com.tmall.service.CategoryService;
import com.tmall.service.PropertyService;
import com.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PropertyController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping("property_list")
    public String list(Integer cid, Model model, Page page) {
        Category c = categoryService.getCategory(cid);

        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> ps = propertyService.list(cid);

        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + c.getId());

        model.addAttribute("c", c);
        model.addAttribute("ps", ps);
        model.addAttribute("page", page);
        return "admin/listProperty";
    }

    @RequestMapping("/property_add")
    public String add(Property property) {
        propertyService.addProperty(property);
        return "redirect:/property_list?cid=" + property.getCid();
    }

    @RequestMapping("/property_delete")
    public String delete(Integer id) {
        Property property = propertyService.getProperty(id);
        propertyService.deleteProperty(id);
        return "redirect:/property_list?cid=" + property.getCid();
    }

    @RequestMapping("/property_edit")
    public String edit(Integer id, Model model) {
        Property property = propertyService.getProperty(id);
        Category category = categoryService.getCategory(property.getCid());
        property.setCategory(category);
        model.addAttribute("p", property);
        return "admin/editProperty";
    }

    @RequestMapping("/property_update")
    public String update(Property property) {
        propertyService.updateProperty(property);
        return "redirect:/property_list?cid=" + property.getCid();
    }
}
