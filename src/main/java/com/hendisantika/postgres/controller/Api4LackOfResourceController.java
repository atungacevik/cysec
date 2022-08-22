package com.hendisantika.postgres.controller;


import com.hendisantika.postgres.Response.AuthTokenReponse;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.entity.VerifyOtp;
import com.hendisantika.postgres.repository.Api1Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api4")
public class Api4LackOfResourceController {

    @Autowired
    Api1Repository repository;

    private static final Logger logger = (Logger) LogManager.getLogger(Api4LackOfResourceController.class);



    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(@Valid @RequestBody User user) {
        logger.debug("Login  started");

        //String authToken = user.getName() + ":" + user.getPassword();
        //String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        String users = repository.checkIsValidUser(user);
        //User loginUser = users.get(0);


        //this.create(user);
        AuthTokenReponse token = new AuthTokenReponse();
        if (users != null) {
            Boolean isOk = repository.createOtp(user.getUsername());
            if (isOk) {
                logger.debug("Login successful");

                return ResponseEntity.status(HttpStatus.OK)
                        .body("{ \"status\" : \"Otp Sent To Your Phone\" }");
            } else {
                logger.debug("Login failed");

                return ResponseEntity.status(HttpStatus.OK)
                        .body("{ \"status\" : \"Failed\" }");
            }
        } else {
            token.setToken(null);
            token.setSuccess("Failed");
            logger.debug("Login successful");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(token);
        }

    }



    @PostMapping(value = "/verify/otp", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> verifyOtp(@Valid @RequestBody VerifyOtp otp) {
        //String authToken = user.getName() + ":" + user.getPassword();
        //String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        //String users = repository.checkIsValidUser(user);
        //User loginUser = users.get(0);

        logger.debug("verify otp started");

        //this.create(user);
        if (otp != null) {
            Boolean isOk = repository.verifyOtp(otp);
            if (isOk) {

                User u = repository.customFindMethodWithUserName(otp.getUsername());
                logger.debug("verify otp successful");

                return ResponseEntity.status(HttpStatus.OK)
                        .body("{ \"status\" : \"OK\" }");
            } else {
                logger.debug("verify otp failed");

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("{ \"status\" : \"Failed\" }");
            }
        } else {
            logger.debug("verify otp failed.unauthorized");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

    }



    @GetMapping(value = "/user/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> details(ServletRequest servletRequest) {
        logger.debug("get user details started");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String auth = req.getHeader("Authorization");
        if(auth != null ){
            try {
                String users = repository.checkIsValidUser(auth);
                if(users != null){
                    //List<User> asd =  repository.findAll();
                    logger.debug("get user details successfull");

                    User u = repository.customFindMethodWithAuthToken(auth);


                    return new ResponseEntity<Object>(u,HttpStatus.OK);
                }
            } catch (Exception e) {
                logger.debug("get user details failed. UNAUTHORIZED");

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(null);
            }

            //List<User> us =  repository.customFindAllMethod(auth);

        }else {
            logger.debug("get user details failed. UNAUTHORIZED");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(null);
        }

        logger.debug("get user details failed. UNAUTHORIZED");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{ \"status\" : \"Unauthorized\" }");
    }








}
