package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.Set;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByFilename(String filename);

    @Select("SELECT * FROM FILES")
    Set<File> getFiles();

    @Insert("INSERT into Files (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertFile(File file);

    @Delete("delete from Files where filename = #{filename}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer deleteFile(String filename);


}
