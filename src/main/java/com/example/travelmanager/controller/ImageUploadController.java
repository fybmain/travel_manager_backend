package com.example.travelmanager.controller;

import com.example.travelmanager.controller.bean.ErrorBean;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.service.image.ImageUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageUploadController {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandler.class);

    //Errors
    private static final ErrorBean ErrFileGet = new ErrorBean(1, "1");



    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 1. 从前端获取图片
        if(file.isEmpty()) {
            return ResultBean.error(HttpStatus.BAD_REQUEST, ErrFileGet.getCode(), ErrFileGet.getMessage());
        }

        // 2. 上传
        try {
            ImageUploader.upload(file);
        }
        catch (IOException e) {
            throw e;
        }

        return ResultBean.success();
    }
}
