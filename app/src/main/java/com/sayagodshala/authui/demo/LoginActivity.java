package com.sayagodshala.authui.demo;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class LoginActivity extends AppCompatActivity implements AuthUIFragment.AuthUIFragmentListener{

    AuthUIFragment authUIFragment;

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
        authUISettings.setGoogleLoginRequired(false);
        authUISettings.setLoginTitle(getString(R.string.login_title));
        authUISettings.setSignupTitle(getString(R.string.signup_title));
        authUISettings.setLoginTerms(getString(R.string.loggin_terms));
        authUISettings.setSignupTerms(getString(R.string.signup_terms));
        authUISettings.setFacebookLoginTitle(getString(R.string.login_with_facebook));
        authUISettings.setFacebookSignupTitle(getString(R.string.signup_with_facebook));
        authUISettings.setGoogleLoginTitle(getString(R.string.login_with_google));
        authUISettings.setGoogleSignupTitle(getString(R.string.signup_with_google));
        authUISettings.setLoginToggleTitle(getString(R.string.have_an_account));
        authUISettings.setSignupToggleTitle(getString(R.string.dont_have_account));
        authUISettings.setDefaultView(AuthView.SIGNUP);

        Bundle bundle = new Bundle();
        bundle.putParcelable(AuthUIFragment.AUTHUI_SETTINGS, authUISettings);
        authUIFragment = AuthUIFragment.newInstance(bundle);
        loadFragment(authUIFragment,  false);
    }

    public void loadFragment(android.support.v4.app.Fragment f, boolean addToBackStack) {
        String tag = getFragmentTag(1);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame, f, tag);
        if (addToBackStack)
            ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
        getFragmentManager().executePendingTransactions();
    }

    private String getFragmentTag(int uiState) {
        return "uistate-" + uiState;
    }

    @Override
    public void onLoginClicked(String username, String password) {

    }

    @Override
    public void onSignupClicked(String name, String email, String mobile, String password) {

    }

    @Override
    public void onFacebookClicked(boolean isRegistration) {

    }

    @Override
    public void onGoogleClicked(boolean isRegistration) {

    }
}
