package com.sayagodshala.authui.demo;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sayagodshala.authui.AuthUIFragment;
import com.sayagodshala.authui.AuthUISettings;
import com.sayagodshala.authui.AuthUIUser;
import com.sayagodshala.authui.AuthUIView;
import com.sayagodshala.authui.LoginType;
import com.sayagodshala.authui.MaterialTheme;


public class LoginActivity extends AppCompatActivity implements AuthUIFragment.AuthUIFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AuthUISettings authUISettings = new AuthUISettings();
        authUISettings.setSocialPlatformRequired(true);
        authUISettings.setAppLogoRequired(true);
        authUISettings.setTermsRequired(true);
        authUISettings.setSignupRequired(true);
        authUISettings.setFacebookLoginRequired(true);
        authUISettings.setGoogleLoginRequired(true);
        authUISettings.setForgotPasswordRequired(true);
//        authUISettings.setLoginType(LoginType.MOBILE);
        authUISettings.setAppLogo(R.mipmap.my_logo);
        authUISettings.setLoginTitle(getString(R.string.login_title));
        authUISettings.setSignupTitle(getString(R.string.signup_title));
        authUISettings.setForgotPasswordTitle(getString(R.string.forgot_password_title));
        authUISettings.setLoginTerms(getString(R.string.loggin_terms));
        authUISettings.setSignupTerms(getString(R.string.signup_terms));
        authUISettings.setFacebookLoginTitle(getString(R.string.login_with_facebook));
        authUISettings.setFacebookSignupTitle(getString(R.string.signup_with_facebook));
        authUISettings.setGoogleLoginTitle(getString(R.string.login_with_google));
        authUISettings.setGoogleSignupTitle(getString(R.string.signup_with_google));
        authUISettings.setLoginToggleTitle(getString(R.string.have_an_account));
        authUISettings.setSignupToggleTitle(getString(R.string.dont_have_account));
        authUISettings.setDefaultView(AuthUIView.LOGIN);
        authUISettings.setMaterialTheme(MaterialTheme.GREY);

        AuthUIFragment.loadFragment(this, AuthUIFragment.newInstance(authUISettings), R.id.frame);
    }

    @Override
    public void onLoginClicked(AuthUIUser user) {
        switch (user.getLoginType()) {
            case EMAIL:
                Log.d("onLoginClicked", "email : " + user.getEmail() + ", password : " + user.getPassword());
                break;
            case MOBILE:
                Log.d("onLoginClicked", "mobile : " + user.getMobile() + ", password : " + user.getPassword());
                break;
            case EMAIL_OR_MOBILE:
                Log.d("onLoginClicked", "email/mobile : " + user.getEmailOrMobile() + ", password : " + user.getPassword());
                break;
        }
    }

    @Override
    public void onSignupClicked(AuthUIUser user) {
        Log.d("onSignupClicked", "name : " + user.getName() + ", email : " + user.getEmail() + ", mobile : " + user.getMobile() + ", password : " + user.getPassword());
    }

    @Override
    public void onForgotPasswordClicked(AuthUIUser user) {
        Log.d("onForgotPasswordClicked", "email : " + user.getEmail());
    }

    @Override
    public void onFacebookClicked(boolean isRegistration) {
        Log.d("onFacebookClicked", "isRegistration : " + isRegistration);
    }

    @Override
    public void onGoogleClicked(boolean isRegistration) {
        Log.d("onGoogleClicked", "isRegistration : " + isRegistration);
    }
}
