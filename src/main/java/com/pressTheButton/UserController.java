package com.pressTheButton;

import com.pressTheButton.Game.GameSession;
import com.pressTheButton.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
public class UserController {

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public void me(){
    }

    @RequestMapping("/")
    public ResponseEntity<Void> home(){
        return ResponseEntity.ok(null);
    }
}
