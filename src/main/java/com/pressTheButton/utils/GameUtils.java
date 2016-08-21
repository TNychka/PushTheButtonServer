package com.pressTheButton.utils;

import com.pressTheButton.Game.Button;
import com.pressTheButton.Game.GameSession;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.Interval;


/**
 * Created by Tyler on 2016-08-20.
 */

@NoArgsConstructor
public class GameUtils {
    public enum GameStatus {
        INPROGRESS, ENDED
    }

    public GameSession updateGame(GameSession currentGame, DateTime updateTime, Float happinessBoost, Float currentHappiness) {

        Interval timeInterval = new Interval(currentGame.getLastUpdated(), updateTime);
        Integer timeDelta = timeInterval.toDuration().toStandardSeconds().getSeconds();
        Button button = currentGame.getButton();
        if (currentHappiness == null) {
           button.setHappy(currentGame.getButton().getHappy()
                    - timeDelta * currentGame.getHappinessDecay()
                    + happinessBoost);
        } else {
            button.setHappy(currentHappiness);
        }
        currentGame.setLastUpdated(DateTime.now());
        return currentGame;
    }
}
