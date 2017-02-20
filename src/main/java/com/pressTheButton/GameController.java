package com.pressTheButton;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pressTheButton.Auth.StormpathApp;
import com.pressTheButton.Game.Button;
import com.pressTheButton.Game.GameSession;
import com.pressTheButton.utils.GameUtils;
import com.stormpath.sdk.account.Account;
import com.stormpath.sdk.directory.CustomData;
import groovy.util.logging.Slf4j;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

import static com.stormpath.sdk.servlet.filter.DefaultFilter.account;

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

    @Autowired
    private ObjectMapper objectMapper;

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/startGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> startGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        log.info("Initiating game for user {}", account.getUsername());
        GameUtils.GameStatus gameStatus = getGameStatus(req).getBody();
        if (gameStatus == null || gameStatus.equals(GameUtils.GameStatus.ENDED)) {
            GameSession newGame = new GameSession(DateTime.now(), new Button(), GameUtils.GameStatus.INPROGRESS);
            account.getCustomData().put("GameSession", newGame);
            account.getCustomData().put("GameStatus", GameUtils.GameStatus.INPROGRESS);
            account.save();
            log.info("New game initiated for user {}", account.getUsername());
            return ResponseEntity.ok(newGame);
        } else {
            log.info("Game already in progress for user {}", account.getUsername());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @RequestMapping(value = "/GameStatus", method = RequestMethod.GET)
    private ResponseEntity<GameUtils.GameStatus> getGameStatus(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        log.info("Retrieving user {} game status", account.getUsername());
        GameUtils.GameStatus gameStatus;
        try {
            gameStatus = (GameUtils.GameStatus) account.getCustomData().get("GameStatus");
        } catch (Exception e) {
            log.info("Failed to retrieve user {} game status; exeption {}, data {}", account.getUsername(), e, account.getCustomData().get("GameStatus"));
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        log.info("Retrieved user {} game status {}", account.getUsername(), gameStatus);
        return ResponseEntity.ok(gameStatus);
    }


    @RequestMapping(value = "/getGame", method = RequestMethod.GET)
    private ResponseEntity<GameSession> getGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        log.info("Retrieving user {} game", account.getUsername());
        GameUtils.GameStatus gameStatus = getGameStatus(req).getBody();
        if (gameStatus == null) {
            log.info("Game was ended user {} game", account.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            GameSession gameSession;
            try {
                ObjectMapper mapper = new ObjectMapper();
                gameSession = mapper.convertValue(account.getCustomData().get("GameSession"), GameSession.class);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (gameSession != null && gameSession.getGameStatus() != GameUtils.GameStatus.ENDED) {
                gameSession = gameUtils.updateGame(gameSession, DateTime.now(), 0.0f, null);
                if (gameSession.getButton().getHappy() <= 0 && gameSession.getGameStatus() != GameUtils.GameStatus.ENDED) {
                    endGame(req);
                    return ResponseEntity.ok(gameSession);
                } else {
                    gameSession.setGameStatus(gameStatus);
                    account.getCustomData().put("GameStatus", gameStatus);
                    account.getCustomData().put("GameSession", gameSession);
                    account.save();
                }
            }
            return ResponseEntity.ok(gameSession);
        }
    }

    /**
     * update the user's current game on the server side
     *
     * @Header boost how much happiness to add to the button's score
     * @Header currentHappiness the value to update the button to, overrides boost
     */
    @RequestMapping(value = "/updateGameStatus", method = RequestMethod.PATCH)
    private ResponseEntity<GameSession> updateGameStatus(@RequestHeader(value = "Boost", required = false) Float happinessBoost,
                                                         @RequestHeader(value = "CurrentHappiness", required = false) Float currentHappiness,
                                                         HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        GameSession currentGame = getGame(req).getBody();
        currentGame = gameUtils.updateGame(currentGame, DateTime.now(), happinessBoost, currentHappiness);
        if (currentGame.getButton().getHappy() <= 0) {
            endGame(req);
            return ResponseEntity.ok(currentGame);
        } else {
            account.getCustomData().put("GameStatus", GameUtils.GameStatus.INPROGRESS);
            account.getCustomData().put("GameSession", currentGame);
            account.save();
        }
        return ResponseEntity.ok(currentGame);
    }

    @RequestMapping(value = "/endGame", method = RequestMethod.POST)
    private ResponseEntity<GameSession> endGame(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        account.getCustomData().put("GameStatus", GameUtils.GameStatus.ENDED);
        account.save();
        return getGame(req);
    }

    @RequestMapping(value = "/setNewScore", method = RequestMethod.POST)
    private ResponseEntity<String> getLeaderBoard(HttpServletRequest req, @RequestParam int score) {
        Account account = stormpathApp.getAccount(req);
        JSONObject leaderboard;
        CustomData customData = account.getCustomData();
        try {
            Object data = customData.get("Leaderboard");
            if (data == null) {
                leaderboard = new JSONObject();
            } else {
                leaderboard = new JSONObject(data.toString());
            }
        } catch (Exception e) {
            leaderboard = new JSONObject();
        }
        if (leaderboard.length() > 5) {
            for (int i = 0; i < leaderboard.length(); i++) {
                int leaderboardScore = leaderboard.getInt(String.valueOf(i));
                if (score > leaderboardScore) {
                    leaderboard.put(String.valueOf(i), score);
                }
            }
        } else {
            int i;
            int leaderboardScore;
            for (i = 0; i < leaderboard.length(); i++) {
                leaderboardScore = leaderboard.getInt(String.valueOf(i));
                if (score > leaderboardScore) {
                    leaderboard = leaderboard.put(String.valueOf(i), score);
                    score = leaderboardScore;
                }
            }
            leaderboard = leaderboard.put(String.valueOf(i), score);
        }
        customData.put("Leaderboard", leaderboard.toString());
        account.save();
        return ResponseEntity.ok(leaderboard.toString());
    }

    @RequestMapping(value = "/getLeaderBoard", method = RequestMethod.GET)
    private ResponseEntity<Object> getLeaderBoard(HttpServletRequest req) {
        Account account = stormpathApp.getAccount(req);
        CustomData customData = account.getCustomData();
        return ResponseEntity.ok(customData.get("Leaderboard"));
    }
}
