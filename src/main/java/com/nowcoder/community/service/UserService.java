package com.nowcoder.community.service;

import com.nowcoder.community.mapper.LoginTicketMapper;
import com.nowcoder.community.mapper.UserMapper;
import com.nowcoder.community.pojo.LoginTicket;
import com.nowcoder.community.pojo.User;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import com.nowcoder.community.util.RedisKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.time.LocalDateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /*
    通过id查询用户的功能
    */
    public User findUserById(int userId){
        return userMapper.selectUserById(userId);

    }

    //注册
    public Map<String, Object> register(User user){
        Map<String, Object> map =new HashMap<>();

        //空值处理
        if(user == null){
            throw new IllegalArgumentException("参数不能为空");

        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
            return map;
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
            return map;
        }

        //验证账号
        //设置一个临时变量用于储存查询结果
        User u=userMapper.selectUserByName(user.getUsername());
        if (u != null){
            map.put("usernameMsg","该账号已存在！");
            return map;
        }

        //验证邮箱
        u= userMapper.selectUserByEmail(user.getEmail());
        if (u != null){
            map.put("emailMsg","该邮箱已被注册！");
            return map;
        }

        //注册用户
        user.setSalt(CommunityUtil.generateUUID().substring(0,5));
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));

        user.setCreateTime(LocalDateTime.now());
        userMapper.insertUser(user);

        //激活邮件
        Context context=new Context();
        context.setVariable("email",user.getEmail());
        String url= domain + contextPath + "/activation" +user.getId() + "/" +user.getActivationCode();
        context.setVariable("url",url);
        String content = templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);

        return map;
    }
    //验证激活码
    public int activation(int userId, String code){
        User user= userMapper.selectUserById(userId);
        if (user.getStatus()==1){
            return ACTIVATION_REPEAT;
        } else if (user.getActivationCode().equals(code)) {
            userMapper.updateStatus(userId, 1);
            return ACTIVATION_SUCCESS;
        }else {
            return ACTIVATION_FAILURE;
        }
    }


//    用户登录
    public Map<String, Object> login(String username, String password, int expiredSeconds){
        Map<String, Object> map =new HashMap<>();

        //空值处理
        if (StringUtils.isBlank(username)){
            map.put("usernameMsg","账号不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("passwordMsg","密码不能为空！");
            return map;

        }
        //验证账号是否存在或者被激活
        User user = userMapper.selectUserByName(username);
        if (user == null){
            map.put("usernameMsg", "该账号不存在！");
            return map;
        }
        if (user.getStatus() == 0){
            map.put("usernameMsg","该账号未激活！");
            return map;

        }

//        验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)){
            map.put("passwordMsg", "密码不正确！");
            return map;
        }

//        生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);


        map.put("ticket", loginTicket.getTicket());
        return map;

    }

    //退出登陆
    public void logout(String ticket){
        loginTicketMapper.updateStatus(ticket, 1);
//        String redisKey = RedisKeyUtil.getTicketKey(ticket);
//        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
//        loginTicket.setStatus(1);
//        redisTemplate.opsForValue().set(redisKey, loginTicket);
    }


    //查询登录凭证
    public LoginTicket findLoginTicket(String ticket) {
        return loginTicketMapper.selectByTicket(ticket);

    }

    //更新用户头像
    public int updateHeader(int userId, String headerUrl) {

        return userMapper.updateHeader(userId, headerUrl);


    }

    public User findUserByName(String username) {
        return userMapper.selectUserByName(username);
    }



}
