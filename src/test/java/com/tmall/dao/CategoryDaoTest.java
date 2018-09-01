package com.tmall.dao;

import com.tmall.BaseTest;
import com.tmall.pojo.Category;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class CategoryDaoTest extends BaseTest {

    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void addCategory() {
        Category category = new Category();
        category.setName("测试1");
        categoryDao.addCategory(category);
    }
}