package com.epam.command;

public class CommandExecute {
    RoteType roteType;
    private String pagePath;

    public CommandExecute(RoteType roteType, String pagePath) {
        this.roteType = roteType;
        this.pagePath = pagePath;
    }

    public CommandExecute(RoteType roteType) {
        this.roteType = roteType;
    }

    public RoteType getRoteType() {
        return roteType;
    }

    public String getPagePath() {
        return pagePath;
    }

    public void setRoteType(RoteType roteType) {
        this.roteType = roteType;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }
}
