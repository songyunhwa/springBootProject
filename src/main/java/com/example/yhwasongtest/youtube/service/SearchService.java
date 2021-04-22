package com.example.yhwasongtest.youtube.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

/**
 * Prints a list of videos based on a search term.
 *
 * @author Jeremy Walker
 */
@Service
public class SearchService {

    private static String PROPERTIES_FILENAME = "youtube.properties";

    public String searchYoutube(String msg) throws IOException {

        Properties properties = new Properties();
        try {
            InputStream in = SearchService.class.getResourceAsStream("/" + PROPERTIES_FILENAME);
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

        URL url = new URL(apiurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        return response.toString();
    }

}