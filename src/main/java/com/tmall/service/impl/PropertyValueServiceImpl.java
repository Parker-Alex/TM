package com.tmall.service.impl;

import com.tmall.dao.PropertyValueMapper;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import com.tmall.service.PropertyService;
import com.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyValueServiceImpl implements PropertyValueService {

    @Autowired
    private PropertyValueMapper propertyValueMapper;

    @Autowired
    private PropertyService propertyService;

    @Override
    public void addPV(PropertyValue propertyValue) {
        propertyValueMapper.insertSelective(propertyValue);
    }

    @Override
    public boolean updatePV(PropertyValue propertyValue) {
        return propertyValueMapper.updateByPrimaryKeySelective(propertyValue)>0;
    }

    @Override
    public void deletePV(Integer id) {
        propertyValueMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PropertyValue getPV(Integer id) {
        return propertyValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PropertyValue> list(Integer pdid) {
        PropertyValueExample example = new PropertyValueExample();
        example.createCriteria().andPdidEqualTo(pdid);
        example.setOrderByClause("id desc");
        return propertyValueMapper.selectByExample(example);
    }

    @Override
    public void fill(List<PropertyValue> propertyValues) {
        for (PropertyValue pv :
                propertyValues) {
            Property p = propertyService.getProperty(pv.getPpid());
            pv.setProperty(p);
        }
    }
}
