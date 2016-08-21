package com.pressTheButton.Game;

import com.pressTheButton.utils.GameUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by Tyler on 2016-08-12.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameSession {

    private DateTime lastUpdated;
    private Button button;
    private final float happinessDecay = 2f; //change in happiness per second
    private GameUtils.GameStatus gameStatus = GameUtils.GameStatus.ENDED;

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

    public GameUtils.GameStatus getGameStatus(){
        return gameStatus;
    }

    public void setGameStatus(GameUtils.GameStatus status) {
        this.gameStatus = status;
    }
}
