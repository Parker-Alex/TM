package com.tmall.service;

import com.tmall.BaseTest;
import com.tmall.pojo.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CategoryServiceTest extends BaseTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void addCategory() {
        Category category = new Category();
        category.setName("测试3");
        categoryService.addCategory(category);
    }
}