package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.Response.AuthTokenReponse;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("api8")
public class Api8InjectionController {
    @Autowired
    Api1Repository repository;

    private static final Logger logger = (Logger) LogManager.getLogger(Api8InjectionController.class);

    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody User user) {
        //String authToken = user.getName() + ":" + user.getPassword();
        //String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        logger.debug("Login started");

        List users = repository.checkIsValidUserApi8(user);
        //User loginUser = users.get(0);


        //this.create(user);
        if(users != null){
            logger.debug("Login ok");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(users);
        }else {
            logger.debug("Login failed");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("{ \"status\" : \"Unauthorized\" }");
        }

    }



}
