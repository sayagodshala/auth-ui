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
    RelativeLayout fb;
    RelativeLayout google;
    TextView terms;
    TextView signinSignup;
    TextView facebookTv;
    TextView googleTv;
    TextView or;
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

    public static void loadFragment(FragmentActivity activity, android.support.v4.app.Fragment f, int frameId) {
        activity.getSupportFragmentManager().beginTransaction().add(frameId, f, TAG).addToBackStack(AuthUIFragment.TAG).commitAllowingStateLoss();
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
            bindView(view);
            setClickListener();
            bindData();
        } else {
            getActivity().getFragmentManager().popBackStack();
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

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
                        mListener.onSignupClicked(name.getText().toString(), email.getText().toString(), mobile.getText().toString(), password.getText().toString());
                    }
                }
            } else {
                if (!proceed.getText().toString().equalsIgnoreCase("Login")) {
                    if (isSignInValid()) {
                        if (mListener != null) {
                            mListener.onLoginClicked(email.getText().toString(), password.getText().toString());
                        }
                    }
                } else {
                    if (isForgotPasswordValid()) {
                        if (mListener != null) {
                            mListener.onForgotPasswordClicked(email.getText().toString());
                        }
                    }
                }

            }

        } else if (view.getId() == R.id.fb) {
            if (mListener != null)
                mListener.onFacebookClicked(layoutName.getVisibility() == View.VISIBLE);

        } else if (view.getId() == R.id.google) {
            if (mListener != null)
                mListener.onGoogleClicked(layoutName.getVisibility() == View.VISIBLE);
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
        }
    }

    public interface AuthUIFragmentListener {
        void onLoginClicked(String username, String password);

        void onSignupClicked(String name, String email, String mobile, String password);

        void onForgotPasswordClicked(String email);

        void onFacebookClicked(boolean isRegistration);

        void onGoogleClicked(boolean isRegistration);
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
    }

    private void setClickListener() {
        signinSignup.setOnClickListener(this);
        proceed.setOnClickListener(this);
        google.setOnClickListener(this);
        fb.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    private void bindData() {
        if (bundle != null) {

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

                if (!authUISettings.isSignupRequired())
                    signinSignup.setVisibility(View.GONE);

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
    }

    private void setLoginView() {
        if (authUISettings != null) {
            layoutName.setVisibility(View.GONE);
            layoutMobile.setVisibility(View.GONE);
            if (!authUISettings.isForgotPasswordRequired()) {
                forgotPassword.setVisibility(View.GONE);
            } else {
                forgotPassword.setVisibility(View.VISIBLE);
            }
            belowCont.setVisibility(View.VISIBLE);
            title.setText(authUISettings.getLoginTitle());
            terms.setText(authUISettings.getLoginTerms());
            if (authUISettings.isSocialPlatformRequired()) {
                facebookTv.setText(authUISettings.getFacebookLoginTitle());
                googleTv.setText(authUISettings.getGoogleLoginTitle());
            }

            if (authUISettings.isSignupRequired()) {
                signinSignup.setText(authUISettings.getSignupToggleTitle());
            }
            proceed.setText(getString(R.string.loggin));
        }
    }

    private void setSignupView() {
        if (authUISettings != null) {
            layoutName.setVisibility(View.VISIBLE);
            layoutMobile.setVisibility(View.VISIBLE);
            forgotPassword.setVisibility(View.GONE);
            title.setText(authUISettings.getSignupTitle());
            terms.setText(authUISettings.getSignupTerms());
            belowCont.setVisibility(View.VISIBLE);
            if (authUISettings.isSocialPlatformRequired()) {
                facebookTv.setText(authUISettings.getFacebookSignupTitle());
                googleTv.setText(authUISettings.getGoogleSignupTitle());
            }

            if (authUISettings.isSignupRequired()) {
                signinSignup.setText(authUISettings.getLoginToggleTitle());
            }
            proceed.setText(getString(R.string.signup));
        }
    }

    private void setForgotPasswordView() {
        if (authUISettings != null) {
            layoutName.setVisibility(View.GONE);
            layoutMobile.setVisibility(View.GONE);
            layoutEmail.setVisibility(View.VISIBLE);
            layoutPassword.setVisibility(View.GONE);
            title.setText(authUISettings.getForgotPasswordTitle());
            belowCont.setVisibility(View.GONE);
            signinSignup.setVisibility(View.GONE);
            proceed.setText(getString(R.string.submit));
        }
    }


    private boolean isSignInValid() {
        String validationMessage = "";
        String emailStr = email.getText().toString().trim();
        if (!isValidEmail(emailStr)) {
            email.requestFocus();
            validationMessage = "Please enter valid email";
        } else if (textIsEmpty(password.getText().toString())) {
            password.requestFocus();
            validationMessage = "Invalid Password";
        }

        if (validationMessage.length() != 0)
            showSnackBar(validationMessage);

        return validationMessage.length() == 0;
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
        } else if (textIsEmpty(mobileStr)) {
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
        if (authUISettings != null) {

            MaterialTheme materialTheme = authUISettings.getMaterialTheme();
            MaterialColor materialColor = materialTheme.getColor();

            if (authUISettings.getBg() != 0) {
                bg.setImageResource(authUISettings.getBg());
            } else {
                bg.setBackgroundColor(getResources().getColor(materialColor.getRegular()));
            }

            appLogo.setColorFilter(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));

            title.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
            forgotPassword.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
            terms.setTextColor(ContextCompat.getColor(getContext(), materialColor.getTextPrimaryColor()));
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
    }


}
