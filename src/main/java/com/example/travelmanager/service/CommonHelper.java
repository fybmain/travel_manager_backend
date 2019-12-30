package com.example.travelmanager.service;

import com.example.travelmanager.config.Constant;
import org.springframework.util.DigestUtils;

import java.util.Calendar;
import java.util.Date;

public class CommonHelper {
    public static Date getUTCDate() {
        Calendar cal = Calendar.getInstance();
        //获得时区和 GMT-0 的时间差,偏移量
        int offset = cal.get(Calendar.ZONE_OFFSET);
        //获得夏令时  时差
        int dstoff = cal.get(Calendar.DST_OFFSET);
        cal.add(Calendar.MILLISECOND, - (offset + dstoff));
        return cal.getTime();
    }

    /**MD5加密算法**/
    public static String MD5Encode(String text){
        text = text + "/" + Constant.SIGNING_KEY;
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }
}
