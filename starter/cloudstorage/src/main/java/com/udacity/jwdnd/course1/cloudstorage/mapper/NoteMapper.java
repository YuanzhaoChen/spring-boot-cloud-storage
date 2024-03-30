package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.LinkedList;

@Mapper
public interface NoteMapper {
    @Results(id = "noteResult", value = {
            @Result(property = "id", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM NOTES WHERE noteid = #{id}")
    Note getNote(int id);

    @ResultMap("noteResult")
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    LinkedList<Note> getAllNotes(Integer userId);

    @ResultMap("noteResult")
    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "noteid")
    int insertNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{id}")
    void deleteNote(Integer id);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{id}")
    void updateNote(Note note);

}
