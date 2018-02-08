package com.sayagodshala.authui;

public enum AuthView {
    LOGIN("login"),
    SIGNUP("signup");

    private final String description;

    AuthView(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
