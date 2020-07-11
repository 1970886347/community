package com.hzc.community.controller;

import com.hzc.community.service.NewMessageService;
import com.hzc.community.service.QuestionService;
import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.Question;
import com.hzc.community.model.UserModel;
import com.hzc.community.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NewMessageService messageService;
    @GetMapping("/")
    public String index(Model model, HttpServletRequest request,Integer page,String filter) {
        Cookie[] cookies = request.getCookies();
        HttpSession session = request.getSession();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserModel user = userMapper.select(token);
                    if (user != null) {
                        session = request.getSession();
                        session.setAttribute("user", user);
                    }
                    break;
                }
            }
        UserModel user=(UserModel) session.getAttribute("user");
            if(user!=null){
               int messageCount=messageService.countNewMessage(user.getAccountId());
               session.setAttribute("messageCount",messageCount);
            }
                if(page==null)
                    page=1;
        if(filter==null){
            filter="new";
        }

                PageHelper pageHelper = new PageHelper(page);
                List<Question> questions = questionService.listQuestions(pageHelper.getStart(), pageHelper.getPageSize(),filter);
                int totalScore=questionService.countQuestions(filter);
                int maxPage=totalScore/pageHelper.getPageSize();
                if(totalScore%pageHelper.getPageSize()!=0)
                    maxPage+=1;
                model.addAttribute("questions", questions);
                model.addAttribute("maxPage",maxPage);
                model.addAttribute("currentIndex",pageHelper.getPageIndex());
                model.addAttribute("filter",filter);
                request.getSession().setAttribute("currentLocation","index");
                return "index";
            }
}
