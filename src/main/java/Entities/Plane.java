package Entities;

/**
 * Created by d3m0 on 27.08.2016.
 */
public class Plane {
    private String planeName;
    private Statistics statistics;

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
