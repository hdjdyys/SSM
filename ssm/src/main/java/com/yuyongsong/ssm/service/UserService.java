package com.yuyongsong.ssm.service;

import com.yuyongsong.ssm.pojo.User;
import org.springframework.dao.DataAccessException;


public interface UserService {

    /**
     * 新增一个用户信息
     *
     * @return
     */
    boolean insertUser(User user) throws DataAccessException;

    /**
     * 查询一个用户信息
     *
     * @return
     */
    User selectUser(User user);
}
