package com.hzc.community.controller;

import com.hzc.community.mapper.UserMapper;
import com.hzc.community.model.UserModel;
import com.hzc.community.util.FileNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class ChangeHeadController {
    @Autowired
    FileNameUtil nameUtil;
    @Autowired
    UserMapper userMapper;
    @PostMapping("/changHead")
    public String doChangeHead(HttpServletRequest request, @RequestParam("headImg") MultipartFile multipartFile){
        String root=null;
        try {
            root=ResourceUtils.getURL("classpath:").getPath()+"static";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String originName=multipartFile.getOriginalFilename();
        String saveName=nameUtil.getFileName(originName);
        String path=root+"/img/";
        File file=new File(path);
        if(!file.exists()){
            file.mkdirs();
        }
        File saveFile=new File(path+saveName);
        try {
            multipartFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpSession session=request.getSession();
        UserModel user=(UserModel) session.getAttribute("user");
        userMapper.updateAvatarUrl("/img/"+saveName,user.getAccountId());
        user.setAvatarUrl("/img/"+saveName);
        return "me";
    }
}
