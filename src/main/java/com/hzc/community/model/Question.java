package com.hzc.community.model;

import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Question {
    private int id;
    @NotEmpty(message ="标题不能为空")
    private String title;
    @NotEmpty(message = "内容不能为空")
    private String description;
    private String tag;
    private long creator;
    private int commentCount;
    private int viewCount;
    private int likeCount;
    private long gmtCreate;
    private long gmtModify;
    private UserModel userModel;
    private List<Tag> tags;
    private List<Reply>replies;
    private String[]tagArrays;

    public String[] getTagArrays() {
        return tagArrays;
    }

    public void setTagArrays(String[] tagArrays) {
        this.tagArrays = tagArrays;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(long gmtModify) {
        this.gmtModify = gmtModify;
    }
}
