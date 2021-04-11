package com.example.yhwasongtest.service.impl;

import com.example.yhwasongtest.model.BaseQuestion;
import com.example.yhwasongtest.model.UserModel;
import com.example.yhwasongtest.repository.BaseRepository;
import com.example.yhwasongtest.repository.UserRepository;
import com.example.yhwasongtest.service.repository.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
}
