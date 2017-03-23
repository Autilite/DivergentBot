package main;

import database.CarryModel;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class Utils {
    public static String stripId(String arg) {
        return arg.replaceAll("[<@>]", "");
    }

    public static String carryModelLeecherToString(CarryModel model) {
        String s = Main.jda.getUserById(model.getLeecherId()).getName() + ": " + model.getBoss();
        if (model.getNumCarries() > 1) {
            s += " (" + model.getNumCarries() + ")";
        }
        return s;
    }
}
