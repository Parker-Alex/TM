package com.tmall.service.impl;

import com.tmall.dao.PictureMapper;
import com.tmall.pojo.Picture;
import com.tmall.pojo.PictureExample;
import com.tmall.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureMapper pictureMapper;

    @Override
    public List<Picture> list(Integer pid) {
        PictureExample example = new PictureExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id asc");
        return pictureMapper.selectByExample(example);
    }

    @Override
    public void addPicture(Picture picture) {
        pictureMapper.insertSelective(picture);
    }

    @Override
    public void deletePicture(Integer id) {
        pictureMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Picture getPicture(Integer id) {
        return pictureMapper.selectByPrimaryKey(id);
    }
}
