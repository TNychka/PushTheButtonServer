package com.pressTheButton.Game;

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
    static public enum gameStatus{
        INPROGRESS, ENDED
    }

    private DateTime lastUpdated;
    private Button button;
    private final float happinessDecay = 0.5f; //change in happiness per second
}
