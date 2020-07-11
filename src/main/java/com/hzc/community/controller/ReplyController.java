package com.hzc.community.controller;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.mapper.ReplyMapper;
import com.hzc.community.model.Reply;
import com.hzc.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ReplyController {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @PostMapping("/reply")
    public  String doReply(Integer questionId,Integer beReplied, String content, HttpServletRequest request, RedirectAttributes model){
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
        if(user==null){
            model.addFlashAttribute("error","你尚未登录,请先登录");
            return "redirect:/question?id="+questionId;
        }
        if(content==null||content.equals("")){
            model.addFlashAttribute("errors","回复内容不能为空");
            return "redirect:/question?id="+questionId;
        }
        Reply reply=new Reply();
        reply.setUserId(user.getAccountId());
        reply.setQuestionId(questionId);
        reply.setContent(content);
        reply.setGmtCreate(System.currentTimeMillis());
        reply.setBeReplied(beReplied);
        replyMapper.insert(reply);
        Integer commentCount=questionMapper.selectById(questionId).getCommentCount();
        commentCount+=1;
        questionMapper.update(questionId,null,commentCount);
        return "redirect:/question?id="+questionId;
    }
}
