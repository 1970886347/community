package com.hzc.community.util;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PageHelper {
    private int pageIndex;
    private int pageSize=3;
    private int start;

    public PageHelper(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageIndex() {
        if(pageIndex<=0)
            pageIndex=1;
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public int getStart(){
        start=pageSize*(pageIndex-1);
        return start;
    }
}
