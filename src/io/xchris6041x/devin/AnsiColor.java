package io.xchris6041x.devin;

@SuppressWarnings("ALL")
public final class AnsiColor {

    // Reset
    public static final String RESET = "\u001B[0m";

    // Colors
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[1;31m";
    public static final String GREEN = "\u001B[1;32m";
    public static final String YELLOW = "\u001B[1;33m";
    public static final String BLUE = "\u001B[1;34m";
    public static final String PURPLE = "\u001B[1;35m";
    public static final String CYAN = "\u001B[1;36m";
    public static final String WHITE = "\u001B[1;37m";

    // Dark Colors
    public static final String DARK_RED = "\u001B[31m";
    public static final String DARK_GREEN = "\u001B[32m";
    public static final String DARK_YELLOW = "\u001B[33m";
    public static final String DARK_BLUE = "\u001B[34m";
    public static final String DARK_PURPLE = "\u001B[35m";
    public static final String DARK_CYAN = "\u001B[36m";
    public static final String GRAY = "\u001B[37m";

    private AnsiColor() {
    }

}
