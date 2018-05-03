package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Route extends Serializable {
    void setCorrelationId(Long id);
    Long correlationId();
    String getService();
    String getOperation();
    void setEnvelope(Envelope envelope);
    void setRouted(Boolean routed);
    Boolean routed();
}
