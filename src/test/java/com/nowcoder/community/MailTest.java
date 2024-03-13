package com.nowcoder.community;


import com.nowcoder.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@SpringBootTest
public class MailTest {

    @Autowired
    private MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Test
    public void test1(){
        mailClient.sendMail("shouzhi040202@sina.com","Test","Hello!");

    }

    @Test
    public void test2(){
        Context context=new Context();
        context.setVariable("username","sunday");

        String content=templateEngine.process("mail/demo",context);
        System.out.println(content);

        mailClient.sendMail("shouzhi040202@sina.com","HTML",content);
    }
}
