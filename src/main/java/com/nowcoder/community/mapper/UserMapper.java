package com.nowcoder.community.mapper;

import com.nowcoder.community.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("SELECT * from user where id = #{userId}")
    User selectUserById(int userId);

    @Select("SELECT * from user where username = #{username}")
    User selectUserByName(String username);

    @Select("SELECT * from user where email = #{email}")
    User selectUserByEmail(String email);


    @Insert("insert into user (username, password, salt, email, type, status, activation_code, header_url, create_time) " +
            "values(#{username}, #{password}, #{salt}, #{email}, #{type}, #{status}, #{activationCode}, #{headerUrl}, #{createTime})")
    int insertUser(User user);

    @Update("update user set status=#{i} where id=#{userId}")
    void updateStatus(int userId, int i);


    @Update("update user set header_url = #{headerUrl} where id= #{userId}")
    int updateHeader(int userId, String headerUrl);
    @Update("update user set password = #{Password} where id= #{userId}")
    int updatePassword(int userId, String Password);
}
