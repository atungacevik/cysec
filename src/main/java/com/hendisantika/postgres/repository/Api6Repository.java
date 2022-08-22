package com.hendisantika.postgres.repository;

import com.hendisantika.postgres.Response.CommentReponse;
import com.hendisantika.postgres.entity.Api5User;
import com.hendisantika.postgres.entity.Api6User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface Api6Repository extends JpaRepository<Api6User, Long> {

}
