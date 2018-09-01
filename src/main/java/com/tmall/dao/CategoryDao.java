package com.tmall.dao;

import com.tmall.pojo.Category;
import com.tmall.util.Page;

import java.util.List;

public interface CategoryDao {

    List<Category> list();

    int getTotal();

    void addCategory(Category category);

    void deleteCategory(Integer id);

    Category getCategory(Integer id);

    void updateCategory(Category category);
}
