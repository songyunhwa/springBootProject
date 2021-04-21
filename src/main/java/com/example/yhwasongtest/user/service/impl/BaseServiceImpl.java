package com.example.yhwasongtest.user.service.impl;

import com.example.yhwasongtest.user.model.BaseQuestion;
import com.example.yhwasongtest.user.model.UserModel;
import com.example.yhwasongtest.user.repository.BaseRepository;
import com.example.yhwasongtest.user.repository.UserRepository;
import com.example.yhwasongtest.user.service.repository.BaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.*;

@Service
public class BaseServiceImpl implements BaseService {
    private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    private BaseRepository baseRepository;
    private UserRepository userRepository;

    @Autowired
    public BaseServiceImpl(BaseRepository baseRepository,
                           UserRepository userRepository){
        this.baseRepository = baseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BaseQuestion insertQuestion(BaseQuestion baseQuestion){

        List<BaseQuestion> baseQuestionOptional = baseRepository.findAllById(baseQuestion.getId());

            BaseQuestion base = new BaseQuestion(baseQuestion.getTitle(), baseQuestion.getContent());
            if(baseQuestionOptional.size() > 0) base.setId(baseQuestionOptional.get(0).getId());
            else base.setId(99);

            return baseRepository.save(base);
    }
    @Override
    public UserModel insertUser(UserModel userModel){
        Optional<UserModel> userModelOptional = userRepository.findAllByName(userModel.getName());
        if(!userModelOptional.isPresent()) userRepository.save(userModel);
        return userModelOptional.get();
    }

    public String getToken(String id, String password) throws Exception{

        ObjectMapper mapper = new ObjectMapper();
        JSONObject object = new JSONObject();
        Map<String, String> map = new HashMap<String, String>();
        object.put("typ", "JWT");
        object.put("alg", "HS256");
        String bytes = mapper.writeValueAsString(object);
        String headerResult = Base64.getUrlEncoder().encodeToString(bytes.getBytes());
        headerResult = headerResult.replaceAll("=", "");

        JSONObject object1 = new JSONObject();
        object1.put("iss", "mapyhwasong.com");
        object1.put("exp", "1485270000000");
        object1.put("https://github.com/songyunhwa/springBootProject_back", true);
        object1.put("userId", id);
        object1.put("password", password);
        String bytes1 = mapper.writeValueAsString(object1);
        String bodyResult = Base64.getUrlEncoder().encodeToString(bytes1.getBytes());
        bodyResult = bodyResult.replaceAll("=", "");

        String passwordHashed = BCrypt.hashpw(headerResult + "." + bodyResult, BCrypt.gensalt());

        return passwordHashed;
    }
/*
    @Override
    public void getYoutubeApi(String nextToken){
        String key = "AIzaSyD7hNuBTimwcD8k7D1ZlF9Jzgs_4kudAEc";
        String channelId = "";
        String playlistId = "";
        String apiUrl = "https://www.googleapis.com/youtube/v3/playlistItems?key=" + key
                + "&part=snippet&fields=nextPageToken,pageInfo,items(id,snippet(publishedAt,title,description,thumbnails(high(url)),resourceId(videoId)))&order=date&maxResults=50";

        if(nextToken != null){
            apiUrl += "&pageToken=" + nextToken;
        }
        GetJson getJson = new GetJson();
        JSONObject json = getJson.readJsonFromUrl(apiUrl);

        if( json.has("nextPageToken")){
            nextToken = json.getString("nextPageToken");
        }else {
            nextToken = null;
        }

        //페이지 관련 정보
        //JSONObject page = json.getJSONObject("pageInfo");
        JSONArray jArray = json.getJSONArray("items");

//	 System.out.println("next 토큰은 "+ nextToken +" 배열에는 " + jArray.length()+ "개의 정보 들어옴");
        ArrayList<crawlingDTO> Ylist = new ArrayList<>();
        for(int i =0 ; i< jArray.length(); i++) {

            JSONObject item =   jArray.getJSONObject(i);

            //snippet
            JSONObject snippet = item.getJSONObject("snippet");

            String publishedAt = snippet.getString("publishedAt");
            String description = snippet.getString("description");
            if(description.equals("This video is unavailable.")) {continue;}
            String title = snippet.getString("title");

            JSONObject thumbnails = snippet.getJSONObject("thumbnails");
            JSONObject high = thumbnails.getJSONObject("high");


            //비디오 id
            JSONObject resourceId = snippet.getJSONObject("resourceId");
            String videoId = resourceId.getString("videoId");

            String thumbUrl = "https://i.ytimg.com/vi/"+videoId+"/mqdefault.jpg";

            //유튜브 동영상 정보  출력   여기값들 저장 필요;;;;
            //		System.out.println(cnt);
            //		System.out.println(title);
            //		System.out.println(publishedAt);
            //		System.out.println(description);
            //		System.out.println(thumbUrl);
            //		System.out.println(videoId);

            String date = publishedAt.substring(0,publishedAt.indexOf("T"));
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy.MM.dd");
            Date dates;

            try {
                dates = format.parse(date);
                date = format2.format(dates);
            } catch (ParseException e) {
                System.out.println("date 파싱 에러");
            }
            title = Normalizer.normalize(title, Normalizer.Form.NFC);
            //이게 내용
            description = Normalizer.normalize(description, Normalizer.Form.NFC);

        }

        //토큰값이 있으면 계속해서 호출
        if(nextToken != null) {
            getYoutube(nextToken);
        }else {
            System.out.println("종료!!");
        }
    }*/
}
