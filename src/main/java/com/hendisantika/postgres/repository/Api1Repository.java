package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.Response.ApiResponse;
//import org.springframework.http.HttpStatus;
import com.hendisantika.postgres.entity.Answer;
import com.hendisantika.postgres.entity.User;
import org.apache.http.HttpStatus;
import org.jdbi.v3.core.Jdbi;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface Api1Repository extends JpaRepository<User, Long>, CustomUserRepository {
    @Query(value = "select user from users user where user.username = :username ")
    User customFindMethodWithUserName(@Param("username") String username);

    @Query(value = "select user from users user where user.authToken = :authtoken ")
    User customFindMethodWithAuthToken(@Param("authtoken") String authtoken);

}


