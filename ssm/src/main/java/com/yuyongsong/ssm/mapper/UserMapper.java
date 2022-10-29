package com.yuyongsong.ssm.mapper;

import com.yuyongsong.ssm.pojo.User;
import org.springframework.dao.DataAccessException;

import java.sql.SQLException;


public interface UserMapper {

    int insertUser( User user) throws DataAccessException;

    User selectUser(User user);
}
