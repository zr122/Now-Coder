package com.nowcoder.community.util;

import com.nowcoder.community.pojo.User;
import org.springframework.stereotype.Component;


/*
* 持有用户信息代替session对象
* */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }

}
