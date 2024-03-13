package com.nowcoder.community.mapper;


import com.nowcoder.community.pojo.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

//    按照userId查询所有非拉黑帖子，并且分页显示
    @Select("select * from discuss_post where status !=2 and user_id=#{userId} order by type desc,create_time desc Limit #{begin},#{size}")
    List<DiscussPost> selectDiscussPost(int userId,int size,int begin);


//    查询所有userId的非拉黑帖子总数

    int selectDiscussPostRows(int userId);

    //增加帖子
    int insertDiscussPost(DiscussPost discussPost);

    //查看详情
    DiscussPost selectDiscussPostById(int id);

    //更新帖子数量
    int updateCommentCount(int id, int commentCount);

}
