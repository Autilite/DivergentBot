package command;

import database.CarryController;
import main.Main;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import static main.Utils.stripId;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CarryMe extends AbstractCommand {
    @Override
    public String getName() {
        return "carryme";
    }

    @Override
    public String getDescription() {
        return "Request a carry from a user";
    }

    @Override
    public String[] getUsage() {
        return new String[0];
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        if (!(args.length == 2) && !(args.length == 3))
            return;
        String target = stripId(args[0]);
        String boss = args[1];
        int amount = 1;
        if (args.length == 3) {
            amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                ch.sendMessage(user.getAsMention() + "\nYou cannot select a non-positive amount of carries :que:")
                        .queue();
            }
        }
        String author = stripId(user.getId());
        System.out.println("Add request to: " + target + ":" + author + ":" + boss + ":" + amount);
        if (author.equals(target)) {
            // TODO custom emoji
            ch.sendMessage(user.getAsMention()+ "\nYou cannot add yourself to your own carry list :que:").queue();
            return;
        }
        // TODO check if target is a user in the guild
        CarryController.requestCarry(author, target, boss, amount);
        ch.sendMessage(user.getName() + " has requested " + amount + " " + boss + " carry run(s) from "
                + Main.jda.getUserById(target).getAsMention()).queue();
    }
}
