package com.hzc.community.service;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.model.Question;
import com.hzc.community.util.GmtHelper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    GmtHelper gmtHelper;
    public List<Question>listQuestions(Integer start,Integer pageSize, String filter){
        List<Question>questions=null;
        if(filter==null||filter.equals("new")){
            questions=questionMapper.listQuestions(start,pageSize,"gmt_create",null);
        }else if(filter.equals("hot30")||filter.equals("hot7")){
            Long condition=gmtHelper.getGmtMillions(filter);
            questions=questionMapper.listQuestions(start,pageSize,"comment_count",condition);
        }else if(filter.equals("hot")){
            questions=questionMapper.listQuestions(start,pageSize,"comment_count",null);
        }else if(filter.equals("no")){
            questions=questionMapper.listNoReplyQuestion(start,pageSize);
        }
        if(questions==null){
            return new ArrayList<Question>();
        }
        return questions;
    }
    public int countQuestions(String filter){
        int count=0;
        if(filter==null||filter.equals("new")||filter.equals("hot")){
            count=questionMapper.count(null);
        }else if(filter.equals("hot30")||filter.equals("hot7")) {
            Long condition = gmtHelper.getGmtMillions(filter);
            count = questionMapper.count(condition);
          }else if(filter.equals("no")){
            count=questionMapper.countNoReplyQuestion();
        }
        return count;
    }
}
