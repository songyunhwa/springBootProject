package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.PlaceRepository;
import com.example.yhwasongtest.place.controller.PlaceController;
import com.example.yhwasongtest.place.model.Place;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class PlaceService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    PlaceRepository placeRepository;
    @Autowired
    PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }
    public Place putPlace(Place place) throws Exception {
        Place existPlace = placeRepository.findByName(place.getName());
        if(existPlace == null)
            return placeRepository.save(place);
        else
            return existPlace;
    }
}
