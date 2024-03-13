package com.nowcoder.community.service;

import com.nowcoder.community.mapper.DiscussPostMapper;
import com.nowcoder.community.pojo.DiscussPost;
import com.nowcoder.community.util.SensitiveWordsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Slf4j
@Service
public class DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveWordsFilter sensitiveWordsFilter;

    public List<DiscussPost> findDiscussPost(int userId,int size,int begin){
        return discussPostMapper.selectDiscussPost(userId,size,begin);
    }

    public int selectDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

    public int addDiscussPost(DiscussPost post){
        if(post == null) {
            throw  new IllegalArgumentException("参数不能为空！");

        }



        try {
            // 转义HTML标签
            post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
            post.setContent(HtmlUtils.htmlEscape(post.getContent()));
            // 过滤敏感词
            post.setTitle(sensitiveWordsFilter.filter(post.getTitle()));
            post.setContent(sensitiveWordsFilter.filter(post.getContent()));

            int result = discussPostMapper.insertDiscussPost(post);
            // 记录日志
            log.info("Add discuss post result: {}", result);
        } catch (Exception e) {
            // 记录异常信息
            log.error("Error adding discuss post", e);
            throw new RuntimeException("Error adding discuss post", e);
        }


        return discussPostMapper.insertDiscussPost(post);
    }


    public DiscussPost findDiscussPostById(int id) {
        return discussPostMapper.selectDiscussPostById(id);
    }

    public int updateCommentCount(int id, int commentCount){
        return discussPostMapper.updateCommentCount(id, commentCount);
    }


}
