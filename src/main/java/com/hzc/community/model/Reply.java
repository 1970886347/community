package com.hzc.community.model;

import java.util.List;

public class Reply {
    private int id;
    private long userId;
    private int questionId;
    private String content;
    private UserModel user;
    private int beReplied;
    private List<SubReply> subReplyList;
    private Integer subReplyNumbers;
    private int status;
    private Question question;
    private Reply reply;

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getSubReplyNumbers() {
        return subReplyNumbers;
    }

    public void setSubReplyNumbers(Integer subReplyNumbers) {
        this.subReplyNumbers = subReplyNumbers;
    }

    public List<SubReply> getSubReplyList() {
        return subReplyList;
    }

    public void setSubReplyList(List<SubReply> subReplyList) {
        this.subReplyList = subReplyList;
    }

    public int getBeReplied() {
        return beReplied;
    }

    public void setBeReplied(int beReplied) {
        this.beReplied = beReplied;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    private long gmtCreate;
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
