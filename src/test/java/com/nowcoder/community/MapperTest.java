package com.nowcoder.community;

import com.nowcoder.community.mapper.DiscussPostMapper;
import com.nowcoder.community.mapper.LoginTicketMapper;
import com.nowcoder.community.pojo.DiscussPost;
import com.nowcoder.community.pojo.LoginTicket;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@SpringBootTest
class MapperTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;
    @Autowired
    private LoginTicketMapper loginTicketMapper;
    @Test
    public void SelectDiscussPostTest(){
        List<DiscussPost> list1 = discussPostMapper.selectDiscussPost(149, 10, 0);
       for(DiscussPost post : list1){
           System.out.println(post);
       }

        int rows = discussPostMapper.selectDiscussPostRows(149);
        System.out.println("共有："+rows+"行");
    }

//    测试LoginTicket
    @Test
    public void LoginTicketMapperTest(){

        LoginTicket loginTicketTest=new LoginTicket();

        loginTicketTest.setTicket("asdf");
        loginTicketTest.setUserId(101);
        loginTicketTest.setStatus(0);
        loginTicketTest.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicketTest);

    }

    @Test
    public void testSelectLoginTicket(){
        LoginTicket loginTicket = loginTicketMapper.selectByTicket("asdf");
        System.out.println(loginTicket);


        loginTicketMapper.updateStatus("asdf",1);
        loginTicket = loginTicketMapper.selectByTicket("asdf");
        System.out.println(loginTicket);
    }
}
