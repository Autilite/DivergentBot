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
public class AcceptRequest extends AbstractCommand {
    @Override
    public String getName() {
        return "acceptrequest";
    }

    @Override
    public String getDescription() {
        return "Accept a carry request from your request list.";
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
        String target = Utils.stripId(args[0]);
        String boss = args[1];
        int amount = 1;

        if (!Main.isGuildMember(target) || !Bosses.isBoss(boss)){
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }

        if (args.length == 3) {
            // and amount argument
            try {
                amount = Integer.parseInt(args[2]);
                if (amount <= 0) {
                    ch.sendMessage(user.getAsMention() + "\nYou cannot select a non-positive amount of carries :que:")
                            .queue();
                    return;
                }
            } catch (NumberFormatException e){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
            // accept those carries
            try {
                CarryController.acceptCarry(user.getId(), target, boss, amount);
            } catch (NonexistentCarryException e) {
                ch.sendMessage(user.getAsMention() + "\nThis request was not in your request list").queue();
                return;
            }
        } else {
            // if no amount specified, accept all carries for this boss
            try {
                CarryController.acceptCarry(user.getId(), target, boss);
            } catch (NonexistentCarryException e) {
                // value for this key did not exist so there were no carries to accept
                ch.sendMessage(user.getAsMention() + "\nThis request was not in your request list").queue();
                return;
            }
        }
        ch.sendMessage(user.getAsMention() + " has accepted " + Main.jda.getUserById(target).getAsMention()
                + "'s request for carrying " + boss).queue();
    }
}
