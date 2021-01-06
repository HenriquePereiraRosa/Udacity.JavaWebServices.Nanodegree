package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("select * from CREDENTIALS where url = #{url}")
    Credential getByUrl(String url);

    @Select("select * from CREDENTIALS where userid = #{userid}")
    List<Credential> getByUserId(Integer userid);

    @Insert("insert into CREDENTIALS (url, username, key, password, userid) " +
            "VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertOne(Credential credential);

    @Delete("delete from CREDENTIALS where url = #{url}")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer deleteByUrl(String url);


}
