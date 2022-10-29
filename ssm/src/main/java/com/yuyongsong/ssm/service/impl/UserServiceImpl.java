package com.yuyongsong.ssm.service.impl;

import com.yuyongsong.ssm.mapper.UserMapper;
import com.yuyongsong.ssm.pojo.User;
import com.yuyongsong.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean insertUser(User user) throws DataAccessException {

        return this.userMapper.insertUser(user) > 0;

    }

    @Override
    public User selectUser(User user) {
        User user1 = this.userMapper.selectUser(user);
        return user1;
    }
}


