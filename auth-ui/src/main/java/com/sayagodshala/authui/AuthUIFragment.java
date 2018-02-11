package com.sayagodshala.authui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;


public class AuthUIFragment extends Fragment implements View.OnClickListener {


    public static final String TAG = "AuthUIFragment";

    public static String AUTHUI_SETTINGS = "authui_settings";

    private Bundle bundle;
    private AuthUIFragmentListener mListener;

    ImageView bg;
    ImageView appLogo;
    TextView title;
    TextInputLayout layoutName;
    EditText name;
    TextInputLayout layoutEmail;
    EditText email;
    TextInputLayout layoutMobile;
    EditText mobile;
    TextInputLayout layoutPassword;
    EditText password;
    Button proceed;
    TextView forgotPassword;
    LinearLayout orCont;
    LinearLayout socials;
    LinearLayout belowCont;
    LinearLayout fpLink;
    RelativeLayout fb;
    RelativeLayout google;
    TextView terms;
    TextView signinSignup;
    TextView facebookTv;
    TextView googleTv;
    TextView or;
    TextView fpLogin;
    TextView fpSignup;
    View socialDivider;
    private View view;

    private AuthUISettings authUISettings;

    public AuthUIFragment() {
        // Required empty public constructor
    }

    public static AuthUIFragment newInstance(AuthUISettings authUISettings) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AUTHUI_SETTINGS, authUISettings);
        AuthUIFragment fragment = new AuthUIFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    public static AuthUIFragment newInstanceWithDefaultSettings() {
        AuthUIFragment fragment = new AuthUIFragment();
        return fragment;
    }

    public static void loadFragment(FragmentActivity activity, android.support.v4.app.Fragment f, int frameId) {
        activity.getSupportFragmentManager().beginTransaction().add(frameId, f, TAG).commitAllowingStateLoss();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        bundle = getArguments();
        if (bundle != null && bundle.containsKey(AUTHUI_SETTINGS)) {
            authUISettings = (AuthUISettings) bundle.getParcelable(AUTHUI_SETTINGS);
            Log.d("AuthUISettings", new Gson().toJson(authUISettings));
        } else {
            authUISettings = new AuthUISettings();
            Log.d("Default AuthUISettings", new Gson().toJson(authUISettings));
        }
        bindView(view);
        setClickListener();
        bindData();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthUIFragmentListener) {
            mListener = (AuthUIFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AuthUIFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.proceed) {
            if (layoutName.getVisibility() == View.VISIBLE) {
                if (isSignUpValid()) {
                    if (mListener != null) {
                        AuthUIUser authUIUser = new AuthUIUser();
                        authUIUser.setName(name.getText().toString());
                        authUIUser.setEmail(email.getText().toString());
                        authUIUser.setMobile(mobile.getText().toString());
                        authUIUser.setPassword(password.getText().toString());
                        mListener.onSignupClicked(authUIUser);
                    }
                }
            } else {
                if (proceed.getText().toString().equalsIgnoreCase("login")) {
                    AuthUIUser authUIUser = new AuthUIUser();
                    authUIUser.setLoginType(authUISettings.getLoginType());
                    switch (authUISettings.getLoginType()) {
                        case EMAIL:
                            if (isSignInValid()) {
                                authUIUser.setEmail(email.getText().toString());
                                authUIUser.setPassword(password.getText().toString());
                                mListener.onLoginClicked(authUIUser);
                            }
                            break;
                        case MOBILE:
                            if (isSignInWithMobileAndPasswordValid()) {
                                authUIUser.setMobile(mobile.getText().toString());
                                authUIUser.setPassword(password.getText().toString());
                                mListener.onLoginClicked(authUIUser);
                            }
                            break;
                        case EMAIL_OR_MOBILE:
                            if (isSignInWithEmailOrMobileValid()) {
                                authUIUser.setEmailOrMobile(email.getText().toString());
                                authUIUser.setPassword(password.getText().toString());
                                mListener.onLoginClicked(authUIUser);
                            }
                            break;
                    }

                } else {
                    if (isForgotPasswordValid()) {
                        if (mListener != null) {
                            mListener.onForgotPasswordClicked(new AuthUIUser(email.getText().toString()));
                        }
                    }
                }

            }

        } else if (view.getId() == R.id.fb) {
            if (mListener != null)
                mListener.onSocialAccountClicked(SocialAccount.FACEBOOK, layoutName.getVisibility() == View.VISIBLE);
        } else if (view.getId() == R.id.google) {
            if (mListener != null)
                mListener.onSocialAccountClicked(SocialAccount.GOOGLE, layoutName.getVisibility() == View.VISIBLE);
        } else if (view.getId() == R.id.signin_signup) {
            if (!authUISettings.isSignupRequired())
                return;
            if (layoutName.getVisibility() == View.VISIBLE) {
                setLoginView();
            } else {
                setSignupView();
            }
        } else if (view.getId() == R.id.forgot_password) {
            setForgotPasswordView();
        } else if (view.getId() == R.id.fp_login) {
            setLoginView();
        } else if (view.getId() == R.id.fp_signup) {
            setSignupView();
        }

    }

    private void bindView(View view) {
        bg = view.findViewById(R.id.bg);
        appLogo = view.findViewById(R.id.app_logo);
        title = view.findViewById(R.id.title);
        layoutName = view.findViewById(R.id.layout_name);
        name = view.findViewById(R.id.name);
        layoutEmail = view.findViewById(R.id.layout_email);
        email = view.findViewById(R.id.email);
        layoutMobile = view.findViewById(R.id.layout_mobile);
        mobile = view.findViewById(R.id.mobile);
        layoutPassword = view.findViewById(R.id.layout_password);
        password = view.findViewById(R.id.password);
        proceed = view.findViewById(R.id.proceed);
        orCont = view.findViewById(R.id.or_cont);
        forgotPassword = view.findViewById(R.id.forgot_password);
        socials = view.findViewById(R.id.socials);
        fb = view.findViewById(R.id.fb);
        google = view.findViewById(R.id.google);
        terms = view.findViewById(R.id.terms);
        signinSignup = view.findViewById(R.id.signin_signup);
        facebookTv = view.findViewById(R.id.facebook_tv);
        googleTv = view.findViewById(R.id.google_tv);
        or = view.findViewById(R.id.or);
        socialDivider = view.findViewById(R.id.social_divider);
        belowCont = view.findViewById(R.id.below_cont);
        fpLink = view.findViewById(R.id.fp_link);
        fpLogin = view.findViewById(R.id.fp_login);
        fpSignup = view.findViewById(R.id.fp_signup);
    }

    private void setClickListener() {
        signinSignup.setOnClickListener(this);
        proceed.setOnClickListener(this);
        google.setOnClickListener(this);
        fb.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        fpLogin.setOnClickListener(this);
        fpSignup.setOnClickListener(this);
    }

    public void recallLoginView() {
        bindData();
    }

    public void bindData() {
        email.setText("");
        if (authUISettings != null) {
            if (!authUISettings.isFacebookLoginRequired() && !authUISettings.isGoogleLoginRequired()) {
                authUISettings.setSocialPlatformRequired(false);
            }

            if (!authUISettings.isSocialPlatformRequired()) {
                socials.setVisibility(View.GONE);
                orCont.setVisibility(View.GONE);
            }

            if (!authUISettings.isFacebookLoginRequired()) {
                socialDivider.setVisibility(View.GONE);
                fb.setVisibility(View.GONE);
            }

            if (!authUISettings.isGoogleLoginRequired()) {
                socialDivider.setVisibility(View.GONE);
                google.setVisibility(View.GONE);
            }

            if (!authUISettings.isAppLogoRequired()) {
                appLogo.setVisibility(View.VISIBLE);
            } else {
                if (authUISettings.getAppLogo() != 0) {
                    appLogo.setImageResource(authUISettings.getAppLogo());
                }
            }

            switch (authUISettings.getDefaultView()) {
                case LOGIN:
                    setLoginView();
                    break;
                case SIGNUP:
                    setSignupView();
                    break;
                default:
                    setForgotPasswordView();
                    break;
            }
            applyTheme();
        }
    }

    private void setLoginView() {
        layoutName.setVisibility(View.GONE);
        layoutMobile.setVisibility(View.GONE);
        fpLink.setVisibility(View.GONE);
        layoutPassword.setVisibility(View.VISIBLE);
        layoutEmail.setVisibility(View.VISIBLE);
        if (!authUISettings.isForgotPasswordRequired()) {
            forgotPassword.setVisibility(View.GONE);
        } else {
            forgotPassword.setVisibility(View.VISIBLE);
        }
        belowCont.setVisibility(View.VISIBLE);
        title.setText(Html.fromHtml(authUISettings.getLoginTitle()));
        terms.setText(Html.fromHtml(authUISettings.getLoginTerms()));

        if (!textIsEmpty(authUISettings.getEmailHint()))
            layoutEmail.setHint(authUISettings.getEmailHint());
        if (!textIsEmpty(authUISettings.getPasswordHint()))
            layoutPassword.setHint(authUISettings.getPasswordHint());

        switch (authUISettings.getLoginType()) {
            case EMAIL:
                break;
            case MOBILE:
                layoutEmail.setVisibility(View.GONE);
                layoutMobile.setVisibility(View.VISIBLE);
                break;
            case EMAIL_OR_MOBILE:
                layoutEmail.setHint("Email/Mobile");
                break;
        }

        if (authUISettings.isSocialPlatformRequired()) {
            facebookTv.setText(Html.fromHtml(authUISettings.getFacebookLoginTitle()));
            googleTv.setText(Html.fromHtml(authUISettings.getGoogleLoginTitle()));
        }

        if (authUISettings.isSignupRequired()) {
            signinSignup.setVisibility(View.VISIBLE);
            signinSignup.setText(Html.fromHtml(authUISettings.getSignupToggleTitle()));
        } else {
            signinSignup.setVisibility(View.GONE);
        }

        proceed.setText(getString(R.string.loggin));
    }

    private void setSignupView() {
        layoutName.setVisibility(View.VISIBLE);
        layoutMobile.setVisibility(View.VISIBLE);
        layoutEmail.setVisibility(View.VISIBLE);
        layoutPassword.setVisibility(View.VISIBLE);
        fpLink.setVisibility(View.GONE);
        forgotPassword.setVisibility(View.GONE);
        title.setText(authUISettings.getSignupTitle());
        terms.setText(authUISettings.getSignupTerms());
        belowCont.setVisibility(View.VISIBLE);

        if (!textIsEmpty(authUISettings.getEmailHint()))
            layoutEmail.setHint(authUISettings.getEmailHint());
        if (!textIsEmpty(authUISettings.getMobileHint()))
            layoutMobile.setHint(authUISettings.getMobileHint());
        if (!textIsEmpty(authUISettings.getPasswordHint()))
            layoutPassword.setHint(authUISettings.getPasswordHint());
        if (!textIsEmpty(authUISettings.getNameHint()))
            layoutName.setHint(authUISettings.getNameHint());

        if (authUISettings.isSocialPlatformRequired()) {
            facebookTv.setText(Html.fromHtml(authUISettings.getFacebookSignupTitle()));
            googleTv.setText(Html.fromHtml(authUISettings.getGoogleSignupTitle()));
        }

        if (authUISettings.isSignupRequired()) {
            signinSignup.setVisibility(View.VISIBLE);
            signinSignup.setText(Html.fromHtml(authUISettings.getLoginToggleTitle()));
        } else {
            signinSignup.setVisibility(View.GONE);
        }
        proceed.setText(getString(R.string.signup));
    }

    private void setForgotPasswordView() {
        layoutName.setVisibility(View.GONE);
        layoutMobile.setVisibility(View.GONE);
        layoutEmail.setVisibility(View.VISIBLE);
        layoutPassword.setVisibility(View.GONE);
        title.setText(Html.fromHtml(authUISettings.getForgotPasswordTitle()));
        belowCont.setVisibility(View.GONE);
        signinSignup.setVisibility(View.GONE);
        fpLink.setVisibility(View.VISIBLE);
        fpLogin.setText(getString(R.string.loggin));
        fpSignup.setText(getString(R.string.signup));
        proceed.setText(getString(R.string.submit));
        if (!authUISettings.isSignupRequired())
            fpSignup.setVisibility(View.GONE);
    }

    private boolean isSignInValid() {
        String validationMessage = "";
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        if (!isValidEmail(emailStr)) {
            email.requestFocus();
            validationMessage = "Please enter valid email";
        } else if (textIsEmpty(passwordStr)) {
            password.requestFocus();
            validationMessage = "Invalid Password";
        }

        checkErrorMessage(validationMessage);

        return validationMessage.length() == 0;
    }

    private boolean isSignInWithEmailOrMobileValid() {
        String validationMessage = "";
        String emailStr = email.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        if (textIsEmpty(emailStr)) {
            email.requestFocus();
            validationMessage = "Please enter email/mobile";
        } else if (textIsEmpty(passwordStr)) {
            password.requestFocus();
            validationMessage = "Invalid Password";
        }

        checkErrorMessage(validationMessage);

        return validationMessage.length() == 0;
    }

    private boolean isSignInWithMobileAndPasswordValid() {
        String validationMessage = "";
        String mobileStr = mobile.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        if (textIsEmpty(mobileStr)) {
            mobile.requestFocus();
            validationMessage = "Please enter valid mobile";
        } else if (mobileStr.length() < 10) {
            validationMessage = "mobile number should be 10 digits long";
        } else if (textIsEmpty(passwordStr)) {
            password.requestFocus();
            validationMessage = "Invalid Password";
        }

        checkErrorMessage(validationMessage);

        return validationMessage.length() == 0;
    }

    private void checkErrorMessage(String validationMessage) {
        if (validationMessage.length() != 0) {
            if (authUISettings.isHandleFormError()) {
                mListener.onFormValidationError(validationMessage);
            } else {
                showSnackBar(validationMessage);
            }
        }
    }

    private boolean isSignUpValid() {
        String validationMessage = "";
        String nameStr = name.getText().toString().trim();
        String emailStr = email.getText().toString().trim();
        String mobileStr = mobile.getText().toString().trim();
        String passwordStr = password.getText().toString().trim();
        if (textIsEmpty(nameStr)) {
            name.requestFocus();
            validationMessage = "Please enter name";
        } else if (!isValidEmail(emailStr)) {
            email.requestFocus();
            validationMessage = "Please enter valid email";
        } else if (textIsEmpty(mobileStr) && mobileStr.length() < 10) {
            mobile.requestFocus();
            validationMessage = "Please enter mobile";
        } else if (textIsEmpty(passwordStr)) {
            mobile.requestFocus();
            validationMessage = "Please enter password";
        }

        if (validationMessage.length() != 0)
            showSnackBar(validationMessage);

        return validationMessage.length() == 0;
    }

    private boolean isForgotPasswordValid() {
        String validationMessage = "";
        String emailStr = email.getText().toString().trim();
        if (!isValidEmail(emailStr)) {
            email.requestFocus();
            validationMessage = "Please enter valid email";
        }

        if (validationMessage.length() != 0)
            showSnackBar(validationMessage);

        return validationMessage.length() == 0;
    }

    public static boolean textIsEmpty(String value) {
        if (value == null)
            return true;
        boolean empty = false;
        String message = value.trim();
        if (TextUtils.isEmpty(message)) {
            empty = true;
        }
        boolean isWhitespace = message.matches("^\\s*$");
        if (isWhitespace) {
            empty = true;
        }
        return empty;
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    public static StateListDrawable selectorBackgroundColor(int normal, int pressed) {
        GradientDrawable normalDrawable = new GradientDrawable();
        normalDrawable.setColor(normal);
        normalDrawable.setCornerRadius(8);
        GradientDrawable pressedDrawable = new GradientDrawable();
        pressedDrawable.setColor(pressed);
        pressedDrawable.setCornerRadius(8);
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                pressedDrawable);
        states.addState(new int[]{}, normalDrawable);
        return states;
    }

    private void applyTheme() {

        MaterialTheme materialTheme = authUISettings.getMaterialTheme();
        MaterialColor materialColor = materialTheme.getColor();

        if (authUISettings.getBg() != 0) {
            bg.setImageResource(authUISettings.getBg());
        } else {
            bg.setBackgroundColor(getResources().getColor(materialColor.getRegular()));
        }

//            appLogo.setColorFilter(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));

        title.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        forgotPassword.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        terms.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        fpLogin.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        fpSignup.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        proceed.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));

        email.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        mobile.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        name.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        password.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        or.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));

        proceed.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
        proceed.setBackgroundDrawable(selectorBackgroundColor(getResources().getColor(materialColor.getLight()), getResources().getColor(materialColor.getDark())));

        if (materialTheme.equals(MaterialTheme.INDIGO)) {
            fb.setBackgroundDrawable(selectorBackgroundColor(getResources().getColor(materialColor.getLight()), getResources().getColor(materialColor.getDark())));
        } else if (materialTheme.equals(MaterialTheme.PINK)) {
            google.setBackgroundDrawable(selectorBackgroundColor(getResources().getColor(materialColor.getLight()), getResources().getColor(materialColor.getDark())));
        } else if (materialTheme.equals(MaterialTheme.RED)) {
            google.setBackgroundDrawable(selectorBackgroundColor(getResources().getColor(materialColor.getLight()), getResources().getColor(materialColor.getDark())));
        }

        signinSignup.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getContext(), materialColor.getRegular()));
        }
    }

    public AuthUISettings defaultAuthUISettings() {
        AuthUISettings authUISettings = new AuthUISettings();
        authUISettings.setSocialPlatformRequired(true);
        authUISettings.setAppLogoRequired(true);
        authUISettings.setTermsRequired(true);
        authUISettings.setSignupRequired(true);
        authUISettings.setFacebookLoginRequired(true);
        authUISettings.setGoogleLoginRequired(true);
        authUISettings.setForgotPasswordRequired(true);
        authUISettings.setAppLogo(R.mipmap.ic_launcher);
        authUISettings.setHandleFormError(true);
        authUISettings.setLoginTitle(getActivity().getString(R.string.login_title));
        authUISettings.setSignupTitle(getActivity().getString(R.string.signup_title));
        authUISettings.setForgotPasswordTitle(getActivity().getString(R.string.forgot_password_title));
        authUISettings.setLoginTerms(getActivity().getString(R.string.loggin_terms));
        authUISettings.setSignupTerms(getActivity().getString(R.string.signup_terms));
        authUISettings.setFacebookLoginTitle(getActivity().getString(R.string.login_with_facebook));
        authUISettings.setFacebookSignupTitle(getActivity().getString(R.string.signup_with_facebook));
        authUISettings.setGoogleLoginTitle(getActivity().getString(R.string.login_with_google));
        authUISettings.setGoogleSignupTitle(getActivity().getString(R.string.signup_with_google));
        authUISettings.setLoginToggleTitle(getActivity().getString(R.string.have_an_account));
        authUISettings.setSignupToggleTitle(getActivity().getString(R.string.dont_have_account));
        authUISettings.setDefaultView(AuthUIView.LOGIN);
        authUISettings.setMaterialTheme(MaterialTheme.WHITE);
        return authUISettings;
    }


}
