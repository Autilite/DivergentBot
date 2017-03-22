package command;

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
        return "Displays a list of commands";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                "help"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        ch.sendMessage(user.getAsMention() + " please, how can I help you if I can't even solo chaos vellum?")
                .queue();
    }
}
