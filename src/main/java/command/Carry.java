package command;

import database.CarryController;
import main.Main;
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
        return "Add a person to your carry list";
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
        System.out.println("args length");
        System.out.println(args.length);
        if (args.length == 3) {
            amount = Integer.parseInt(args[2]);
            if (amount <= 0) {
                ch.sendMessage(user.getAsMention() + "You cannot provide a negative amount of carries").queue();
            }
        }
        String author = stripId(user.getId());
        System.out.println("Add carry to: " + author + ":" + target + ":" + boss + ":" + amount);
        if (author.equals(target)) {
            // TODO custom emoji
            ch.sendMessage(user.getAsMention()+ "\nYou cannot add yourself to your own carry list :que:").queue();
            return;
        }
        CarryController.addCarry(author, target, boss, amount);
        ch.sendMessage(user.getName() + " has provided " + amount + " " + boss + " carry run(s) "
        + Main.jda.getUserById(target).getAsMention()).queue();
    }

}
