package com.pressTheButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pressTheButton.Auth.StormpathApp;
import com.pressTheButton.Game.GameSession;
import com.stormpath.sdk.account.Account;
import groovy.util.logging.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Tyler on 2016-08-20.
 */
@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {

    @Autowired
    private StormpathApp stormpathApp;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> startGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        if(getGameStatus(req).getBody().equals(GameSession.gameStatus.ENDED)) {
            GameSession newGame = new GameSession();
            account.getCustomData().put("GameSession", newGame);
            account.getCustomData().put("GameStatus", GameSession.gameStatus.INPROGRESS);
            account.save();
            return ResponseEntity.ok(newGame);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> getGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        if(getGameStatus(req).getBody().equals(GameSession.gameStatus.ENDED)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {

            ObjectMapper mapper = new ObjectMapper();
            GameSession gameSession = mapper.convertValue(account.getCustomData().get("GameSession"), GameSession.class);
            if (gameSession != null){
                return ResponseEntity.ok(gameSession);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @RequestMapping(value = "/gameStatus", method = RequestMethod.GET)
    private ResponseEntity<GameSession.gameStatus> getGameStatus(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        log.info("Retrieving user {} game status {}", account.getUsername(), account.getCustomData().get("GameStatus"));
        GameSession.gameStatus status = Enum.valueOf(GameSession.gameStatus.class, (String) account.getCustomData().get("GameStatus"));
        if (status == null) {
            status = GameSession.gameStatus.ENDED;
            account.getCustomData().put("GameStatus", status);
            account.save();
        }
        log.info("Retrieved user {} game status {}", account.getUsername(), status);
        return ResponseEntity.ok(status);
    }

    @RequestMapping(value = "/updateGameStatus", method = RequestMethod.PATCH)
    private ResponseEntity<Void> updateGameStatus(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    private ResponseEntity<Void> endGame(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/getLeaderBoard", method = RequestMethod.GET)
    private ResponseEntity<Void> getLeaderBoard(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }
}
