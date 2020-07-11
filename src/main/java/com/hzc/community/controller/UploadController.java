package com.hzc.community.controller;

import com.alibaba.fastjson.JSONObject;
import com.hzc.community.entity.UploadResonse;
import com.hzc.community.util.FileNameUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

@RestController
public class UploadController {
    @Autowired
    FileNameUtil nameUtil;
    @RequestMapping("/upload")
    public String upload(@RequestParam("files")MultipartFile files,HttpServletRequest request){
        String originName=files.getOriginalFilename();
        String fileName=nameUtil.getFileName(originName);
        String root= null;
        try {
            root = ResourceUtils.getURL("classpath:").getPath()+"static";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String path=root+ "/img/";
        File file=new File(path);
        if(!file.exists()){
                file.mkdirs();
        }
        File saveFile=new File(path+fileName);
        try {
            files.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        UploadResonse resonse=new UploadResonse();
        resonse.setErrno(0);
        resonse.setData(new String[]{"/img/"+fileName});
        String result=JSONObject.toJSONString(resonse);
        return result;
    }
}
