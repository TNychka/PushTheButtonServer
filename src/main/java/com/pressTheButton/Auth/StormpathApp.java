package com.pressTheButton.Auth;

import com.pressTheButton.UserController;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.servlet.account.AccountResolver;
import com.stormpath.sdk.tenant.Tenant;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tyler on 2016-08-14.
 */
@Slf4j
public class StormpathApp {
    private Client client;

    private Tenant tenant;

    private Application application;

    private String appName = "push-the-button";

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

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

    public Account getAccount(HttpServletRequest req){
        Account account = AccountResolver.INSTANCE.getAccount(req);
        logger.debug("Account requested {}, req {}", account, req);
        return account;
    }
}
