package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

/**
 * Prints a list of videos based on a search term.
 *
 * @author Jeremy Walker
 */
@Service
public class SearchYoutube {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SearchYoutube.class);

    @Autowired
    YoutubeRepository youtubeRepository;

    @Autowired
    PlaceService placeService;


    public void SearchService(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    private static String PROPERTIES_FILENAME = "youtube.properties";

    public String searchYoutube(String msg, String nextToken) throws Exception {
        String result = "";
        Properties properties = new Properties();
        try {
            InputStream in = SearchYoutube.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        String apiKey = properties.getProperty("youtube.apikey");

        String apiurl = "https://www.googleapis.com/youtube/v3/search";
        apiurl += "?key=" + apiKey;
        apiurl += "&part=snippet&type=video&maxResults=20&videoEmbeddable=true";
        apiurl += "&q="+ URLEncoder.encode(msg+" 디저트","UTF-8");

        if(nextToken != null) {
            apiurl +="&pageToken=" + nextToken;
        }
        //https://www.youtube.com/watch?v=qhnAsRwed5s URL 로 나중에 이동할때 하는 방식
        URL url = new URL(apiurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while((inputLine = br.readLine()) != null) {
            response.append(inputLine + '\n');
        }
        br.close();

        JSONObject jsonObject = new JSONObject(response.toString());

        if(jsonObject.has("nextPageToken")){
            nextToken = jsonObject.getString("nextPageToken");
        } else {
            nextToken = null;
        }

        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for(int t=0; t<jsonArray.length(); t++) {
            JSONObject item = jsonArray.getJSONObject(t);
            JSONObject snippet = item.getJSONObject("snippet");

            String publishedAt = snippet.getString("publishedAt");
            String channelId = snippet.getString("channelId");
            String title = snippet.getString("title");
            String description = snippet.getString("description");
            String channelTitle = snippet.getString("channelTitle");

            snippet = item.getJSONObject("id");
            String videoId = snippet.getString("videoId");

            result += description + "\n";

            // 같은 채널명이 아니면 continue;
            if(channelTitle.equals(msg)){
                continue;
            }

            YoutubeModel youtubeModel = new YoutubeModel();
            youtubeModel.setPublishedAt(publishedAt);
            youtubeModel.setChannelId(channelId);
            youtubeModel.setTitle(title);
            youtubeModel.setDescription(description);
            youtubeModel.setChannelTitle(channelTitle);
            youtubeModel.setVideoId(videoId);
            /*
            // placeModel 에 description 붙이기
            try {
                putDescription(description, youtubeModel);
            }catch (Exception e){
                logger.info("SearchYoutube error => " , e.toString());
                continue;
            }
            */
            youtubeRepository.save(youtubeModel);


        }


        //if(nextToken != null) {
        //    searchYoutube(msg, nextToken);
        //}

        return result;
    }

    public void putDescription(String description, YoutubeModel youtubeModel) throws Exception{

        String start_pattern="'";  // => 유투브마다 다를 것. 패턴이.
        String end_pattern="'";

        int startIndex =0;
        int endIndex =-1;
        while(startIndex != -1) {
            startIndex = description.indexOf(start_pattern, endIndex+1);
            if (startIndex == -1) {
                if (start_pattern == "'") {
                    start_pattern = "["; end_pattern = "]";
                    startIndex = description.indexOf(start_pattern, endIndex);
                } else {
                    start_pattern = "'"; end_pattern = "'";
                    startIndex = description.indexOf(start_pattern, endIndex);
                }
            }
            endIndex = description.indexOf(end_pattern, startIndex+1);

            if (startIndex > -1 && endIndex > -1 && endIndex - startIndex < 100) {
                String name = description.substring(startIndex+1, endIndex);
                PlaceModel placeModel = new PlaceModel();
                placeModel.setName(name);
                placeModel.setYoutube(youtubeModel);
                placeModel.setSubCategory("dessert");
                placeService.putPlace(placeModel);
            }
        }
    }



}