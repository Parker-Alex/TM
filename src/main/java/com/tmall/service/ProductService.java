package com.tmall.service;

import com.tmall.pojo.Category;
import com.tmall.pojo.Product;

import java.util.List;

public interface ProductService {

    Product getProduct(Integer id);

    void addProduct(Product product);

    void deleteProduct(Integer id);

    void updateProduct(Product product);

    List<Product> list(Integer cid);

    void fill(Category category);

    void fillAll(List<Category> categories);

    void fillByRow(List<Category> categories);

    List<Product> search(String keyword);
}
