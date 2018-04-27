package io.onemfive.data;

import java.io.Serializable;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Route extends Serializable {
    String getService();
    String getOperation();
    void setEnvelope(Envelope envelope);
}
