package com.example.travelmanager.controller;

import com.example.travelmanager.controller.bean.ErrorBean;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.entity.Picture;
import com.example.travelmanager.entity.User;
import com.example.travelmanager.response.image.ImageUploadResponse;
import com.example.travelmanager.service.MyLogger;
import com.example.travelmanager.service.image.ImageUploader;
import com.example.travelmanager.service.image.PictureBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageUploadController {

    //Errors
    private static final ErrorBean ErrFileGet = new ErrorBean(1, "1");

    @Autowired
    public PictureBuilder pictureBuilder;

    // APIs
    // TODO swagger 错误状态码对应描述
    @ApiOperation(value = "上传图片")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "上传成功", response = ImageUploadResponse.class),
            @ApiResponse(code = 500, message = "上传失败", response = ResultBean.class)
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 1. 从前端获取图片
        if(file.isEmpty()) {
            return ResultBean.error(HttpStatus.BAD_REQUEST, ErrFileGet.getCode(), ErrFileGet.getMessage());
        }

        // 2. 上传
        String url;
        try {
            url = ImageUploader.upload(file);
        }
        catch (IOException e) {
            throw e;
        }

        // 3. Create Picture in database

        Picture p = pictureBuilder.createPicture(url);
        ImageUploadResponse response = new ImageUploadResponse();
        response.setPictureId(p.getId());

        return ResultBean.success(HttpStatus.CREATED, response);
    }
}
