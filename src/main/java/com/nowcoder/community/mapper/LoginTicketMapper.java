package com.nowcoder.community.mapper;

import com.nowcoder.community.pojo.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper

public interface LoginTicketMapper {

    @Insert("insert into login_ticket(user_id, ticket, status, expired) " +
            "VALUES (#{userId},#{ticket},#{status},#{expired})")
//    开启自动填充，目标为"id"
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);

    @Select("select * from login_ticket where ticket=#{ticket};")
    LoginTicket selectByTicket(String ticket);

    @Update("update login_ticket set status=#{status} where ticket=#{ticket}")
    int updateStatus(String ticket, int status);
}
