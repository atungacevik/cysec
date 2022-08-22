package com.hendisantika.postgres.controller;

import com.hendisantika.postgres.Response.CommentReponse;
import com.hendisantika.postgres.entity.Api3Comment;
import com.hendisantika.postgres.entity.Api3User;
import com.hendisantika.postgres.entity.User;
import com.hendisantika.postgres.repository.Api1Repository;
import com.hendisantika.postgres.repository.Api3Repository;
import com.hendisantika.postgres.repository.Api4Repository;
import com.hendisantika.postgres.repository.CustomUserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("api3")
public class Api3ExcessiveController {

    @Autowired
    Api1Repository repository;

    @Autowired
    Api3Repository repository3;

    private static final Logger logger = (Logger) LogManager.getLogger(Api3ExcessiveController.class);

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addUser(@Valid @RequestBody Api3User user) {
        logger.debug("User create started");

        double random = ThreadLocalRandom.current().nextDouble(-360, 360);
        double random1 = ThreadLocalRandom.current().nextDouble(-360, 360);
        user.setId(Long.valueOf((int) (Math.random() * 99999) + 1));
        user.setLatitude(String.valueOf(random));
        user.setLongitude(String.valueOf(random1));

        int result = repository.create4Api3(user);
        if (result == 1) {
            logger.debug("User created successfully");

            Api3User us = repository.customFindMethod4Api3(user.getId());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(us);
        }
        logger.debug("User creation failed");

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{ \"status\" : \"failed\" , \"message\":\"Creation Failed\" }");

    }

    @PostMapping(value = "/comment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addComment(@Valid @RequestBody Api3Comment comment) {
        logger.debug("Comment create started");

        String alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = 21;
        comment.setId(Long.valueOf((int) (Math.random() * 99999) + 1));
        Api3User user = repository3.customFindMethod4Api3Username(comment.getUsername());
        if (user != null) {
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(alphabet.length());
                char randomChar = alphabet.charAt(index);
                sb.append(randomChar);
            }
            repository.createComment4Api3(comment);
            logger.debug("Comment created successfully");

            return ResponseEntity.status(HttpStatus.OK)
                    .body("{ \"status\" : \"ok\" , \"message\":\"Comment Created\" }");
        }

        logger.debug("Comment create failed");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{ \"status\" : \"failed\" , \"message\":\"User not found\" }");
    }


    @GetMapping(value = "/getComments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getComments() {

        List<CommentReponse> us = repository3.customFindMethod4Api3();
        return ResponseEntity.status(HttpStatus.OK)
                .body(us);
    }


}
