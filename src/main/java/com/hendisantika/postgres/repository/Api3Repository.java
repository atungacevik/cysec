package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.Response.CommentReponse;
import com.hendisantika.postgres.entity.Api3User;
import com.hendisantika.postgres.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hendisantika.postgres.entity.Api3User;
import java.util.List;
import java.util.Optional;
@Repository
public interface Api3Repository extends JpaRepository<Api3User, Long> {

    @Query(value = "select new com.hendisantika.postgres.Response.CommentReponse( comment.id,user.username,user.latitude,user.longitude,comment.comment)" +
            " from api_3_user user join api_3_comment comment on user.username = comment.username")
    List<CommentReponse> customFindMethod4Api3();

    @Query(value = "select u from api_3_user u where u.username = :username")
    Api3User customFindMethod4Api3Username(@Param("username") String username);




}
