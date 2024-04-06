package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.LinkedList;

@Mapper
public interface CredentialMapper {
    @Results(id="credentialResults", value = {
            @Result(property = "id", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "userName", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{id}")
    Credential getCredential(Integer id);

    @ResultMap("credentialResults")
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    LinkedList<Credential> getAllCredentials(Integer userId);

    @ResultMap("credentialResults")
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{userName}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "credentialid")
    int insertCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{id}")
    void deleteCredential(Integer id);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, key = #{key}, password = #{password} WHERE credentialid = #{id}")
    void updateCredential(Credential credential);

}
