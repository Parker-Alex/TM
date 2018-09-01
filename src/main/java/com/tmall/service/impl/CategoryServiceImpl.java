package com.tmall.service.impl;

import com.tmall.dao.CategoryDao;
import com.tmall.pojo.Category;
import com.tmall.service.CategoryService;
import com.tmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public List<Category> list() {
        return categoryDao.list();
    }

    @Override
    public void addCategory(Category category) {
        categoryDao.addCategory(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        categoryDao.deleteCategory(id);
    }

    @Override
    public Category getCategory(Integer id) {
        return categoryDao.getCategory(id);
    }

    @Override
    public void updateCategory(Category category) {
        categoryDao.updateCategory(category);
    }
}
