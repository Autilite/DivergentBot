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
public class Carry extends AbstractCommand {
    @Override
    public String getName() {
        return "carry";
    }

    @Override
    public String getDescription() {
        return "Add a user to your carry list";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName() + " @target boss [amount]"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        // Check input
        if (!(args.length == 2) && !(args.length == 3)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        // Parse input
        String target = stripId(args[0]);
        String boss = args[1].toUpperCase();
        int amount = 1;

        if (!Main.isGuildMember(target) || !Bosses.isBoss(boss)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }

        if (args.length == 3) {
            try {
                amount = Integer.parseInt(args[2]);
                if (amount <= 0) {
                    ch.sendMessage(user.getAsMention() + "\nYou cannot select a non-positive amount of carries")
                            .queue();
                    return;
                }
            } catch (NumberFormatException e) {
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
        }

        String author = stripId(user.getId());
        System.out.println("Add carry to: " + author + ":" + target + ":" + boss + ":" + amount);
        if (author.equals(target)) {
            ch.sendMessage(user.getAsMention()+ "\nYou cannot add yourself to your own carry list").queue();
            return;
        }

        // Add target to user's carry list
        CarryController.addCarry(author, target, boss, amount);
        ch.sendMessage(user.getName() + " has provided " + amount + " `" + boss + "` <:entrance_ticket:294022828725108736> for "
        + Main.jda.getUserById(target).getAsMention()).queue();
    }

}
