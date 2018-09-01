package com.tmall.service.impl;

import com.tmall.dao.PropertyMapper;
import com.tmall.pojo.Property;
import com.tmall.pojo.PropertyExample;
import com.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public Property getProperty(Integer id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addProperty(Property property) {
        propertyMapper.insertSelective(property);
    }

    @Override
    public void deleteProperty(Integer id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateProperty(Property property) {
        propertyMapper.updateByPrimaryKeySelective(property);
    }

    @Override
    public List<Property> list(Integer cid) {
        PropertyExample example = new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }
}
