package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.repository.DessertRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final DessertRepository dessertRepository;
    private final PlaceRepository placeRepository;

    public SearchService(DessertRepository dessertRepository, PlaceRepository placeRepository) {
        this.dessertRepository = dessertRepository;
        this.placeRepository = placeRepository;
    }

    public List<DessertModel> getDessert() {

        List<DessertModel> results = new ArrayList<>();
        List<DessertModel> dessertModels = dessertRepository.findAll();
        dessertModels.forEach(dessertModel -> {
            if(!results.contains(dessertModel)){
                results.add(dessertModel);
            }
        });
        return dessertModels;
    }

    public List<PlaceModel> selectDessert(String subCategory) {
        return placeRepository.findBySubCategory(subCategory);
    }
}