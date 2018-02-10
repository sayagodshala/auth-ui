package com.sayagodshala.authui;

public class AuthUIUser {

    private String name;
    private String email;
    private String mobile;
    private String emailOrMobile;
    private String password;
    private LoginType loginType;

    public AuthUIUser() {
    }

    public AuthUIUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AuthUIUser(String name, String emailOrMobile, String password) {
        this.name = name;
        this.emailOrMobile = emailOrMobile;
        this.password = password;
    }

    public AuthUIUser(String email) {
        this.email = email;
    }

    public AuthUIUser(String name, String email, String mobile, String password) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailOrMobile() {
        return emailOrMobile;
    }

    public void setEmailOrMobile(String emailOrMobile) {
        this.emailOrMobile = emailOrMobile;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
