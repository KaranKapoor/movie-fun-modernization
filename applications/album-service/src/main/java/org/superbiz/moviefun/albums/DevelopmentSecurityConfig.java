package org.superbiz.moviefun.albums;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnProperty(name = "${application.oauth-enabled]", havingValue = "false")
public class DevelopmentSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public RestOperations restTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext oAuth2ClientContext) {
        return new RestTemplate();
    }
}
