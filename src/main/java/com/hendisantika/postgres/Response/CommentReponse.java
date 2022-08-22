package com.hendisantika.postgres.Response;

import javax.persistence.Column;
import javax.persistence.Id;

public class CommentReponse {

    private Long id;

    private String username;

    private String latitude;

    private String longitude;

    private String comment;


    public CommentReponse(Long id, String username, String latitude, String longitude, String comment) {
        this.id = id;
        this.username = username;
        this.latitude = latitude;
        this.longitude = longitude;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
