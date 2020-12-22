package com.udacity.jwdnd.c1.javawebdev.mapper;

import com.udacity.jwdnd.c1.javawebdev.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE email = #{email}")
    User getUserByEmail(String email);

    @Insert("INSERT into USERS (email, salt, password, firstname, lastname) " +
            "VALUES(#{email}, #{salt}, #{password}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertUser(User user);


}
