package com.hzc.community.controller;

import com.hzc.community.mapper.QuestionMapper;
import com.hzc.community.mapper.TagMapper;
import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.Question;
import com.hzc.community.model.Tag;
import com.hzc.community.model.UserModel;
import com.hzc.community.provider.LuceneProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/publish")
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    TagMapper tagMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private LuceneProvider luceneProvider;
    @GetMapping
    public String publishQuestion(HttpServletRequest request){
        HttpSession session=request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserModel user = userMapper.select(token);
                    if (user != null) {
                        session.setAttribute("user", user);
                    }
                    break;
                }
            }
        UserModel user=(UserModel) session.getAttribute("user");
        if(user!=null){
            request.getSession().setAttribute("currentLocation","publish");
            return "publish";
        }
        return null;
    }
   @PostMapping
    public String doPublish(@Validated Question question,Errors errors, Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
       model.addAttribute("title", question.getTitle());
       model.addAttribute("description",question.getDescription());
       model.addAttribute("tag",question.getTag());
        if(user==null){
            model.addAttribute("errors","你尚未登录，请先登录");
            return "publish";
        }
        if(errors.hasErrors()){
            StringBuffer buffer=new StringBuffer();
            if(question!=null) {
                if(question.getTitle()==null||question.getTitle().equals("")){
                    buffer.append("标题不能为空");
                }
                if(question.getDescription()==null||question.getDescription().equals("")){
                    buffer.append("内容不能为空");
                }
            }else{
              buffer.append("标题和内容不能为空");
            }
            model.addAttribute("errors",buffer.toString());
            return "publish";
        }
        question.setCreator(user.getAccountId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModify(question.getGmtCreate());
        questionMapper.insert(question);
        luceneProvider.addScore(question);
        String[]tags=question.getTag().split(",");
        List<Tag> tagList=new ArrayList<>();
        for(String t:tags){
            if(t!=null&&!t.equals("")){
                if(t.length()>10){
                    model.addAttribute("errors","单个标签内容过长");
                    return "publish";
                }
                Tag tag=new Tag();
                tag.setQuestionId(question.getId());
                tag.setContent(t);
                tagList.add(tag);
            }
        }
        if(tagList.size()>0){
            tagMapper.insertTags(tagList);
        }
       return "redirect:/";
   }
}
