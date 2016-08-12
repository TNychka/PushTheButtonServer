package com.pressTheButton;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Tyler on 2016-08-10.
 */
@RestController
public class UserController {

    @RequestMapping("/test")
    public ResponseEntity<String> test(@RequestParam(value="message", defaultValue="test") String message){
        return ResponseEntity.ok(message);
    }
    @RequestMapping("/")
    public ResponseEntity<Void> home(){
        return ResponseEntity.ok(null);
    }
}
