package com.pressTheButton;

import com.pressTheButton.User.User;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.application.Application;
import com.stormpath.sdk.application.ApplicationList;
import com.stormpath.sdk.application.Applications;
import com.stormpath.sdk.client.Client;
import com.stormpath.sdk.client.ClientBuilder;
import com.stormpath.sdk.client.Clients;
import com.stormpath.sdk.directory.CustomData;
import com.stormpath.sdk.tenant.Tenant;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
public class UserController {

    static Client client = null;
    static Application application = null;

    @RequestMapping("/test")
    public ResponseEntity<String> test(@RequestParam(value="message", defaultValue="test") String message){
        return ResponseEntity.ok(message);
    }

    @RequestMapping("/createUser")
    public ResponseEntity<User> createUser (@RequestHeader("username") String username,
                                              @RequestHeader("password") String password){
        if (application == null || client == null) {
            authorizeApplication();
        }

        Account account = client.instantiate(Account.class);

        account.setGivenName(username);
        account.setSurname(username);
        account.setUsername(username); //optional, defaults to email if unset
        account.setEmail(username);
        account.setPassword(password);

        application.createAccount(account);

        User newUser = new User(username, account);

        return ResponseEntity.ok(newUser);
    }

    private Application authorizeApplication() {
        ClientBuilder builder = Clients.builder();
        Client client = builder.build();

        Tenant tenant = client.getCurrentTenant();
        ApplicationList applications = tenant.getApplications(
                Applications.where(Applications.name().eqIgnoreCase("push-the-button"))
        );

        return applications.iterator().next();
    }
}
