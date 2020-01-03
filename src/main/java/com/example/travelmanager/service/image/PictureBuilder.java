package com.example.travelmanager.service.image;

import com.example.travelmanager.dao.PictureDao;
import com.example.travelmanager.entity.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PictureBuilder {

    @Autowired
    private PictureDao pictureDao;

    public Picture createPicture(String url) {
        Picture p = new Picture();
        p.setUrl(url);
        p.setUploadTime();

        pictureDao.save(p);
        return p;
    }
}