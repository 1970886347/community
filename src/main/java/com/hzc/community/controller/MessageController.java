package com.hzc.community.controller;

import com.hzc.community.mapper.ReplyMapper;
import com.hzc.community.mapper.SubReplyMapper;
import com.hzc.community.model.Reply;
import com.hzc.community.model.SubReply;
import com.hzc.community.model.UserModel;
import com.hzc.community.service.NewMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private SubReplyMapper subReplyMapper;
    @Autowired
    private NewMessageService messageService;
    @GetMapping("/message")
    public String listMessage(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
        if(user!=null){
            List<Reply>replyList=replyMapper.listByReplied(user.getAccountId());
            List<SubReply>subReplyList=subReplyMapper.listByReplied(user.getAccountId());
            model.addAttribute("replyList",replyList);
            model.addAttribute("subReplyList",subReplyList);
            int messageCount=messageService.countNewMessage(user.getAccountId());
            session.setAttribute("messageCount",messageCount);
        }
        return "newMessage";
    }
}
