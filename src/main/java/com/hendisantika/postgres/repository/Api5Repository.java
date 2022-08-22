package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.Response.Api5Response;
import com.hendisantika.postgres.Response.CommentReponse;
import com.hendisantika.postgres.entity.Api3User;
import com.hendisantika.postgres.entity.Api5User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Api5Repository extends JpaRepository<Api5User, Long> {

    @Query(value = "select new com.hendisantika.postgres.Response.Api5Response( user.username,user.name,user.id,user.address,user.mobileNo, user.pass,user.user_role)" +
            " from api_5_user user where user.id = :userId")
    Api5Response customFindMethod4Api5(@Param("userId")Long userId);


    @Query(value = "select new com.hendisantika.postgres.Response.Api5Response( user.username,user.name,user.id,user.address,user.mobileNo, user.pass,user.user_role)" +
            " from api_5_user user ")
    List<Api5Response> customFindAllMethod4Api5();


    @Query(value = "select new com.hendisantika.postgres.Response.Api5Response( user.username,user.name,user.id,user.address,user.mobileNo, user.pass,user.user_role)" +
            " from api_5_user user  where user.authToken = :auth_token")
    Api5Response checkUser(@Param("auth_token")String auth_token);

}
