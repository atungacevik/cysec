package com.hendisantika.postgres.controller;


import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hendisantika.postgres.Response.AuthTokenReponse;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api2")
public class Api2LoginController {


    @Autowired
    Api1Repository repository;

    private static final Logger logger = (Logger) LogManager.getLogger(Api2LoginController.class);

    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody User user) {

        logger.debug("Login  started");

        //String authToken = user.getName() + ":" + user.getPassword();
        //String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        String users = repository.checkIsValidUser(user);
        //User loginUser = users.get(0);


        //this.create(user);
        AuthTokenReponse token =  new AuthTokenReponse();
        if(users != null){
            token.setToken(users);
            token.setSuccess("True");
            logger.debug("Login successful");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(token);
        }else {
            logger.debug("Login Failed");

            token.setToken(null);
            token.setToken("Failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(token);
        }

    }

    //TODO just implement for the user.......
    @GetMapping(value = "/user/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> details(ServletRequest servletRequest) {
        logger.debug("Get user details started");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String auth = req.getHeader("Authorization");
        if(auth != null ){
            try {
                String users = repository.checkIsValidUser(auth);
                if(users != null){
                    List<User> asd =  repository.findAll();
                    logger.debug("Get user detail ended successfully");

                    return new ResponseEntity<Object>(asd,HttpStatus.OK);
                }
            } catch (Exception e) {
                logger.debug("Get user detail ended.Unauthorized");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
            }

            //List<User> us =  repository.customFindAllMethod(auth);

        }else {
            logger.debug("Get user detail ended.Unauthorized");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        logger.debug("Get user detail ended.Unauthorized");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{ \"status\" : \"Unauthorized\" }");
    }





}
