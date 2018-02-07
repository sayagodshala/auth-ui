package com.sayagodshala.authui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class AuthFragment extends Fragment implements View.OnClickListener {

    public static String SOCIAL_PLATFORM_REQUIRED = "social_platform required";
    public static String APP_LOGO = "app_logo";
    public static String BG = "bg";
    public static String TITLE = "title";
    public static String NAME_HINT = "name_hint";
    public static String EMAIL_HINT = "email_hint";
    public static String MOBILE_HINT = "mobile_hint";
    public static String PASSWORD_HINT = "password_hint";
    public static String TERMS = "terms";
    public static String LOGIN_API = "terms";

    private Bundle bundle;
    private AuthFragmentListener mListener;

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
    RelativeLayout fb;
    RelativeLayout google;
    TextView terms;
    TextView signinSignup;
    private View view;

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance(Bundle bundle) {
        AuthFragment fragment = new AuthFragment();
        if (bundle != null)
            fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        bundle = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        bindView(view);
        bindData();
        setClickListener();
        setLoginView();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthFragmentListener) {
            mListener = (AuthFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
                if (isSignInValid()) {
                    if (mListener != null) {
                        mListener.onLoginClicked(email.getText().toString(), password.getText().toString());
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
            if (layoutName.getVisibility() == View.VISIBLE) {
                setLoginView();
            } else {
                setSignupView();
            }
        }
    }

    public interface AuthFragmentListener {
        void onLoginClicked(String username, String password);

        void onSignupClicked(String name, String email, String mobile, String password);

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
    }

    private void setClickListener() {
        signinSignup.setOnClickListener(this);
        proceed.setOnClickListener(this);
        google.setOnClickListener(this);
        fb.setOnClickListener(this);
    }

    private void bindData() {
        if (bundle != null) {

            if (bundle.containsKey(SOCIAL_PLATFORM_REQUIRED) && !bundle.getBoolean(SOCIAL_PLATFORM_REQUIRED)) {
                socials.setVisibility(View.GONE);
                orCont.setVisibility(View.GONE);
            }

            if (bundle.containsKey(APP_LOGO)) {
                appLogo.setVisibility(View.VISIBLE);
                appLogo.setImageResource(bundle.getInt(APP_LOGO));
            }

            if (bundle.containsKey(BG)) {
                bg.setImageResource(bundle.getInt(BG));
            }

            if (bundle.containsKey(TITLE)) {
                title.setText(bundle.getString(TITLE));
            }

            if (bundle.containsKey(TERMS)) {
                terms.setText(bundle.getString(TERMS));
            }
        }
    }

    private void setLoginView() {
        layoutName.setVisibility(View.GONE);
        layoutMobile.setVisibility(View.GONE);
        signinSignup.setText(getString(R.string.dont_have_account));
        proceed.setText(getString(R.string.loggin));
    }

    private void setSignupView() {
        layoutName.setVisibility(View.VISIBLE);
        layoutMobile.setVisibility(View.VISIBLE);
        signinSignup.setText(getString(R.string.have_an_account));
        proceed.setText(getString(R.string.signup));
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
        } else if (isValidEmail(emailStr)) {
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
                .make(view.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
