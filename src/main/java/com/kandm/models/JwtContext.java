package com.kandm.models;

/**
 * Created by jose on 8/31/16.
 */
public class JwtContext {
    private String token;

    public JwtContext(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
