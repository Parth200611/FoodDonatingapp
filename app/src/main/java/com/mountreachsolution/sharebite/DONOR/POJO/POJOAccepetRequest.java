package com.mountreachsolution.sharebite.DONOR.POJO;

public class POJOAccepetRequest {
    String id,Dusername,AUsername,status;

    public POJOAccepetRequest(String id, String dusername, String AUsername, String status) {
        this.id = id;
        Dusername = dusername;
        this.AUsername = AUsername;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDusername() {
        return Dusername;
    }

    public void setDusername(String dusername) {
        Dusername = dusername;
    }

    public String getAUsername() {
        return AUsername;
    }

    public void setAUsername(String AUsername) {
        this.AUsername = AUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
