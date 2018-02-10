package com.sayagodshala.authui;

public enum MaterialColor {
    RED(R.color.white,R.color.dark_grey,R.color.mat_red,R.color.mat_red_light_dark, R.color.mat_red_dark),
    PINK(R.color.white,R.color.dark_grey,R.color.mat_pink,R.color.mat_pink_light_dark,R.color.mat_pink_dark),
    PURPLE(R.color.white,R.color.dark_grey,R.color.mat_purple,R.color.mat_purple_light_dark,R.color.mat_purple_dark),
    DEEP_PURPLE(R.color.white,R.color.dark_grey,R.color.mat_deep_purple,R.color.mat_deep_purple_light_dark,R.color.mat_deep_purple_dark),
    INDIGO(R.color.white,R.color.dark_grey,R.color.mat_indigo,R.color.mat_indigo_light_dark,R.color.mat_indigo_dark),
    BLUE(R.color.white,R.color.dark_grey,R.color.mat_blue,R.color.mat_blue_light_dark,R.color.mat_blue_dark),
    LIGHT_BLUE(R.color.white,R.color.dark_grey,R.color.mat_light_blue,R.color.mat_light_blue_light_dark,R.color.mat_light_blue_dark),
    CYAN(R.color.white,R.color.dark_grey,R.color.mat_cyan,R.color.mat_cyan_light_dark,R.color.mat_cyan_dark),
    TEAL(R.color.white,R.color.dark_grey,R.color.mat_teal,R.color.mat_teal_light_dark,R.color.mat_teal_dark),
    BLUE_GREY(R.color.white,R.color.dark_grey,R.color.mat_blue_grey,R.color.mat_blue_grey_light_dark,R.color.mat_blue_grey_dark),
    YELLOW(R.color.black,R.color.dark_grey,R.color.white,R.color.grey,R.color.dark_grey),
    WHITE(R.color.black,R.color.dark_grey,R.color.white,R.color.grey,R.color.dark_grey);

    private final int textPrimaryColor;
    private final int textSecondaryColor;
    private final int regular;
    private final int light;
    private final int dark;

    MaterialColor(int textPrimaryColor,int textSecondaryColor,int regular, int light, int dark) {
        this.textPrimaryColor = textPrimaryColor;
        this.textSecondaryColor = textSecondaryColor;
        this.regular = regular;
        this.light = light;
        this.dark = dark;
    }

    public int getRegular() {
        return regular;
    }

    public int getLight() {
        return light;
    }

    public int getDark() {
        return dark;
    }

    public int getTextPrimaryColor() {
        return textPrimaryColor;
    }

    public int getTextSecondaryColor() {
        return textSecondaryColor;
    }
}


