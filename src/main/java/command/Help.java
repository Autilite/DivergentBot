package command;

import handler.CommandHandler;
import main.Utils;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Kelvin on 21/03/2017.
 */
public class Help extends AbstractCommand {
    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Displays a list of available commands";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName()
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
//        ch.sendMessage(user.getAsMention() + " please, how can I help you if I can't even solo chaos vellum?")
//                .queue();
        StringBuilder help = new StringBuilder();
        help.append("```fix\n");
        help.append("List of Commands:\n");
        CommandHandler.getCommands().forEach(abstractCommand -> {
            help.append("\n  ");
            help.append(String.format("%-50s", Utils.usageToString(abstractCommand.getUsage())));
            help.append(abstractCommand.getDescription());
        });
        help.append("```");
        ch.sendMessage(help.toString()).queue();
    }
}
