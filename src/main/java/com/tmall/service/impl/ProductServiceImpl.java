package com.tmall.service.impl;

import com.tmall.dao.ProductMapper;
import com.tmall.pojo.Category;
import com.tmall.pojo.Picture;
import com.tmall.pojo.Product;
import com.tmall.pojo.ProductExample;
import com.tmall.service.PictureService;
import com.tmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private PictureService pictureService;

    @Override
    public Product getProduct(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        List<Picture> pictures = pictureService.list(product.getId());
        product.setPictures(pictures);
        return product;
    }

    @Override
    public void addProduct(Product product) {
        productMapper.insertSelective(product);
    }

    @Override
    public void deleteProduct(Integer id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateProduct(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public List<Product> list(Integer cid) {
        ProductExample example = new ProductExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return productMapper.selectByExample(example);
    }

    @Override
    public void fill(Category category) {
        List<Product> products = list(category.getId());
        category.setProducts(products);
    }

    @Override
    public void fillAll(List<Category> categories) {
        for (Category c :
                categories) {
            fill(c);
        }
    }

    @Override
    public void fillByRow(List<Category> categories) {
        int productNumberOfEachRow = 8;
        for (Category category : categories) {
            List<Product> products = category.getProducts();
            List<List<Product>> productByRow = new ArrayList<>();
            for (int i=0; i<productNumberOfEachRow; i+=productNumberOfEachRow) {
                int size = i + productNumberOfEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productOfEachRow = products.subList(i, size);
                productByRow.add(productOfEachRow);
            }
            category.setProductByRow(productByRow);
        }
    }

    @Override
    public List<Product> search(String keyword) {
        ProductExample example = new ProductExample();
        example.createCriteria().andNameLike("%"+keyword+"%");
        example.setOrderByClause("id desc");
        return productMapper.selectByExample(example);
    }
}

