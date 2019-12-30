package com.example.travelmanager.service.image;

import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadResult;
import com.aliyun.oss.model.UploadFileRequest;
import com.aliyun.oss.model.UploadFileResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ImageUploader {
    private static String URLprefix = "https://picturesbed.oss-cn-hangzhou.aliyuncs.com/";

    public static String upload(MultipartFile file) throws IOException {
        // 原文件名
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);

        String newName = "travelmanager/" + generateFileName(fileName) + "." + suffix;
        OSSUtil.uploadPic(file, newName);

        return URLprefix + newName;
    }


    private static String generateFileName(String oriName) {
        Long l = System.currentTimeMillis();
        String timeString = l.toString();
        Integer hashCode = timeString.hashCode();
        String timeHashName;

        if(hashCode < 0) {
            Integer negInt = (-hashCode);
            timeHashName = "0" + negInt.toString();
        }
        else {
            timeHashName = hashCode.toString();
        }

        Integer salt = timeHashName.hashCode();
        String saltString = salt.toString();

        Integer oriInteger = oriName.hashCode();
        String oriHash = oriInteger.toString();

        return timeHashName + saltString + oriHash;
    }
}