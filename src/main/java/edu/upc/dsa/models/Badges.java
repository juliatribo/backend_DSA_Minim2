package edu.upc.dsa.models;

public class Badges {

    private String url;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Badges(){

    }

    public Badges(String url, String name){
        this.url = url;
        this.name = name;
    }
}
