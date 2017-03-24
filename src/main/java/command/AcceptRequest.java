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
public class AcceptRequest extends AbstractCommand {
    @Override
    public String getName() {
        return "nicecolee";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String[] getUsage() {
        return new String[0];
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        // @FluffyBot nicecolee @target boss [amount]
        if (!(args.length == 2) && !(args.length == 3))
            // TODO send usage command
            return;
        // Parse arguments
        String target = Utils.stripId(args[0]);
        String boss = args[1];
        int amount = 1;
        if (args.length == 3) {
            try {
                // and validate arguments
                amount = Integer.parseInt(args[2]);
                if (amount > 0) {
                    CarryController.acceptCarry(user.getId(), target, boss, amount);
                }
            } catch (NumberFormatException e){
                ch.sendMessage("Invalid argument").queue();
                return;
            } catch (NonexistingCarryException e) {
                ch.sendMessage(user.getAsMention() + "\nThis request was not in your request list").queue();
                return;
            }
        } else {
            // accept all carries for this boss
            try {
                CarryController.acceptCarry(user.getId(), target, boss);
            } catch (NonexistingCarryException e) {
                // value for this key did not exist so there were no carries to accept
                ch.sendMessage(user.getAsMention() + "\nThis request was not in your request list").queue();
                return;
            }
        }
        ch.sendMessage(user.getAsMention() + " has accepted " + Main.jda.getUserById(target).getAsMention()
                + "'s request for carrying " + boss).queue();
    }
}
