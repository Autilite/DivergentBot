package main;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class Utils {
    public static String stripId(String arg) {
        return arg.replaceAll("[<@>]", "");
    }

}
