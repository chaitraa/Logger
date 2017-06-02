package com.Logger.services;

import com.Logger.domain.model.UserInfo;
import com.Logger.domain.repository.UserRepository;
import com.Logger.utils.ParserUtility;
import com.mongodb.MongoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServices {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParserUtility parserUtility;

    public String saveUser(UserInfo userInfo) throws MongoException{
        UserInfo userResult;
        try {
           //UserInfo userInfo=parserUtility.createUserInfoNode(userNode);
          userResult=userRepository.insert(userInfo);
        }
        catch(Exception mongoProblem)
        {
            throw new MongoException(mongoProblem.getMessage(),mongoProblem);
        }
        return userResult==null?"Cannot Save Empty Arguments":"Saved succesfully";
    }
    }
