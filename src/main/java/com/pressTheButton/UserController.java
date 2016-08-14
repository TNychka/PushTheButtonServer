package com.pressTheButton;

import com.pressTheButton.Auth.StormpathApp;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.account.AccountList;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private StormpathApp stormpathApp;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);;



    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public ResponseEntity<AccountList> me(){
        Map<String, Object> queryParams = new HashMap<String, Object>();
        queryParams.put("username", "tk421");
        AccountList accounts = stormpathApp.getApplication().getAccounts(queryParams);
        for (Account acct : accounts) {
            logger.debug("Found Account: " + acct.getHref() + ", " + acct.getEmail());
        }
        return ResponseEntity.ok(accounts);
    }

    @RequestMapping("/")
    public ResponseEntity<Void> home(){
        return ResponseEntity.ok(null);
    }
}
