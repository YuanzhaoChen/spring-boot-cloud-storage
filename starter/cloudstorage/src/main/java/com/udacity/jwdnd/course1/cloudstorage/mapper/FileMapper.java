package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.LinkedList;

@Mapper
public interface FileMapper {
    @Results(id = "fileResults", value = {
            @Result(property = "id", column = "fileId"),
            @Result(property = "name", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "size", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "data", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE fileId = #{id}")
    File getFile(Integer id);

    @ResultMap("fileResults")
    @Select("SELECT * FROM FILES WHERE userid = #{userId} and filename = #{fileName}")
    File getFileByName(Integer userId, String fileName);

    @ResultMap("fileResults")
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    LinkedList<File> getAllFiles(Integer userId);

    @ResultMap("fileResults")
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{name}, #{contentType}, #{size}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{id}")
    void deleteFile(Integer id);
}
