package command;

import database.CarryController;
import main.Bosses;
import main.Main;
import main.Utils;
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
        // Sanitize inputs
        String target = stripId(args[0]);
        String boss = args[1];
        int amount = 1;
        if (!Main.isGuildMember(target) || !Bosses.isBoss(boss)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        if (args.length == 3) {
            try {
                amount = Integer.parseInt(args[2]);
            } catch (NumberFormatException e){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
            if (amount <= 0) {
                ch.sendMessage(user.getAsMention() + "\nYou cannot select a non-positive amount of carries :que:")
                        .queue();
                return;
            }
        }

        String author = stripId(user.getId());
        System.out.println("Add request to: " + target + ":" + author + ":" + boss + ":" + amount);
        if (author.equals(target)) {
            // TODO custom emoji
            ch.sendMessage(user.getAsMention()+ "\nYou cannot add yourself to your own carry list :que:").queue();
            return;
        }
        CarryController.requestCarry(author, target, boss, amount);
        ch.sendMessage(user.getName() + " has requested " + amount + " " + boss + " carry run(s) from "
                + Main.jda.getUserById(target).getAsMention()).queue();
    }
}
