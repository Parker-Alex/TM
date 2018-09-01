package com.tmall.service;

import com.tmall.pojo.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> list(Integer pid);

    void addPicture(Picture picture);

    void deletePicture(Integer id);

    Picture getPicture(Integer id);
}
