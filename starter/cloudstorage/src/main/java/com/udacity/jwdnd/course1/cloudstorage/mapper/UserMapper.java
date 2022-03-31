package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    int insert(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userid}")
    User getUserByUserid(Integer userid);

    @Update("UPDATE USERS SET firstname = #{firstname}, lastname = #{lastname} WHERE userid = #{userid}")
    int updateUserByUserid(User user);

    @Delete("DELETE USERS WHERE username = #{username}")
    int deleteUserByUsername(String username);

    @Delete("DELETE USERS WHERE userid = #{userid}")
    int deleteUserByUserid(Integer userid);
}
