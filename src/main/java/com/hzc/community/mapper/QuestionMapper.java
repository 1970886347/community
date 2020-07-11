package com.hzc.community.mapper;

import com.hzc.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface QuestionMapper {
    @Insert("insert into question(title,description,creator,gmt_create,gmt_modify) values(#{title},#{description},#{creator},#{gmtCreate},#{gmtModify})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    void insert(Question question);
    List<Question> listQuestions(@Param("start")Integer start,@Param("pageSize")Integer pageSize, @Param("filter")String filter,@Param("condition")Long contion);
    int count(@Param("condition") Long condition);
    int countNoReplyQuestion();
    List<Question>listNoReplyQuestion(@Param("start")Integer start,@Param("pageSize")Integer pageSize);
    Question selectById(@Param("id") Integer id);
    void update(@Param("id")Integer id,@Param("viewCount")Integer viewCount,@Param("commentCount")Integer commentCount);
    List<Question> listByAccountId(long id);
}
