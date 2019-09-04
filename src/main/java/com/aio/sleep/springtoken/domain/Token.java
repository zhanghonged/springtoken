package com.aio.sleep.springtoken.domain;

public class Token {
    private int id;
    private String token;
    private String salt;
    private int client_id;

    public Token() {
    }

    public Token(String token, String salt, int client_id) {
        this.token = token;
        this.salt = salt;
        this.client_id = client_id;
    }

    @Override
    public String toString() {
        return "Token{" +
                "token='" + token + '\'' +
                ", salt='" + salt + '\'' +
                ", client_id=" + client_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }
}
