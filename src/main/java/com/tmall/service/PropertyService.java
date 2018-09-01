package com.tmall.service;

import com.tmall.pojo.Property;

import java.util.List;

public interface PropertyService {

    Property getProperty(Integer id);

    void addProperty(Property property);

    void deleteProperty(Integer id);

    void updateProperty(Property property);

    List<Property> list(Integer cid);
}
