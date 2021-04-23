package com.example.yhwasongtest.youtube.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

@Service
public class searchMango {

    public String searchMango(String location) throws Exception {

        String apiurl = "https://www.mangoplate.com/search/";
        apiurl +=  URLEncoder.encode(location,"UTF-8");
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

        String result = response.toString();
        result = result.replaceAll(" ", "");

        int start = 0;
        int data = 0; //들어가는 데이터수
        String subString = null;
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            int startIndex = result.indexOf("<figcaption>", start + 1);
            int endIndex = result.indexOf("</figcaption>" , start + 1);

            if(startIndex >= 0) {
                subString = result.substring(startIndex, endIndex);
                arrayList.add(result);
                start += startIndex;
                data++;
            }


        } while (subString != null && data < 20);

        return arrayList.toString();
    }

}
