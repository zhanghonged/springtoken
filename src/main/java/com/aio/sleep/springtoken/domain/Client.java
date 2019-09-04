package com.aio.sleep.springtoken.domain;

public class Client {
    private int id;



    private String aid;
    private String gaid;
    private String ip;
    private String country;
    private String operators;
    private Boolean is_wifi;
    private int req_num;

    public Client() {
    }

    public Client(int id, String aid, String gaid, String ip, String country, String operators, Boolean is_wifi, int req_num) {
        this.id = id;
        this.aid = aid;
        this.gaid = gaid;
        this.ip = ip;
        this.country = country;
        this.operators = operators;
        this.is_wifi = is_wifi;
        this.req_num = req_num;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", aid='" + aid + '\'' +
                ", gaid='" + gaid + '\'' +
                ", ip='" + ip + '\'' +
                ", country='" + country + '\'' +
                ", operators='" + operators + '\'' +
                ", is_wifi=" + is_wifi +
                ", req_num=" + req_num +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getGaid() {
        return gaid;
    }

    public void setGaid(String gaid) {
        this.gaid = gaid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public Boolean getIs_wifi() {
        return is_wifi;
    }

    public void setIs_wifi(Boolean is_wifi) {
        this.is_wifi = is_wifi;
    }

    public int getReq_num() {
        return req_num;
    }

    public void setReq_num(int req_num) {
        this.req_num = req_num;
    }
}
