package com.mountreachsolution.sharebite.ACCEPTER.POJO;

public class POJOPOSt {
    String id,username,address,detail,quantity,image;

    public POJOPOSt(String id, String username, String address, String detail, String quantity, String image) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.detail = detail;
        this.quantity = quantity;
        this.image = image;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
