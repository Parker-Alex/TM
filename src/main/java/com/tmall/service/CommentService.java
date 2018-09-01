package com.tmall.service;

import com.tmall.pojo.Comment;

import java.util.List;

public interface CommentService {

    void addComment(Comment comment);

    void updateComment(Comment comment);

    void deleteComment(Integer id);

    Comment getComment(Integer id);

    List<Comment> listByPid(Integer pid);

    void fillComment(Comment comment);

    void fillAllComment(List<Comment> comments);
}
