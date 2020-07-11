package com.hzc.community.controller;

import com.hzc.community.mapper.ReplyMapper;
import com.hzc.community.mapper.SubReplyMapper;
import com.hzc.community.model.Reply;
import com.hzc.community.model.SubReply;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MessageDetailController {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private SubReplyMapper subReplyMapper;
    @GetMapping("/messageDetail")
    public String getMessageDetail(@Param("id")Integer id, @Param("type")String type, Model model){
        if(type!=null){
            if(type.equals("reply")){
                Reply reply=replyMapper.selectById(id);
                model.addAttribute("message",reply);
                replyMapper.update(reply.getId());
            }else if(type.equals("subReply")){
                SubReply subReply=subReplyMapper.selectById(id);
                model.addAttribute("message",subReply);
                subReplyMapper.update(subReply.getId());
            }
        }
        return "messageDetail";
    }
}
