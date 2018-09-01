package com.tmall.dao;

import com.tmall.pojo.PropertyValue;
import com.tmall.pojo.PropertyValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PropertyValueMapper {
    long countByExample(PropertyValueExample example);

    int deleteByExample(PropertyValueExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PropertyValue record);

    int insertSelective(PropertyValue record);

    List<PropertyValue> selectByExample(PropertyValueExample example);

    PropertyValue selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PropertyValue record, @Param("example") PropertyValueExample example);

    int updateByExample(@Param("record") PropertyValue record, @Param("example") PropertyValueExample example);

    int updateByPrimaryKeySelective(PropertyValue record);

    int updateByPrimaryKey(PropertyValue record);
}