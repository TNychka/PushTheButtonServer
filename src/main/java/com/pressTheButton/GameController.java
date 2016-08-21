package com.pressTheButton;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pressTheButton.Auth.StormpathApp;
import com.pressTheButton.Game.GameSession;
import com.pressTheButton.utils.GameUtils;
import com.stormpath.sdk.account.Account;
import groovy.util.logging.Slf4j;
import org.joda.time.DateTime;
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
 * Created by Tyler on 2016-08-20.
 */
@RestController
@Slf4j
@RequestMapping("/game")
public class GameController {

    @Autowired
    private StormpathApp stormpathApp;

    @Autowired
    private GameUtils gameUtils;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> startGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        GameUtils.GameStatus gameStatus = getGameStatus(req).getBody();
        if(gameStatus.equals(GameUtils.GameStatus.ENDED) || gameStatus == null) {
            GameSession newGame = new GameSession();
            account.getCustomData().put("GameSession", newGame);
            account.getCustomData().put("GameStatus", GameUtils.GameStatus.INPROGRESS);
            account.save();
            return ResponseEntity.ok(newGame);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/GameStatus", method = RequestMethod.GET)
    private ResponseEntity<GameUtils.GameStatus> getGameStatus(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        Object customData = account.getCustomData().get("GameStatus");
        if (customData == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String gameStatus = (String) customData;
        log.info("Retrieving user {} game status {}", account.getUsername(), gameStatus);
        GameUtils.GameStatus status = Enum.valueOf(GameUtils.GameStatus.class, gameStatus);
        log.info("Retrieved user {} game status {}", account.getUsername(), gameStatus);
        return ResponseEntity.ok(status);
    }


    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> getGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        if(getGameStatus(req).getBody().equals(GameUtils.GameStatus.ENDED)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ObjectMapper mapper = new ObjectMapper();
            GameSession gameSession = mapper.convertValue(account.getCustomData().get("GameSession"), GameSession.class);
            if (gameSession != null){
                return ResponseEntity.ok(gameSession);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
    }



    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    private ResponseEntity<GameSession> endGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        account.getCustomData().put("GameSession", null);
        account.getCustomData().put("GameStatus", GameUtils.GameStatus.ENDED);
        account.save();
        return ResponseEntity.ok(null);
    }

    @RequestMapping(value = "/updateGameStatus", method = RequestMethod.PATCH)
    private ResponseEntity<GameSession> updateGameStatus(@RequestHeader(value = "boost", required = false) Float happinessBoost,
                                                         @RequestHeader(value = "currentHappiness", required = false) Float currentHappiness,
                                                         HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        GameSession currentGame = getGame(req).getBody();
        currentGame = gameUtils.updateGame(currentGame, DateTime.now(), happinessBoost, currentHappiness);
        if (currentGame.getButton().getHappy() <= 0) {
            return endGame(req);
        }
        account.getCustomData().put("GameSession", currentGame);
        account.getCustomData().put("GameStatus", GameUtils.GameStatus.INPROGRESS);
        account.save();
        return ResponseEntity.ok(currentGame);
    }

    @RequestMapping(value = "/getLeaderBoard", method = RequestMethod.GET)
    private ResponseEntity<Void> getLeaderBoard(HttpServletRequest req) {
        return ResponseEntity.ok(null);
    }


}
