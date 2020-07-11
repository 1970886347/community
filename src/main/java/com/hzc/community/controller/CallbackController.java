package com.hzc.community.controller;

import com.hzc.community.entity.AuthTokenEntity;
import com.hzc.community.entity.User;
import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.UserModel;
import com.hzc.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class CallbackController {

    @Autowired
    private GithubProvider provider;
    @Autowired
    private UserMapper userMapper;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;
    @RequestMapping("/callback")
    public String getCallback(@RequestParam(name="code")String code, @RequestParam(name="state")String state, HttpServletResponse response, HttpServletRequest request, Model model){

        if(code==null||state==null||!state.equals("1")){
            model.addAttribute("error","登录失败");
            return "index";
        }
        AuthTokenEntity entity=new AuthTokenEntity();
        entity.setClient_id(clientId);
        entity.setClient_secret(clientSecret);
        entity.setCode(code);
        entity.setState(state);
        entity.setRedirect_uri(redirectUri);
        String githubToken=provider.getAuthToken("https://github.com/login/oauth/access_token",entity);
        User githubUser=provider.getUser(githubToken);
        String currentLocation=(String) request.getSession().getAttribute("currentLocation");
        if(githubUser!=null){
            String token = UUID.randomUUID().toString();
            UserModel user=userMapper.selectByAccount(Long.parseLong(githubUser.getId()));
            if(user==null) {
                user=new UserModel();
                user.setAccountId(Long.parseLong(githubUser.getId()));
                user.setName(githubUser.getName());
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModify(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatar_url());
            }else{
                user.setToken(token);
            }
            userMapper.delete(user.getAccountId());
            userMapper.insert(user);
            Cookie cookie=new Cookie("token",token);
            response.addCookie(cookie);
            if (currentLocation.equals("index"))
            return "redirect:/";
            else if(currentLocation.equals("publish")){
                return "redirect:/publish";
            }else if(currentLocation.equals("question")){
                Integer id=(Integer) request.getSession().getAttribute("id");
                return "redirect:/question?id="+id;
            }
        }else{
            model.addAttribute("error","登录失败");
        }
        return "index";
    }
}
