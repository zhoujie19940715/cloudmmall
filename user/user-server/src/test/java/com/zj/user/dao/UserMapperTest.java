package com.zj.user.dao;

import com.zj.user.UserApplicationTests;
import com.zj.user.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;
@Component
public class UserMapperTest extends UserApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void selectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user.toString());
    }
}