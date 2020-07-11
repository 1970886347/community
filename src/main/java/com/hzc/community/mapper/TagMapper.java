package com.hzc.community.mapper;

import com.hzc.community.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {
    void insertTags(List<Tag> tags);
    List<Tag> selectByQuestionId(@Param("questionId") Integer questionId);
}
