package Entities;

import java.util.Date;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class History {
    private Date datetime;
    private int agKills;
    private int aaKills;
    private int result;
    private int deathsCount;
    private int stage;
    private String mission;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getAgKills() {
        return agKills;
    }

    public void setAgKills(int agKills) {
        this.agKills = agKills;
    }

    public int getAaKills() {
        return aaKills;
    }

    public void setAaKills(int aaKills) {
        this.aaKills = aaKills;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getDeathsCount() {
        return deathsCount;
    }

    public void setDeathsCount(int deathsCount) {
        this.deathsCount = deathsCount;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }
}
