package com.tmall.service.impl;

import com.tmall.dao.CommentMapper;
import com.tmall.dao.UserMapper;
import com.tmall.pojo.Comment;
import com.tmall.pojo.CommentExample;
import com.tmall.pojo.User;
import com.tmall.service.CommentService;
import com.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserService userService;

    @Override
    public void addComment(Comment comment) {
        commentMapper.insertSelective(comment);
    }

    @Override
    public void updateComment(Comment comment) {
        commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public void deleteComment(Integer id) {
        commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Comment getComment(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过产品id得到该产品的评论列表
     * @param pid
     * @return
     */
    @Override
    public List<Comment> listByPid(Integer pid) {
        CommentExample example = new CommentExample();
        example.createCriteria().andPidEqualTo(pid);
        example.setOrderByClause("id desc");
        List<Comment> comments = commentMapper.selectByExample(example);
        fillAllComment(comments);
        return comments;
    }

    /**
     * 为评论设置用户
     * @param comment
     */
    @Override
    public void fillComment(Comment comment) {
        int uid = comment.getUid();
        User user = userService.getUser(uid);
        comment.setUser(user);
    }

    @Override
    public void fillAllComment(List<Comment> comments) {
        for (Comment c :
                comments) {
            fillComment(c);
        }
    }


}
