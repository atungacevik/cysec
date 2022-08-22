package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomUserRepository  {
    User customFindMethod(Long id);

    Api3User customFindMethod4Api3(Long id);

    Api6User customFindMethod4Api6(Long id);

    Api7User customFindMethod4Api7(Long id);

    Api3User customFindMethod4Api3Username(String username);

    List<User> customFindAllMethod(String auth_token);

    List<String> getAllTokens();

    String checkIsValidUser(User user);

    String checkIsValidUser(String auth_token);

    Boolean createOtp(String userName);

    Boolean verifyOtp(VerifyOtp otp);


    void create (User s);

    int create4Api3 (Api3User s);

    void createComment4Api3 (Api3Comment s);

    int create4Api5 (Api5User s);

    List checkIsValidUserApi8(User s);

    int create4Api6(Api6User s);

    int create4Api7(Api7User s);

    //String login4Api7(Api7User s);

    String checkIsValidUserApi7(Api7User s);

    int createSession(String username);

    int create4Api9(Api9User s);

    String login4Api9_1(Api9User s);

    String login4Api9_2(Api9User s);

    int updateTryCount4Api9(String s,Long a);

}


