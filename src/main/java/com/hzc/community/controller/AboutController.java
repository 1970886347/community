package com.hzc.community.controller;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.model.Question;
import com.hzc.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AboutController {
    @Autowired
    private QuestionMapper questionMapper;
    @GetMapping("/aboutMe")
    public String aboutMe(HttpServletRequest request, Model model){
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
        if(user!=null){
            List<Question> myQuestions=questionMapper.listByAccountId(user.getAccountId());
            model.addAttribute("questions",myQuestions);
        }
        return "me";
    }
}
