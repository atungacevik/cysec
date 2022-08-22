package com.hendisantika.postgres.controller;


import com.hendisantika.postgres.Response.Api5Response;
import com.hendisantika.postgres.entity.Api5User;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import com.hendisantika.postgres.repository.Api5Repository;
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
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("api5")
public class Api5BFLAController {

    @Autowired
    Api1Repository repository;
    @Autowired
    Api5Repository repository5;

    private static final Logger logger = (Logger) LogManager.getLogger(Api5BFLAController.class);


    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody Api5User user) {
        logger.debug("User create started");

        user.setId(Long.valueOf((int)(Math.random() * 99999) + 1));
        String authToken = user.getName() + ":" + user.getPass();
        String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        user.setAuthToken(encodedString);
        repository.create4Api5(user);
        //this.create(user);
        Api5Response us =  repository5.customFindMethod4Api5(user.getId());
        logger.debug("User create ended succesfully");

        return ResponseEntity.status(HttpStatus.OK)
                .body(us);
    }



    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUser(ServletRequest servletRequest,@Valid @RequestBody Long userId) {
        logger.debug("get user details started");

        Api5Response us =  repository5.customFindMethod4Api5(userId);
        String authToken = us.getName() + ":" + us.getPass();
        String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String auth = req.getHeader("Authorization");

        if(encodedString.equals(auth)){
            us.setPass("");
            logger.debug("get user details successfull");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }else {
            logger.debug("get user details failed");

            return ResponseEntity.status(HttpStatus.OK)
                    .body("{ \"status\" : \"Failed\" , \"message\" : \" wrong username or password\"  }");

        }


    }

    @GetMapping(value = "/user/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAllUsers() {
        /*Only for admin*/
        logger.debug("get all users details successfull");

        List<Api5Response> us =  repository5.customFindAllMethod4Api5();

        logger.debug("get user ended successfull");

        return ResponseEntity.status(HttpStatus.OK)
                .body(us);
    }


    @GetMapping(value = "/user/admin", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> admin(ServletRequest servletRequest) {
        /*Only for admin*/
        logger.debug("get admin started");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String auth = req.getHeader("Authorization");

        Api5Response us =  repository5.checkUser(auth);
        if(us.getUser_role().equals("admin")){
            logger.debug("get admin successful");

            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }else {
            logger.debug("get admin failed. forbiddem");

            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("[\"message\" : \" You have to be admin for to reach this url\" }");
        }

    }





}
