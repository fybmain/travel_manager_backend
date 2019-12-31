package com.example.travelmanager.dao;

import org.springframework.data.repository.CrudRepository;
import com.example.travelmanager.entity.Picture;
import org.springframework.stereotype.Repository;

//@Repository
public interface PictureDao extends CrudRepository<Picture, Integer> {

}
