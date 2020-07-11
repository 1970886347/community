package com.hzc.community.service;

import com.hzc.community.mapper.ReplyMapper;
import com.hzc.community.mapper.SubReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewMessageService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private SubReplyMapper subReplyMapper;
    public Integer countNewMessage(Long accountId){
        Integer replyCount=replyMapper.countByBeReplied(accountId);
        Integer subReplyCount=subReplyMapper.countByBeReplied(accountId);
        return replyCount+subReplyCount;
    }
}
