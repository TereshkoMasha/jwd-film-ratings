package com.epam.command;

public class CommandExecute {
    RouteType routeType;
    private String pagePath;

    public CommandExecute(RouteType routeType, String pagePath) {
        this.routeType = routeType;
        this.pagePath = pagePath;
    }

    public RouteType getRouteType() {
        return routeType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setRouteType(RouteType routeType) {
        this.routeType = routeType;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}
