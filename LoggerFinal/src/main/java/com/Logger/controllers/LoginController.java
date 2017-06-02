package com.Logger.controllers;

import com.Logger.domain.model.UserInfo;
import com.Logger.services.LoginServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/")
@RestController
public class LoginController {

    @Autowired
    LoginServices loginServices;
   // @RequestMapping(path = "/api/logger/crs/login", method = RequestMethod.POST)
   /* public static UserInfo getUserDetails(@RequestBody UserInfo user) {

       return UserInfo.stream()
                *//*.filter(person -> UserInfo.equalsIgnoreCase(person.getName()))
                .findAny().orElse(null);*//*
    }*/
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST,consumes= MediaType.APPLICATION_JSON_VALUE)
    public String saveUser(@RequestBody UserInfo user) {
        return loginServices.saveUser(user);
    }
    }

