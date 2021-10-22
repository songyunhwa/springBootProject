package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.dto.PlaceDto;
import com.example.yhwasongtest.place.model.LocationModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.repository.DessertRepository;
import com.example.yhwasongtest.place.repository.FoodRepository;
import com.example.yhwasongtest.place.repository.LocationRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.youtube.service.YoutubeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(PlaceService.class);

    private LocationRepository locationRepository;


    @Autowired
    public LocationService( LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public void saveLocation(PlaceModel placeModel, PlaceDto placeDto) {
        LocationModel locationModel = locationRepository.findByPlaceIdAndAddress(placeModel.getId(), placeDto.getLocation());
        if(locationModel ==null ) {
            locationModel = new LocationModel();
            locationModel.setName(placeModel.getName());
            locationModel.setPlaceId(placeModel.getId());
            locationModel.setAddress(placeDto.getLocation());
            locationRepository.save(locationModel);
        }
    }
}
