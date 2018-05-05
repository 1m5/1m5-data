package io.onemfive.data.health;

import org.dizitart.no2.IndexType;
import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.Index;
import org.dizitart.no2.objects.Indices;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
@Indices({
        @Index(value = "did", type = IndexType.NonUnique)
})
public class HealthRecord implements Serializable {

    public enum HealthStatus {Unknown, Poor, Good, Excellent}

    @Id
    private Long id;
    private Long did;
    private HealthStatus healthStatus = HealthStatus.Unknown;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDid() {
        return did;
    }

    public void setDid(Long did) {
        this.did = did;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }
}
