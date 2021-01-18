package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("select * from Notes where id = #{id}")
    Note getById(Integer id);

    @Select("select * from Notes where noteTitle = #{noteTitle}")
    Note getByTitle(String noteTitle);

    @Select("select * from NOTES where userid = #{userid}")
    List<Note> getAllByUserId(Integer userid);

    @Insert("insert into Notes (noteTitle, noteDescription, userid) " +
            "VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertOne(Note note);

    @Update("update Notes " +
            " set noteTitle = #{noteTitle}," +
            "   noteDescription = #{noteDescription}," +
            "   userid = #{userid} " +
            " where id = #{id} ")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer updateOne(Note note);


    @Delete("delete from Notes where id = #{id}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer deleteById(Integer id);

    @Delete("delete from Notes where noteTitle = #{noteTitle}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer deleteByTitle(String noteTitle);


}
