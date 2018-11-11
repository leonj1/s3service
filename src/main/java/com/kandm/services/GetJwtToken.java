package com.kandm.services;

import com.google.gson.Gson;
import com.kandm.models.JwtContext;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class GetJwtToken {
    private String token;
    private Gson gson;

    public GetJwtToken(String token) {
        this.token = token;
        this.gson = new Gson();
    }

    public String token() {
        JwtContext context = this.gson.fromJson(this.token, JwtContext.class);
        return context.getToken();
    }
}
