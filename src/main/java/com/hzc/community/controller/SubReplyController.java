package com.hzc.community.controller;

import com.hzc.community.mapper.SubReplyMapper;
import com.hzc.community.model.SubReply;
import com.hzc.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SubReplyController {
    @Autowired
    private SubReplyMapper subReplyMapper;
    @PostMapping("/subReply")
    public String doSubReply(SubReply subReply, HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
        if(user==null){
            model.addAttribute("error","你尚未登录,请先登录");
            return "redirect:/question?id="+subReply.getQuestionId();
        }
        subReply.setUserId(user.getAccountId());
        subReply.setGmtCreate(System.currentTimeMillis());
        subReplyMapper.insert(subReply);
        return "redirect:/question?id="+subReply.getQuestionId();
    }
}
