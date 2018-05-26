package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public interface DirectedRouteGraph extends Route {

    int numberRemainingRoutes();

    boolean inProgress();

    void start();

    Route nextRoute();

    Route peekAtNextRoute();
}
