package com.eca.aishu.trafichack;

public class Posting {
    private String location;
    private String description;
    private String status;
    private String time;

    public Posting(){

    }

    public Posting(String status,String location,String description,String time) {
        this.status = status;
        this.location=location;
        this.time=time;
        this.description=description;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getDescription() {
        return description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    public void setTime(String time){
        this.time=time;
    }

    public String getTime() {
        return time;
    }

}
