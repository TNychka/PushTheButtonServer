package com.pressTheButton.Game;

import com.pressTheButton.utils.GameUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by Tyler on 2016-08-12.
 */
@Data
@Builder
@NoArgsConstructor
public class GameSession {

    private DateTime lastUpdated;
    private Button button;
    private GameUtils.GameStatus gameStatus;

    private final float happinessDecay = 2f; //change in happiness per second
    private final float maxHappiness = 100f;


    public GameSession(DateTime lastUpdated,
                       Button button,
                       GameUtils.GameStatus gameStatus){

        if (button == null) {
            this.lastUpdated = DateTime.now();
        } else {
            this.lastUpdated = lastUpdated;
        }

        if (button == null) {
            this.button = new Button();
        } else {
            this.button = button;
        }

        if (gameStatus == null) {
            this.gameStatus = GameUtils.GameStatus.INPROGRESS;
        } else {
            this.gameStatus = gameStatus;
        }
    }

    public DateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Button getButton() {
        return button;
    }

    public Float getHappinessDecay() {
        return happinessDecay;
    }

    public Float getMaxHappiness() {
        return maxHappiness;
    }

    public GameUtils.GameStatus getGameStatus(){
        return gameStatus;
    }

    public void setGameStatus(GameUtils.GameStatus status) {
        this.gameStatus = status;
    }
}
