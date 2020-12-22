package com.udacity.jwdnd.c1.javawebdev.mapper;

import com.udacity.jwdnd.c1.javawebdev.model.entity.ChatMessage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper {

    @Select("SELECT * FROM MESSAGE WHERE username = #{username}")
    List<ChatMessage> getMessageByUsername(String username);

    @Insert("INSERT into USERS (username, salt password, firstname, lastname) " +
            "VALUES(#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertMessage(String username);


}
