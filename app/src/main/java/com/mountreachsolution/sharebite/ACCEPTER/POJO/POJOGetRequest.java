package com.mountreachsolution.sharebite.ACCEPTER.POJO;

public class POJOGetRequest {
    String id,Dusername,Ausername;

    public POJOGetRequest(String id, String dusername, String ausername) {
        this.id = id;
        Dusername = dusername;
        Ausername = ausername;
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

    public String getAusername() {
        return Ausername;
    }

    public void setAusername(String ausername) {
        Ausername = ausername;
    }
}
