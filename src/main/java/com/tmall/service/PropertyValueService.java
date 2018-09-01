package com.tmall.service;

import com.tmall.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {

    void addPV(PropertyValue propertyValue);

    boolean updatePV(PropertyValue propertyValue);

    void deletePV(Integer id);

    PropertyValue getPV(Integer id);

    List<PropertyValue> list(Integer pdid);

    void fill(List<PropertyValue> propertyValues);
}
