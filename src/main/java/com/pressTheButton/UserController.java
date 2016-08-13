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

    @RequestMapping("/message")
    public ResponseEntity<String> message(@RequestParam(value="message", defaultValue="test") String message){
        return ResponseEntity.ok(message);
    }

    @RequestMapping("/test")
    public ResponseEntity<Void> test(){
        return ResponseEntity.ok(null);
    }

    @RequestMapping("/")
    public ResponseEntity<Void> home(){
        return ResponseEntity.ok(null);
    }
}
