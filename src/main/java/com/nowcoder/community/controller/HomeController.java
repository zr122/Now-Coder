package com.nowcoder.community.controller;

import com.nowcoder.community.pojo.DiscussPost;
import com.nowcoder.community.pojo.Page;
import com.nowcoder.community.pojo.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.*;

@Controller
public class HomeController implements CommunityConstant {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path ="/index", method = RequestMethod.GET)
    public String getIndexPage(Model model,Page page){
        //方法调用前，SpringMVC会自动帮我们实例化对象，
        //因此我们可以直接调用page和model中的数据
        page.setRows(discussPostService.selectDiscussPostRows(103));
        page.setPath("/index");
        List<DiscussPost> list =discussPostService.findDiscussPost(103, page.getLimit(), page.getOffSet());
        List<Map<String,Object>> discussPosts =new ArrayList<>();
        if(list != null){
            for (DiscussPost post : list) {
                Map<String,Object> map =new HashMap<>();
                map.put("post",post);
                User user =userService.findUserById(post.getUserId());
                map.put("user",user);

                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId());
                map.put("likeCount", likeCount);

                discussPosts.add(map);

            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/index";
    }


    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage(){
        return "/error/500";
    }
}
