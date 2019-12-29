package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.Picture;

public interface PictureDao extends CrudRepository<Picture, Integer> {

}
