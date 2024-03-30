package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Results(id="userResults", value = {
            @Result(property = "id", column = "userid"),
            @Result(property = "username", column = "username"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "password", column = "password"),
            @Result(property = "firstName", column = "firstname"),
            @Result(property = "lastName", column = "lastname"),
    })
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByName(String username);

    @Select("SELECT * FROM USERS WHERE userid = #{userId}")
    User getUserById(Integer userId);

    @ResultMap("userResults")
    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertUser(User user);
}
