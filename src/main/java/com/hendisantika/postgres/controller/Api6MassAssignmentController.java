package com.hendisantika.postgres.controller;


import com.hendisantika.postgres.entity.Api3User;
import com.hendisantika.postgres.entity.Api6User;
import com.hendisantika.postgres.repository.Api1Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("api6")
public class Api6MassAssignmentController {

    @Autowired
    Api1Repository repository;

    private static final Logger logger = (Logger) LogManager.getLogger(Api6MassAssignmentController.class);


    @PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody Api6User user) {
        user.setId(Long.valueOf((int) (Math.random() * 99999) + 1));
        logger.debug("User create started");

        int result = repository.create4Api6(user);
        if (result == 1) {
            logger.debug("User create successful");

            Api6User us = repository.customFindMethod4Api6(user.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }
        logger.debug("User create failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{ \"status\" : \"failed\" , \"message\":\"Creation Failed\" }");

    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUser(ServletRequest servletRequest, @PathVariable Long userId) {
        Api6User us = repository.customFindMethod4Api6(userId);
        logger.debug("get user  started");

        if (us != null) {
            logger.debug("get user successful");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }

        logger.debug("get user failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{ \"status\" : \"failed\" , \"message\":\"Failed.No user found.\" }");

    }


}
