package com.hendisantika.postgres.Response;

public class Api5Response {

    String username;

    String name;

    Long id;

    String address;

    String mobileno;

    String pass;

    String user_role;

    public Api5Response(String username, String name, Long id, String address, String mobileno, String pass, String user_role) {
        this.username = username;
        this.name = name;
        this.id = id;
        this.address = address;
        this.mobileno = mobileno;
        this.pass = pass;
        this.user_role = user_role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }
}
