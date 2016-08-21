package com.pressTheButton;

import com.pressTheButton.Auth.StormpathApp;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.servlet.account.AccountResolver;
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

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private StormpathApp stormpathApp;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/")
    public ResponseEntity<Void> home(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseEntity<Account> user(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        if (account == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(account);
    }
}