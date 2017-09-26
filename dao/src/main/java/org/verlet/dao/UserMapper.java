package org.verlet.dao;

import org.apache.ibatis.annotations.Param;
import org.verlet.pojo.User;

public interface UserMapper {

    String login(@Param("account") String account,@Param("password") String password);

    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}