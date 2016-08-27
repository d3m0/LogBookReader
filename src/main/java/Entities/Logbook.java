package Entities;

import java.util.List;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class Logbook {
    private List<Player> players;
    private String currentPlayerName;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public void setCurrentPlayerName(String currentPlayerName) {
        this.currentPlayerName = currentPlayerName;
    }
}
