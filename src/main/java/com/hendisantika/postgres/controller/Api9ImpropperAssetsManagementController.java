package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.entity.Api9User;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import com.hendisantika.postgres.repository.Api9Repository;
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
import java.util.Base64;
import java.util.Random;

import static org.apache.logging.log4j.core.Logger.*;

@RestController
@RequestMapping("api9")
public class Api9ImpropperAssetsManagementController {
    @Autowired
    Api1Repository repository;

    @Autowired
    Api9Repository repository9;

    private static final Logger logger = (Logger) LogManager.getLogger(Api9ImpropperAssetsManagementController.class);


    @PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody Api9User user) {
        logger.debug("User create started");
        user.setId(Long.valueOf((int) (Math.random() * 99999) + 1));
        if (user.getPin() == null || user.getPin() == 0L) {
            int randomNumber = new Random().nextInt(9000) + 1000;
            user.setPin(Long.valueOf(randomNumber));
        }
        user.setTryCount(0L);
        repository.create4Api9(user);
        //this.create(user);
        Api9User us = repository9.customFindMethodWithId(user.getId());
        if (us != null) {
            logger.debug("User created");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        } else {
            logger.debug("User create failed");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"Failed\" }");
        }

    }

    @PostMapping(value = "/user/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginV1(@Valid @RequestBody Api9User user) {
        logger.debug("Login started");

        String x = repository.login4Api9_1(user);
        Api9User us = null;
        if (x != null && !x.isEmpty()) {
            us = repository9.customFindMethodWithUsername(x);

        } else {
            logger.debug("Login successful");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"Failed to login\" }");
        }


        if (us != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);

        } else {
            logger.debug("Login failed");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"Failed to gather user info\" }");
        }


    }

    @PostMapping(value = "/user/v2/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginV2(@Valid @RequestBody Api9User user) {
        logger.debug("Login started");

        Api9User userCheck = repository9.customFindMethodWithUsername(user.getUsername());
        if(userCheck.getTryCount() < 5L){
            user.setTryCount(userCheck.getTryCount());
            String x = repository.login4Api9_2(user);
            Api9User us = null;
            if (x != null && !x.isEmpty()) {


                us = repository9.customFindMethodWithUsername(x);

            } else {
                logger.debug("Login failed");
                int a = repository.updateTryCount4Api9(user.getUsername(),userCheck.getTryCount()+1);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{ \"status\" : \"Failed to login\" }");
            }


            if (us != null) {
                logger.debug("Login successful");
                return ResponseEntity.status(HttpStatus.OK)
                        .body(us);

            } else {
                logger.debug("Login failed  to gather user info");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{ \"status\" : \"Failed to gather user info\" }");
            }
        }else{
            logger.debug("Login failed. limit exceed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"Limit exceed\" }");
        }

        }




}
