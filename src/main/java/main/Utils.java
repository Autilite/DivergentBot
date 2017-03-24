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

    public static String usageToString(String[] usage) {
        if (usage.length == 0)
            return "";
        StringBuilder builder = new StringBuilder(usage[0]);
        for (int i = 1; i < usage.length; i++) {
            builder.append(" ").append(usage[i]);
        }
        return builder.toString();
    }

}
