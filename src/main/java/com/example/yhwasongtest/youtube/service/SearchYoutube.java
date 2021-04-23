package com.example.yhwasongtest.youtube.service;

import com.example.yhwasongtest.youtube.model.YoutubeModel;
import com.example.yhwasongtest.youtube.repository.YoutubeRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    @Autowired
    YoutubeRepository youtubeRepository;

    public void SearchService(YoutubeRepository youtubeRepository) {
        this.youtubeRepository = youtubeRepository;
    }

    private static String PROPERTIES_FILENAME = "youtube.properties";

    public boolean searchYoutube(String msg) throws IOException, JSONException {

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
        apiurl += "&q="+ URLEncoder.encode(msg,"UTF-8");

        String nextToken = null;
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
            String videoId = snippet.getString("videoId");

            YoutubeModel youtubeModel = new YoutubeModel();
            youtubeModel.setPublishedAt(publishedAt);
            youtubeModel.setChannelId(channelId);
            youtubeModel.setVideoId(videoId);
            youtubeModel.setTitle(title);
            youtubeModel.setDescription(description);
            youtubeModel.setChannelTitle(channelTitle);

            youtubeRepository.save(youtubeModel);
        }

        //if(nextToken != null) {
        //    searchYoutube(msg);
        //}

        return true;
    }



}