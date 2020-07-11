package com.hzc.community.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class GmtHelper {
   public long getGmtMillions(String filter){
       int before=0;
       if(filter.equals("hot30")){
           before=30;
       }else if(filter.equals("hot7")){
           before=7;
       }
       Calendar c = Calendar.getInstance();
       Date date=null;
       try {
           SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
           String currentDate=format.format(new Date());
           date =format.parse(currentDate);
       } catch (ParseException e) {
           e.printStackTrace();
       }
       c.setTime(date);
       int day=c.get(Calendar.DATE);
       c.set(Calendar.DATE,day-before);
       return c.getTimeInMillis();
   }
}
