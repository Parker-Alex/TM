package com.tmall.service;

import com.tmall.pojo.Category;
import com.tmall.util.Page;

import java.util.List;

public interface CategoryService {

    List<Category> list();

    void addCategory(Category category);

    void deleteCategory(Integer id);

    Category getCategory(Integer id);

    void updateCategory(Category category);
}

