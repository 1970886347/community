package com.hzc.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginOutController {
    @GetMapping("/loginOut")
    public String doLoginOut(HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        session.removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        response.addCookie(cookie);
        String currentLocation=(String) session.getAttribute("currentLocation");
        if(currentLocation.equals("index"))
            return "redirect:/";
        if(currentLocation.equals("publish"))
            return "redirect:/publish";
        if(currentLocation.equals("question")) {
            Integer id = (Integer) session.getAttribute("id");
            return "redirect:/question?id="+id;
        }
        return "/";
    }
}
