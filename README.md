# auth-ui
Android App Auth(Login, Signup and Forgot Password) UI


<!--[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20Smart%20Login-green.svg?style=true)](https://android-arsenal.com/details/1/3026)-->

<!--![Image](https://raw.githubusercontent.com/CodelightStudios/Android-Smart-Login/master/Screenshots/Info_new.png)-->

# What's in the box

- The login, signup and forgot password UI framework for your app
- Easy way to implement
- Play with element visibility
- Setup material theme like Teal, Cyan, Indigo and many more.
- Hide and Show social logins.

# Setup
## 1. Include in your project

### Using Gradle
The **Auth UI** library is pushed to jcenter, so you need to add the following dependency to your app's `build.gradle`.

```gradle
compile 'com.sayagodshala:auth-ui:1.0'
```

### As a module
If you can't include it as gradle dependency, you can also download this GitHub repo and copy the auth-ui folder to your project.


## 2. Usage

First step in configuring the Auth UI Framework is to place `FrameLayout Container` in your layout.

```xml
<FrameLayout
        android:id="@+id/frame"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```

Next step is to configure the `AuthUISettings`.

Example:

```java
AuthUISettings authUISettings = new AuthUISettings();
authUISettings.setSocialPlatformRequired(true);
authUISettings.setAppLogoRequired(true);
authUISettings.setTermsRequired(true);
authUISettings.setSignupRequired(true);
authUISettings.setFacebookLoginRequired(true);
authUISettings.setGoogleLoginRequired(true);
authUISettings.setForgotPasswordRequired(true);
authUISettings.setAppLogo(R.mipmap.my_logo);
authUISettings.setLoginTitle("Login using your registered email and password.");
authUISettings.setSignupTitle("You are just few steps a head. Register and start a head.");
authUISettings.setForgotPasswordTitle("Put in your email id for password reset link");
authUISettings.setLoginTerms("By Logging in I agree to the <b>Terms of Use</b>");
authUISettings.setSignupTerms("By Signing up I agree to the <b>Terms of Use</b>");
authUISettings.setFacebookLoginTitle("Login with Facebook");
authUISettings.setFacebookSignupTitle("Signup with Facebook");
authUISettings.setGoogleLoginTitle("Login with Google");
authUISettings.setGoogleSignupTitle("Signup with Google");
authUISettings.setLoginToggleTitle("Have an account? <b>LOGIN</b>");
authUISettings.setSignupToggleTitle("Don\'t have an account? <b>SIGN UP</b>");
authUISettings.setDefaultView(AuthUIView.LOGIN);
authUISettings.setMaterialTheme(MaterialTheme.CYAN);
```
Next step is to load the `AuthUIFragment` in your Activity.

```java
AuthUIFragment.loadFragment(this, AuthUIFragment.newInstance(authUISettings), R.id.frame);
```

Final step is to implement `AuthUIFragment.AuthUIFragmentListener` interface in your target activity where `AuthUIFragment` is loaded with corresponding methods.

```java
public class LoginActivity extends AppCompatActivity implements AuthUIFragment.AuthUIFragmentListener {

    @Override
    public void onLoginClicked(String username, String password) {
        ...call your api
    }

    @Override
    public void onSignupClicked(String name, String email, String mobile, String password) {
        ...call your api
    }

    @Override
    public void onForgotPasswordClicked(String email) {
        ...call your api
    }

    @Override
    public void onFacebookClicked(boolean isRegistration) {
        ...call your api
    }

    @Override
    public void onGoogleClicked(boolean isRegistration) {
        ...call your api
    }

}
```
This is the simplest way to configure the library to enable Custom login mode along with Social Platform login modes.

**That's it!**

# Included Libraries
The following third-party libraries were used in this framework.

- GSON library

# Contribution
All contributions are welcome. Encounter any issue? Don't hesitate to [open an issue](https://github.com/sayagodshala/auth-ui/issues)

Convention: **Master branch** would be the development branch. So feel free to fork from the Master branch.

# License

    Copyright 2017 Codelight Studios

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.