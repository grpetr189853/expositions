package com.grpetr.task.web.result;

public class RedirectResult implements CommandResult{
    private String redirectResource;

    public RedirectResult(String resource){
        this.redirectResource = resource;
    }

    public String getResource() {
        return redirectResource;
    }
}
