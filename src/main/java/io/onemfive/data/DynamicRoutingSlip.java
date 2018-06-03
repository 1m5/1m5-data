package io.onemfive.data;

/**
 * TODO: Add Description
 *
 * @author objectorange
 */
public final class DynamicRoutingSlip extends BaseRoute implements RoutingSlip {

    protected Stack<Route> routes = new DequeStack<>();
    private boolean inProgress = false;

    public DynamicRoutingSlip() {}

    @Override
    public int numberRemainingRoutes() {
        return routes.numberRemainingRoutes();
    }

    @Override
    public boolean inProgress() {
        return inProgress;
    }

    @Override
    public void start() {
        inProgress = true;
    }

    @Override
    public Route nextRoute() {
        return routes.pop();
    }

    @Override
    public Route peekAtNextRoute() {
        return routes.peek();
    }

    public boolean addRoute(Route route) {
        route.setId(getId());
        this.routes.push(route);
        return true;
    }

}
