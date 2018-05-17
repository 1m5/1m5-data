package io.onemfive.data;

import java.util.Random;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public abstract class BaseRoute implements Route {

    protected Envelope envelope;
    protected String service;
    protected String operation;
    protected Boolean routed = false;
    protected Long routeId = new Random(843444628947321731L).nextLong();

    @Override
    public void setEnvelope(Envelope envelope) {
        this.envelope = envelope;
    }

    @Override
    public String getService() {
        return service;
    }

    @Override
    public String getOperation() {
        return operation;
    }

    @Override
    public void setId(Long routeId) {
        this.routeId = routeId;
    }

    @Override
    public Long getId() {
        return routeId;
    }

    @Override
    public void setRouted(boolean routed) {
        this.routed = routed;
    }

    @Override
    public Boolean routed() {
        return routed;
    }

}
