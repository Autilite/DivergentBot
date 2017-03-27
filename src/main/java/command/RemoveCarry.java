package command;

import database.CarryController;
import exception.NonexistentCarryException;
import main.Bosses;
import main.Main;
import main.Utils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Kelvin on 27/03/2017.
 */
public class RemoveCarry extends AbstractCommand {
    @Override
    public String getName() {
        return "removecarry";
    }

    @Override
    public String getDescription() {
        return "Remove a carry from your carry list";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName() + " @target boss [amount]"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        if (!(args.length == 2) && !(args.length == 3)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        // Parse arguments
        // Don't need to check if the target is a member of the guild
        // This is to ensure the user is still able to remove a request
        // even if the requester has quit the guild
        String target = Utils.stripId(args[0]);
        String boss = args[1].toUpperCase();
        int amount;

        if (args.length == 2) {
            // delete all requests from target for the given boss
            if (!Bosses.isBoss(boss)){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
            try {
                int removed = CarryController.removeCarry(user.getId(), target, boss);
                ch.sendMessage(user.getAsMention() + " has removed `" + removed + "` of "
                        +  Main.jda.getUserById(target).getAsMention() +  "'s carries for `" + boss +"`").queue();
            } catch (NonexistentCarryException e) {
                ch.sendMessage(user.getAsMention() + ", you have no `" + boss + "` carry for "
                        + Main.jda.getUserById(target).getName()).queue();
            }
        } else {
            // delete the given amount of boss runs provided by the user
            try {
                amount = Integer.parseInt(args[2]);
                if (amount <= 0) {
                    ch.sendMessage(user.getAsMention() + "\nYou cannot select a non-positive amount of carries")
                            .queue();
                    return;
                }
                int removed = CarryController.removeCarry(user.getId(), target, boss, amount);
                ch.sendMessage(user.getAsMention() + " has removed `" + removed + "` of "
                        +  Main.jda.getUserById(target).getAsMention() +  "'s carries for `" + boss +"`").queue();
            } catch (NumberFormatException e) {
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            } catch (NonexistentCarryException e) {
                ch.sendMessage(user.getAsMention() + ", you have no `" + boss + "` carry for "
                        + Main.jda.getUserById(target).getName()).queue();
            }
        }

    }
}
