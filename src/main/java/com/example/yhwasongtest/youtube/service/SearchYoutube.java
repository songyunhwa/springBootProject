package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.place.model.PlaceModel;
import com.example.yhwasongtest.place.repository.PlaceRepository;
import com.example.yhwasongtest.place.service.PlaceService;
import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.json.JSONArray;
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
import java.util.ArrayList;
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

    @Autowired
    PlaceRepository placeRepository;


    public void SearchService(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    private static String PROPERTIES_FILENAME = "youtube.properties";

    public String searchYoutube(String msg, String category, String nextToken) throws Exception {
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
        apiurl += "&part=snippet&type=video&maxResults=50&videoEmbeddable=true";
        apiurl += "&q=" + URLEncoder.encode(msg + " " + category, "UTF-8");
        apiurl += "&field=items(id,snippet(description,publishedAt,channelId,title,channelTitle))";

        if (nextToken != null) {
            apiurl += "&pageToken=" + nextToken;
        }
        //https://www.youtube.com/watch?v=qhnAsRwed5s URL 로 나중에 이동할때 하는 방식
        URL url = new URL(apiurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine + '\n');
        }
        br.close();

        JSONObject jsonObject = new JSONObject(response.toString());

        if (jsonObject.has("nextPageToken")) {
            nextToken = jsonObject.getString("nextPageToken");
        } else {
            nextToken = null;
        }

        JSONArray jsonArray = jsonObject.getJSONArray("items");

        for (int t = 0; t < jsonArray.length(); t++) {
            JSONObject item = jsonArray.getJSONObject(t);
            JSONObject snippet = item.getJSONObject("snippet");

            String description = snippet.getString("description");
            String publishedAt = snippet.getString("publishedAt");
            String channelId = snippet.getString("channelId");
            String title = snippet.getString("title");
            String channelTitle = snippet.getString("channelTitle");

            snippet = item.getJSONObject("id");
            String videoId = snippet.getString("videoId");

            YoutubeModel existYoutube = youtubeRepository.findByVideoId(videoId);
            // 이미 youtube가 등록되어있다면 continue
            if (existYoutube != null)
                continue;

            /// description 에 ' 와 [ 가 없다면 continue;
            ArrayList<String> store;
            store = getDescription(apiKey, videoId);
            if (store.size() == 0) continue;

            // 같은 채널명이 아니면 continue;
             if (!channelTitle.contains(msg)) {
                 continue;
             }


            YoutubeModel youtubeModel = new YoutubeModel();
            youtubeModel.setPublishedAt(publishedAt);
            youtubeModel.setChannelId(channelId);
            youtubeModel.setTitle(title);
            youtubeModel.setDescription(description);
            youtubeModel.setChannelTitle(channelTitle);
            youtubeModel.setVideoId(videoId);

            // placeModel 에 description 붙이기
            try {
                putDescription(store, youtubeModel, category);

            } catch (Exception e) {
                logger.info("SearchYoutube error => ", e.toString());
                continue;
            }
        }

        if (nextToken != null) {
            searchYoutube(msg, category, nextToken);
        }

        return result;
    }

    public void putDescription(ArrayList<String> store, YoutubeModel youtubeModel, String category) throws Exception {

        for (String name : store) {
            PlaceModel placeModel = placeService.getPlaceByName(name);
            if (placeModel == null) {
                placeModel = new PlaceModel();
                placeModel.setName(name.replace(" ", ""));
                placeModel.setSubCategory(category);
                placeModel.setView(1);
                placeModel.setRecommend(0);
                placeModel.setArea("");
                placeModel.setNumber("");
                placeModel.setUrl("");
            } else {
                // 있는 장소라면 view +1
                placeModel.setView(placeModel.getView() + 1);
            }
            youtubeModel.setPlace(placeModel);

            if(placeModel.getYoutubes() != null)
                if(!placeModel.getYoutubes().contains(youtubeModel))
                    placeModel.getYoutubes().add(youtubeModel);
            else placeModel.setYoutubes(new ArrayList<>());

             placeRepository.save(placeModel);

        }

    }

    public ArrayList<String> getDescription(String apiKey, String videoId) throws Exception {


        String apiurl = "https://www.googleapis.com/youtube/v3/videos";
        apiurl += "?key=" + apiKey;
        apiurl += "&part=snippet";
        apiurl += "&id=" + videoId;
        apiurl += "&fields=items(snippet(description))";

        URL url = new URL(apiurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        ArrayList<String> store = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine + '\n');
            int end = -1;
            int start =0;
            if (inputLine.contains("'")) {
                while (start != -1) {
                    start = inputLine.indexOf("'", end + 1);
                    end = inputLine.indexOf("'", start + 1);
                    if(end == -1 )break;
                    if (start > -1 && end > -1 && end - start < 100)
                        store.add(inputLine.substring(start + 1, end));

                }
            }
            end = -1;
            start =0;
            if (inputLine.contains("[")) {
                while (start != -1) {
                    start = inputLine.indexOf("[", end + 1);
                    end = inputLine.indexOf("]", start + 1);
                    if(end == -1 )break;
                    if (start > -1 && end > -1 && end - start < 100)
                        store.add(inputLine.substring(start + 1, end));
                }
            }
        }
        br.close();
        return store;
    }


}