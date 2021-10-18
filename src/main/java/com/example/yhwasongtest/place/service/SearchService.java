package com.example.yhwasongtest.place.service;

import com.example.yhwasongtest.common.CommonCode;
import com.example.yhwasongtest.place.model.DessertModel;
import com.example.yhwasongtest.place.model.LocationModel;
import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.repository.DessertRepository;
import com.example.yhwasongtest.place.repository.LocationRepository;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final DessertRepository dessertRepository;
    private final PlaceRepository placeRepository;
    private final LocationRepository locationRepository;

    @Value(value = "${google.place.key}")
    String apiKey;

    public SearchService(DessertRepository dessertRepository, PlaceRepository placeRepository, LocationRepository locationRepository) {
        this.dessertRepository = dessertRepository;
        this.placeRepository = placeRepository;
        this.locationRepository = locationRepository;
    }

    public List<PlaceModel> getDessert(String subCategory) {
        List<PlaceModel> results = new ArrayList<>();
        results = placeRepository.findByNameContaining(subCategory);

        List<PlaceModel> results_sub = new ArrayList<>();
        results_sub = placeRepository.findBySubCategory(subCategory);
        for (PlaceModel placeModel : results_sub) {
            if (!results.contains(placeModel)) {
                results.add(placeModel);
            }
        }
        return results;
    }

    public void setLocation(String placeName) throws Exception {

        String text = URLEncoder.encode(placeName, "UTF-8");

        String apiUrl = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=" + text +"&inputtype=textquery&fields=formatted_address%2Cname%2Crating%2Copening_hours%2Cgeometry&key="+ this.apiKey;


        HttpURLConnection con;
        try {
            URL url = new URL(apiUrl);

            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }

        String inputLine;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            StringBuffer response = new StringBuffer();

            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine + '\n');
            }
            br.close();

            JSONParser jsonParser = new JSONParser();
            Object obj = jsonParser.parse(response.toString());
            JSONObject jsonObj = (JSONObject) obj;

            JSONArray jsonArray = (JSONArray) jsonObj.get("candidates");
            //JSONArray jsonArray = (JSONArray) new JSONParser().parse( result );

            if(jsonArray.size() == 0) return;

            String address = null;
            String lat = null;
            String lng =null;
            PlaceModel placeModel = placeRepository.findByName(placeName);

            for (int t = 0; t < jsonArray.size(); t++) {
                JSONObject item = (JSONObject) jsonArray.get(t);
                 address = (String) item.get("formatted_address");
                JSONObject geometry = (JSONObject) item.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                 lat = location.get("lat").toString();
                 lng = location.get("lng").toString();

                LocationModel locationModel = locationRepository.findByPlaceIdAndAddress(placeModel.getId(), address);
                if (locationModel == null) {
                    locationModel= new LocationModel();
                    locationModel.setName(placeName);
                    locationModel.setAddress(address);
                    locationModel.setLat(lat);
                    locationModel.setLng(lng);
                    locationModel.setPlaceId(placeModel.getId());
                    locationRepository.save(locationModel);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public JSONObject getCities() {
        String[] city = {"서울특별시","부산광역시","대구광역시","인천광역시","광주광역시","대전광역시","울산광역시",
                "세종특별자치","경기도","의정부","강원도","충청북도","충청남도","전라북도",
                "전라남도","경상북도","포항시","경상남도","진주시","제주특별자치"};

        JSONObject jsonObj = new JSONObject();
        for(int i=0; i<city.length; i++){
            jsonObj.put(i, city[i]);
        }
        return jsonObj;

    }

    public JSONArray getLocationPlaces(String address) {
        List<LocationModel> locationModels = locationRepository.findByAddressContains(address);

        List<PlaceModel> placeModels = new ArrayList<>();
        if (locationModels != null) {
            for(LocationModel locationModel : locationModels) {
                PlaceModel placeModel = placeRepository.findById(locationModel.getPlaceId()).get();
                if (placeModel != null) {
                    placeModels.add(placeModel);
                }
            }
        }
        JSONArray jsonArray = CommonCode.convertToJSON(placeModels);
        return jsonArray;

    }

}
