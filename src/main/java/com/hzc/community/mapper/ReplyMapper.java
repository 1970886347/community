package com.hzc.community.mapper;

import com.hzc.community.model.Reply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ReplyMapper {
    @Insert("insert into reply(user_id,question_id,content,gmt_create,be_replied) values(#{userId},#{questionId},#{content},#{gmtCreate},#{beReplied})")
    void insert(Reply reply);
    List<Reply> selectByQuestionId(Integer questionId);
    @Select("select count(*) from reply where be_replied=#{accountId} and user_id<>#{accountId} and status=0")
    Integer countByBeReplied(@Param("accountId") long accountId);
    List<Reply>listByReplied(@Param("accountId") long accountId);
    Reply selectById(Integer id);
    @Update("update reply set status=1 where id=#{id}")
    void update(@Param("id") Integer id);
}
