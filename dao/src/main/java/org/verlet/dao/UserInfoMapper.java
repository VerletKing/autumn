package org.verlet.dao;

import org.apache.ibatis.annotations.Param;
import org.verlet.pojo.UserInfo;

public interface UserInfoMapper {

    String login(@Param("account") String account,@Param("password") String password);

    int deleteByPrimaryKey(String id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);
}