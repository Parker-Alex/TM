package com.tmall.web;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.service.CategoryService;
import com.tmall.service.ProductService;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PropertyValueController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PropertyValueService propertyValueService;

    @Autowired
    private PropertyService propertyService;

    @RequestMapping("/propertyvalue_list")
    public String properyvalueList(Integer pid, Model model) {
        Product product = productService.getProduct(pid);
        Category category = categoryService.getCategory(product.getCid());
        product.setCategory(category);
        List<PropertyValue> propertyValues = propertyValueService.list(pid);
        List<Property> properties = propertyService.list(category.getId());
        propertyValueService.fill(propertyValues);
        model.addAttribute("p", product);
        model.addAttribute("ps", properties);
        model.addAttribute("pvs", propertyValues);
        return "admin/listPropertyValue";
    }

    @RequestMapping("/propertyvalue_delete")
    public String propertyvalueDelete(@RequestParam("id") Integer id,
                                      @RequestParam("pid") Integer pid) {
        propertyValueService.deletePV(id);
        return "redirect:/propertyvalue_list?pid=" + pid;
    }

    @RequestMapping("/properyvalue_add")
    public String properyvalueAdd(PropertyValue propertyValue) {
        int pid = propertyValue.getPdid();
        Property property = propertyService.getProperty(propertyValue.getPpid());
        propertyValue.setProperty(property);
        propertyValueService.addPV(propertyValue);
        return "redirect:/propertyvalue_list?pid=" + pid;
    }

    @RequestMapping("/propertyvalue_edit")
    public String propertyvalueEdit(Integer id, Model model) {
        PropertyValue propertyValue = propertyValueService.getPV(id);
        model.addAttribute("pv", propertyValue);
        return "admin/editPropertyValue";
    }

    @RequestMapping("/propertyvalue_update")
    public String propertyvalueUpdate(PropertyValue propertyValue) {
        int pid = propertyValue.getPdid();
        propertyValueService.updatePV(propertyValue);
        return "redirect:/propertyvalue_list?pid=" + pid;
    }
}
