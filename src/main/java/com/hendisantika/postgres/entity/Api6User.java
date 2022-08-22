package com.hendisantika.postgres.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity(name="api_6_user")
public class Api6User {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "pass")
    private String pass;

    @Column(name = "credit")
    private Long credit;



}
