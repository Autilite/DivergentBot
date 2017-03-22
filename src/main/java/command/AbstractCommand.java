package command;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Kelvin on 21/03/2017.
 */
public abstract class AbstractCommand {
    public AbstractCommand() {
    }

    /**
     * Get the command name that is used to execute the command
     *
     * @return
     */
    public abstract String getName();

    public abstract String getDescription();

    public abstract String[] getUsage();

    public abstract void execute(String[] args, MessageChannel ch, User user);
}
