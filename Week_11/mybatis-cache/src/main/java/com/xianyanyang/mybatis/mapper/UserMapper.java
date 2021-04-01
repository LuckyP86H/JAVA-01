package com.xianyanyang.mybatis.mapper;

import com.xianyanyang.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    List<User> listAll();

    User findById(String id);

    User findUser(String id);

    void changeUserName(@Param("id") String id, @Param("name") String name);

}
