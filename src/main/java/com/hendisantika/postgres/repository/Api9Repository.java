package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.entity.Api9User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Api9Repository  extends JpaRepository<Api9User, Long> {

    @Query(value = "select new api_9_user(user.id,user.username,user.pin,user.tryCount)from api_9_user user where user.id = :id ")
    Api9User customFindMethodWithId(@Param("id") Long id);

    @Query(value = "select new api_9_user(user.id,user.username,user.pin,user.tryCount)from api_9_user user where user.username = :username ")
    Api9User customFindMethodWithUsername(@Param("username") String username);

}
