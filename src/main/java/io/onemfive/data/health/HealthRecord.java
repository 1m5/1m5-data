package io.onemfive.data.health;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public class HealthRecord implements Serializable {

    public enum HealthStatus {Unknown, Poor, Good, Excellent}

    private HealthStatus healthStatus = HealthStatus.Unknown;

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }
}
