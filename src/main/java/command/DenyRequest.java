package command;

import database.CarryController;
import exception.NonexistentCarryException;
import main.Bosses;
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
        return "deny";
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
        // @Divergent deny @target [boss]
        if (!(args.length == 1) && !(args.length == 2)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        // Parse arguments
        // Don't need to check if the target is a member of the guild
        // This is to ensure the user is still able to remove a request
        // even if the requester has quit the guild
        String target = Utils.stripId(args[0]);
        String boss;

        if (args.length == 1) {
            // no boss specified so delete all requests from target
            CarryController.denyCarry(user.getId(), target);
            ch.sendMessage(user.getAsMention() + " has rejected all boss requests from "
                    + Main.jda.getUserById(target).getAsMention()).queue();
        } else {
            // delete all requests from target for the given boss
            boss = args[1].toUpperCase();
            if (!Bosses.isBoss(boss)){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
            try {
                CarryController.denyCarry(user.getId(), target, boss);
                ch.sendMessage(user.getAsMention() + " has rejected " + Main.jda.getUserById(target).getAsMention()
                        +  "'s request for `" + boss +"`").queue();
            } catch (NonexistentCarryException e) {
                ch.sendMessage(user.getAsMention() + ", you have no `" + boss + "` request from "
                        + Main.jda.getUserById(target).getName()).queue();
            }
        }
    }
}
