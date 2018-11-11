package com.kandm.services;

/**
 * Created by jose on 9/2/16.
 */
public class JwtBearerAuthorizationPattern {
    private static final String JWT_TOKEN = ".+\\..+\\..+";
    private static final String JWT_BEARER_AUTHORIZATION = String.format("^Bearer (%s)$", JWT_TOKEN);

    public JwtBearerAuthorizationPattern() {}

    public String pattern() {
        return this.JWT_BEARER_AUTHORIZATION;
    }

    public String jwtTokenPattern() {
        return this.JWT_TOKEN;
    }
}
