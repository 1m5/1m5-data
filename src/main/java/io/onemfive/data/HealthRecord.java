package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class HealthRecord {

    public enum HealthStatus {Unknown, Critical, Poor, Average, Good, Excellent}

    private LID lid;
    private HealthStatus overallHealth = HealthStatus.Unknown;
    private HealthStatus heartHealth = HealthStatus.Unknown;


    public HealthRecord() {
    }

    public HealthRecord(LID lid) {
        this.lid = lid;
    }

    public LID getLid() {
        return lid;
    }

    public void setLid(LID lid) {
        this.lid = lid;
    }

    public HealthStatus getOverallHealth() {
        return overallHealth;
    }

    public void setOverallHealth(HealthStatus overallHealth) {
        this.overallHealth = overallHealth;
    }

    public HealthStatus getHeartHealth() {
        return heartHealth;
    }

    public void setHeartHealth(HealthStatus heartHealth) {
        this.heartHealth = heartHealth;
    }
}
