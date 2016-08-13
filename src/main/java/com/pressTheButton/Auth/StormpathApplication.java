package com.pressTheButton.Auth;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.tenant.Tenant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Tyler on 2016-08-12.
 */

@Configuration
public class StormpathApplication {

    @Autowired
    private StormpathClient stormpathClient;

    private final Tenant tenant;

    private final Application application;

    @Autowired
    public StormpathApplication() {
        tenant = stormpathClient.getClient().getCurrentTenant();
        ApplicationList applications = tenant.getApplications(
                Applications.where(Applications.name().eqIgnoreCase("push-the-button"))
        );
        application = applications.iterator().next();
    }

    @Bean
    public StormpathApplication stormpathApplication(){
        return new StormpathApplication();
    }
}
