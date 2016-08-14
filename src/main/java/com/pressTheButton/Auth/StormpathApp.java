package com.pressTheButton.Auth;

import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.tenant.Tenant;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by Tyler on 2016-08-14.
 */
public class StormpathApp {
    private Client client;

    private Tenant tenant;

    private Application application;

    private String appName = "push-the-buttonS";

    public StormpathApp() {
        ClientBuilder builder = Clients.builder();
        client = builder.build();
        tenant = client.getCurrentTenant();
        ApplicationList applications = tenant.getApplications(
                Applications.where(Applications.name().eqIgnoreCase("push-the-button"))
        );
        application = applications.iterator().next();
    }
    public Client getClient(){
        return client;
    }

    public Application getApplication(){
        return application;
    }
}
