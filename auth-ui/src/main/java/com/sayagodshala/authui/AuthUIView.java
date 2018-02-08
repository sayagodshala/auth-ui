package com.sayagodshala.authui;

public enum AuthUIView {
    LOGIN("login"),
    SIGNUP("signup"),
    FORGOT_PASSWORD("signup");

    private final String description;

    AuthUIView(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
