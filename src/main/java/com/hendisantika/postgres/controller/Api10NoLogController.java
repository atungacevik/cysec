package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.entity.Api6User;
import com.hendisantika.postgres.repository.Api1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;

@RestController
@RequestMapping("api10")
public class Api10NoLogController {

    @Autowired
    Api1Repository repository;

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUser(ServletRequest servletRequest, @PathVariable Long userId) {
        Api6User us = repository.customFindMethod4Api6(userId);

        if (us != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{ \"status\" : \"failed\" , \"message\":\"Failed.No user found.\" }");

    }
}
