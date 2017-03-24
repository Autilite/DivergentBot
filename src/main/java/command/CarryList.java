package command;

import database.CarryController;
import database.CarryModel;
import main.Main;
import main.Utils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.Set;

import static main.Utils.carryModelLeecherToString;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CarryList extends AbstractCommand {
    @Override
    public String getName() {
        return "carrylist";
    }

    @Override
    public String getDescription() {
        return "Show the list of people you will carry (and their respective boss(es))";
    }

    @Override
    public String[] getUsage() {
        return new String[0];
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        StringBuilder response = new StringBuilder();
        String target = user.getId();

        for (int i = 0; i < args.length; i++) {
            System.out.println("Args[" + i + "] = " + args[0]);
        }

        if (args.length >= 1) {
            target = Utils.stripId(args[0]);
            if (target == null) {
                target = user.getId();
            }
        }
        System.out.println("target " + target);
        response.append(Main.jda.getUserById(target).getName()).append(" is currently carrying the following users");

        Set<String> carrylist = CarryController.getCarryList(target);
        if (carrylist.size() == 0) {
            response.append("\nEmpty carry list");
        } else {
            //response.append("\n").append(s)
            carrylist.forEach(s -> {
                       CarryModel model = CarryController.getValue(s);
                       response.append("\n ").append(carryModelLeecherToString(model));
                    }
            );
        }
        ch.sendMessage(user.getAsMention() + "\n" + response.toString()).queue();
    }


}
