package com.kandm.config;

import com.josemleon.AppProperty;
import com.josemleon.exceptions.PropertiesFileNotFoundException;

import java.io.IOException;

/**
 * Created for K and M Consulting LLC.
 * Created by Jose M Leon 2018
 **/
public class AppProperties {
    private AppProperty getProperty;

    public AppProperties(AppProperty appProperty) {
        this.getProperty = appProperty;
    }

    public int httpPort() throws PropertiesFileNotFoundException, IOException {
        return Integer.parseInt(this.getProperty.value("http.port"));
    }

    public String getSesKey() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("aws_ses_access_key");
    }

    public String getSesSecret() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("aws_ses_secret_key");
    }

    public String secretToken() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("super.secret.token");
    }

    public String profile() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("app.profile");
    }

    public String awsS3Endpoint() throws PropertiesFileNotFoundException, IOException {
        return this.getProperty.value("s3.endpoint");
    }
}
