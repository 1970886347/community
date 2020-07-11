package com.hzc.community.util;

import org.springframework.stereotype.Component;

@Component
public class FileNameUtil {
    public String getFileName(String origineName){
        long time=System.currentTimeMillis();
        String fileName=String.valueOf(time)+origineName;
        return fileName;
    }
}
