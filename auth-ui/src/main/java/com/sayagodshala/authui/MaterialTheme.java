package com.sayagodshala.authui;

public enum MaterialTheme {

    RED(MaterialColor.RED),
    PINK(MaterialColor.PINK),
    PURPLE(MaterialColor.PURPLE),
    DEEP_PURPLE(MaterialColor.DEEP_PURPLE),
    INDIGO(MaterialColor.INDIGO),
    BLUE(MaterialColor.BLUE),
    LIGHT_BLUE(MaterialColor.LIGHT_BLUE),
    CYAN(MaterialColor.CYAN),
    TEAL(MaterialColor.TEAL),
    BLUE_GREY(MaterialColor.BLUE_GREY),
    WHITE(MaterialColor.WHITE),
    YELLOW(MaterialColor.YELLOW),
    AMBER(MaterialColor.AMBER),
    BROWN(MaterialColor.BROWN),
    GREEN(MaterialColor.GREEN),
    GREY(MaterialColor.GREY),
    DEFAULT(MaterialColor.WHITE);

    MaterialColor color;

    MaterialTheme(MaterialColor color) {
        this.color = color;
    }

    public MaterialColor getColor() {
        return color;
    }
}
