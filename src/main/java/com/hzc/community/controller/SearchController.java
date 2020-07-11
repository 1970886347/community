package com.hzc.community.controller;

import com.hzc.community.model.Question;
import com.hzc.community.provider.LuceneProvider;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    private LuceneProvider luceneProvider;
    @GetMapping("/search")
    public String doSearch(@Param("key")String key, Model model){
        List<Question>questions=luceneProvider.getScores(key);
        model.addAttribute("questions",questions);
        return "searchResult";
    }
}
