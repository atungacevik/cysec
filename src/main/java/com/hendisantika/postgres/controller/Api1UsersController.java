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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api1")
public class Api1UsersController  {

    @Autowired
    Api1Repository repository;

    private static final Logger logger = (Logger) LogManager.getLogger(Api1UsersController.class);

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        logger.debug("User create started");

        user.setId(Long.valueOf((int)(Math.random() * 99999) + 1));
        String authToken = user.getName() + ":" + user.getPass();
        String encodedString = Base64.getEncoder().encodeToString(authToken.getBytes());
        user.setAuthToken(encodedString);
        repository.create(user);
        //this.create(user);
        User us =  repository.customFindMethod(user.getId());
        logger.debug("User create ended");

        return ResponseEntity.status(HttpStatus.OK)
                .body(us);
    }

    @GetMapping(value = "/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getUser(ServletRequest servletRequest, @PathVariable Long userId) {
        logger.debug("get user started");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String auth = req.getHeader("Authorization");
        List<String> tokens = repository.getAllTokens();
        User us = null;
        for (String token : tokens){
            if(auth.equals(token)){
                us =  repository.customFindMethod(userId);

            }

        }

        logger.debug("get user ended");

        return ResponseEntity.status(HttpStatus.OK)
                .body(us);
    }


    @Transactional
    public User create(User u){

        User created = repository.saveAndFlush(u);
        return created;
    }

   /* @GetMapping("/user/{id}")
    public List<Answer> getUser(@PathVariable Long userId) {
        return answerRepository.findByQuestionId(userId);
    }

    @PutMapping("/users/{id}")
    public Answer updateAnswer(@PathVariable Long questionId,
                               @PathVariable Long answerId,
                               @Valid @RequestBody Answer answerRequest) {
        if (!questionRepository.existsById(questionId)) {
            throw new ResourceNotFoundException("Question not found with id " + questionId);
        }

        return answerRepository.findById(answerId)
                .map(answer -> {
                    answer.setText(answerRequest.getText());
                    return answerRepository.save(answer);
                }).orElseThrow(() -> new ResourceNotFoundException("Answer not found with id " + answerId));
    }*/



}