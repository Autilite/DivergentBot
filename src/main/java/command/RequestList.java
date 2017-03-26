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
public class RequestList extends AbstractCommand {
    @Override
    public String getName() {
        return "requestlist";
    }

    @Override
    public String getDescription() {
        return "Shows a list of users who requested a carry from me";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName() + " [@target]"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        if (!(args.length == 0) && !(args.length == 1)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        StringBuilder response = new StringBuilder();

        // Sanitize input
        String target = user.getId();   // use current user if none is specified
        if (args.length == 1) {
            target = Utils.stripId(args[0]);
            if (!Main.isGuildMember(target)){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
        }
        System.out.println("target " + target);
        response.append(Main.jda.getUserById(target).getName()).append(" has been requested to carry the following:");

        Set<String> carrylist = CarryController.getRequestList(target);
        if (carrylist.size() == 0) {
            response.append("\nEmpty carry list");
        } else {
            carrylist.forEach(s -> {
                        CarryModel model = CarryController.getValue(s);
                        response.append("\n ").append(carryModelLeecherToString(model));
                    }
            );
        }
        ch.sendMessage(user.getAsMention() + "\n" + response.toString()).queue();
    }

}
