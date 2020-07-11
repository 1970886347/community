package com.hzc.community.controller;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.Question;
import com.hzc.community.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/question")
    public String showQuestion(@RequestParam("id")Integer id, HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserModel user = userMapper.select(token);
                    if (user != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user", user);
                    }
                    break;
                }
            }
        Question question=questionMapper.selectById(id);
        int viewCount=question.getViewCount();
        viewCount+=1;
        questionMapper.update(question.getId(),viewCount,null);
        question.setViewCount(viewCount);
        model.addAttribute("question",question);
        request.getSession().setAttribute("currentLocation","question");
        request.getSession().setAttribute("id",id);
        return "question";
    }
}
