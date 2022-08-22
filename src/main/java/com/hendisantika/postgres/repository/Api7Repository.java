package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.Api3User;
import com.hendisantika.postgres.entity.Api7User;
import com.hendisantika.postgres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Api7Repository extends JpaRepository<Api7User, Long> {

    @Query(value = "select user.sessionId from api_7_user user where user.username = :username ")
    String customFindMethodWithUsername(@Param("username") String username);

    @Query(value = "select user.sessionId from api_7_user user where user.sessionId = :sessionId ")
    String customFindMethodWithSessionId(@Param("sessionId") String sessionId);
}
