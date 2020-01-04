package com.example.travelmanager.controller;

import com.example.travelmanager.config.WebException.PaymentControllerException;
import com.example.travelmanager.controller.bean.ResultBean;
import com.example.travelmanager.entity.Picture;
import com.example.travelmanager.response.image.ImageUploadResponse;
import com.example.travelmanager.service.image.ImageUploader;
import com.example.travelmanager.service.image.PictureBuilder;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/image")
public class ImageUploadController {

    @Autowired
    public PictureBuilder pictureBuilder;

    // APIs
    @ApiOperation(value = "上传图片")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "上传成功", response = ImageUploadResponse.class),
            @ApiResponse(code = 400, message = "后台获取图片失败 {code=2002, msg=...}", response = ResultBean.class),
            @ApiResponse(code = 500, message = "上传失败 {code=2001, msg=Picture uploaded failed} ", response = ResultBean.class)
    })
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws Exception {
        // 1. 从前端获取图片
        if(file.isEmpty()) {
            throw PaymentControllerException.ImageGetFailedException;
        }

        // 2. 上传
        String url;
        try {
            url = ImageUploader.upload(file);
        }
        catch (IOException e) {
            throw PaymentControllerException.ImageUploadFailedException;
        }

        // 3. Create Picture in database

        Picture p = pictureBuilder.createPicture(url);
        ImageUploadResponse response = new ImageUploadResponse();
        response.setPictureId(p.getId());

        return ResultBean.success(HttpStatus.CREATED, response);
    }
}
