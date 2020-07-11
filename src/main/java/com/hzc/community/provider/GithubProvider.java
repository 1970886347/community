package com.hzc.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hzc.community.entity.AuthTokenEntity;
import com.hzc.community.entity.User;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAuthToken(String url, AuthTokenEntity entity)  {
         MediaType mediaType= MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(entity), mediaType);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
        Response response = null;
        String result=null;
        try {
            response = client.newCall(request).execute();
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.split("&")[0].split("=")[1];
    }
    public User getUser(String token){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+token)
                    .build();

        Response response = null;
        try {
            response = client.newCall(request).execute();
            String result=response.body().string();
            User user=JSON.parseObject(result,User.class);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()){

        }
        return null;
    }
}
