package com.sayagodshala.authui;

public interface AuthUIFragmentListener {
    void onLoginClicked(AuthUIUser user);

    void onSignupClicked(AuthUIUser user);

    void onForgotPasswordClicked(AuthUIUser user);

    void onSocialAccountClicked(SocialAccount socialAccount, boolean isRegistration);

    void onFormValidationError(String error);
}