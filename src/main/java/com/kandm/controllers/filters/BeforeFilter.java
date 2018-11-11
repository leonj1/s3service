package com.kandm.controllers.filters;

import com.kandm.services.JwtBearerAuthorizationPattern;
import io.jsonwebtoken.Jwts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static spark.Spark.before;
import static spark.Spark.halt;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2016
 **/
public class BeforeFilter implements SparkFilter {
    private static final String AUTHORIZATION = "Authorization";

    private Pattern jwtBearerAuthPattern = Pattern.compile(JWT_BEARER_AUTHORIZATION);
    private static final String JWT_BEARER_AUTHORIZATION = new JwtBearerAuthorizationPattern().pattern();
    private String superSecretToken;

    public BeforeFilter(String superSecretToken) {
        this.superSecretToken = superSecretToken;
    }

    public void init() {
        // Filter before each request
        before((req, res) -> {
//            Matcher isPrivatePath = privatePattern.matcher(req.pathInfo());
            boolean hasJwtToken = false;
            if (req.headers(AUTHORIZATION) != null) {
                Matcher jwtToken = jwtBearerAuthPattern.matcher(req.headers(AUTHORIZATION));
//                hasJwtToken = jwtToken.find();

                try {
                    // now verify that the token has not expired
                    String token = jwtToken.group(1);
                    String unpacked = Jwts.parser()
                            .setSigningKey(this.superSecretToken.toString().getBytes())
                            .parseClaimsJws(token)
                            .getBody()
                            .getSubject();
                } catch (Exception e) {
                    halt(401, "Problem while verifying Authorization token");
                }
            }
        });
    }
}
