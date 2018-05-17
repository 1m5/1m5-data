package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Route extends Serializable {
    void setId(Long id);
    Long getId();
    String getService();
    String getOperation();
    void setEnvelope(Envelope envelope);
    void setRouted(boolean routed);
    Boolean routed();
}
