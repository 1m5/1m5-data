package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface Subscription {
    void notifyOfEvent(Envelope envelope);
}
