package com.hendisantika.postgres.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name="users")
public class User {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "username",unique = true)
    private String username;

    @Column(name = "name")
    private String name;


    @Column(name = "course")
    private String course;
    @Column(name = "pass")
    private String pass;

    @Column(name = "authToken")
    private String authToken;

    public User(Long id, String username, String name, String course, String pass, String authToken) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.course = course;
        this.pass = pass;
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
