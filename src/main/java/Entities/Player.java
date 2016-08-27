package Entities;

import java.util.List;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class Player {
    private String name;
    private String callsign;
    private String rankName;
    private String rank;
    private boolean invulnerability;
    private List<Award> award;
    private long countryId;
    private String squadron;
    private String password;
    private List<Game> games;
    private long lastGame;
    private Statistics statistics;

    public boolean isInvulnerability() {
        return invulnerability;
    }

    public void setInvulnerability(boolean invulnerability) {
        this.invulnerability = invulnerability;
    }

    public List<Award> getAward() {
        return award;
    }

    public void setAward(List<Award> award) {
        this.award = award;
    }

    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

    public String getSquadron() {
        return squadron;
    }

    public void setSquadron(String squadron) {
        this.squadron = squadron;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
