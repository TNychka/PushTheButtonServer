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

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ResponseEntity<String> user(HttpServletRequest req) {
        Account account = getAccount(req);
        if (account == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(account.getFullName());
    }

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    public ResponseEntity<Void> startGame(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    public ResponseEntity<Void> getGame(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/updateGameStatus", method = RequestMethod.PATCH)
    public ResponseEntity<Void> updateGameStatus(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    public ResponseEntity<Void> endGame(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/getLeaderBoard", method = RequestMethod.GET)
    public ResponseEntity<Void> getLeaderBoard(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping("/")
    public ResponseEntity<String> home(HttpServletRequest req){
        if (getAccount(req) == null){
            return ResponseEntity.ok("Hey, just a reminder to login!");
        }
        return ResponseEntity.ok("Thanks for logging in! :)");
    }

    private Account getAccount(HttpServletRequest req){
        Account account = AccountResolver.INSTANCE.getAccount(req);
        logger.debug("Account requested {}, req {}", account, req);
        return account;
    }
}
