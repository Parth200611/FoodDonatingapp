package com.mountreachsolution.sharebite.ACCEPTER.POJO;

public class POJORequest {
    String id, username, name, address, image, RegisterNo, detail;

    public POJORequest(String id, String username, String name, String address, String image, String registerNo, String detail) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.address = address;
        this.image = image;
        RegisterNo = registerNo;
        this.detail = detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRegisterNo() {
        return RegisterNo;
    }

    public void setRegisterNo(String registerNo) {
        RegisterNo = registerNo;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}