package command;

import database.CarryController;
import exception.NonexistingCarryException;
import main.Main;
import main.Utils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Kelvin on 23/03/2017.
 */
public class DenyRequest extends AbstractCommand {
    @Override
    public String getName() {
        return "evilcolee";
    }

    @Override
    public String getDescription() {
        return "Reject a carry request from your request list.";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName() + " @target [boss]"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        // @FluffyBot evilcolee @target [boss]
        if (!(args.length == 1) && !(args.length == 2)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        // Parse arguments
        String target = Utils.stripId(args[0]);
        if (args.length == 1) {
            // delete all requests from target
            CarryController.denyCarry(user.getId(), target);
            ch.sendMessage(user.getAsMention() + "\nRejected all boss requests from "
                    + Main.jda.getUserById(target).getAsMention()).queue();
        } else {
            // delete all requests from target for the given boss
            String boss = args[1];
            try {
                CarryController.denyCarry(user.getId(), target, boss);
                ch.sendMessage(user.getAsMention() + "\nRejected `" + boss +"` requests from "
                        + Main.jda.getUserById(target).getAsMention()).queue();
            } catch (NonexistingCarryException e) {
                ch.sendMessage(user.getAsMention() + "\nYou have no `" + boss + "` request from "
                        + Main.jda.getUserById(target).getName()).queue();
            }
        }
    }
}
