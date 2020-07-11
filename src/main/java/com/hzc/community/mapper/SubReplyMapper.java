package com.hzc.community.mapper;

import com.hzc.community.model.Reply;
import com.hzc.community.model.SubReply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SubReplyMapper {
    List<SubReply> listSubReplyByReplyId(Integer replyId);
    @Select("select count(*) from sub_reply where reply_id=#{replyId}")
    Integer countByReplyId(Integer replyId);
    @Insert("insert into sub_reply(reply_id,user_id,be_replied,question_id,content,gmt_create) values(#{replyId},#{userId},#{beReplied},#{questionId},#{content},#{gmtCreate})")
    void insert(SubReply subReply);
    @Select("select count(*) from sub_reply where be_replied=#{accountId} and user_id<>#{accountId} and status=0")
    Integer countByBeReplied(@Param("accountId") long accountId);
    List<SubReply>listByReplied(@Param("accountId") long accountId);
    SubReply selectById(Integer id);
    @Update("update sub_reply set status=1 where id=#{id}")
    void update(@Param("id") Integer id);
}
