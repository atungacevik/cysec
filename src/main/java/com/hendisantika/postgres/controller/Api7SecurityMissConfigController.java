package com.hendisantika.postgres.controller;


import com.hendisantika.postgres.Response.AuthTokenReponse;
import com.hendisantika.postgres.Security.BasicHttpReq;
import com.hendisantika.postgres.entity.Api6User;
import com.hendisantika.postgres.entity.Api7User;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import com.hendisantika.postgres.repository.Api7Repository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.boot.jaxb.Origin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api7")
public class Api7SecurityMissConfigController {

    @Autowired
    Api1Repository repository;

    @Autowired
    Api7Repository repository7;

    private static final Logger logger = (Logger) LogManager.getLogger(Api7SecurityMissConfigController.class);


    @PostMapping(value = "/user/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody Api7User user) {
        user.setId(Long.valueOf((int) (Math.random() * 99999) + 1));
        logger.debug("User create started");

        int result = repository.create4Api7(user);
        if (result == 1) {
            logger.debug("User create ok");

            Api7User us = repository.customFindMethod4Api7(user.getId());
            us.setAuthToken("");
            //us.setSessionId("");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }
        logger.debug("User create failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{ \"status\" : \"failed\" , \"message\":\"Creation Failed\" }");

    }


    @PostMapping(value = "/user/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> login(ServletRequest servletRequest, @Valid @RequestBody Api7User user) {
        logger.debug("login started");

        String username = repository.checkIsValidUserApi7(user);
        String sessionId = "";
        if(username.equals(user.getUsername())){

            int result = repository.createSession(username);
            if (result == 1){
                sessionId = repository7.customFindMethodWithUsername(username);
            }
        }

        //this.create(user);
        if (sessionId != null && !sessionId.isEmpty()) {
            logger.debug("login ok");

            return ResponseEntity.status(HttpStatus.OK)
                    .body("{ \"status\" : \"OK\" , \"sessionId\": \"" + sessionId + "\"}");

        } else {
            logger.debug("login failed");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"failed\" , \"message\":\"Login Failed\" }");
        }
    }

    @CrossOrigin("*")
    @GetMapping(value = "/user/get/authKey", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getAuthKey(ServletRequest servletRequest) throws IOException {
        logger.debug("get auth key started");

        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        Cookie name = WebUtils.getCookie(httpReq, "SESSIONID");
        String a =  httpReq.getHeader("Origin");
        String b="";
        String authToken ="";
        if(a != null && !a.isEmpty()){
            b = this.sendGET(a);
            logger.debug("get auth key ok");

            return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin",a)
                    .body("{ \"status\" : \"OK\" , \"sessionId\": \"" + b + "\"}");

        }else {
             authToken = repository7.customFindMethodWithSessionId(name.getValue());

        }



        //this.create(user);
        if (authToken != null) {
            logger.debug("get auth key ok");

            return ResponseEntity.status(HttpStatus.OK).header("Access-Control-Allow-Origin",a)
                    .body("{ \"status\" : \"OK\" , \"sessionId\": \"" + authToken + "\"}");

        } else {
            logger.debug("get auth key failed");

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"failed\" , \"message\":\"Invalid User\" }");
        }
    }


    @GetMapping(value = "/sendMe", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> cors(ServletRequest servletRequest) {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        Cookie name = WebUtils.getCookie(httpReq, "SESSIONID");

        String authToken = repository7.customFindMethodWithSessionId(name.getValue());


        //this.create(user);
        if (authToken != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("{ \"status\" : \"OK\" , \"sessionId\": \"" + "dolandırdımmmmmmmmm" + " \"}");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"status\" : \"failed\" , \"message\":\"Invalid User\" }");
        }
    }

    private static final String USER_AGENT = "Mozilla/5.0";

    private String GET_URL ;

    private String POST_URL;

    private  String POST_PARAMS = "userName=alper";



    private  String sendGET(String url) throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            return response.toString();
        } else {
            return "GET request not worked";
        }

    }

}



