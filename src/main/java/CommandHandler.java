import Command.AbstractCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CommandHandler {
    private HashMap<String, AbstractCommand> commands = new HashMap<>();

    public CommandHandler() {
        // Load commands into hash map
        Reflections reflections = new Reflections("Command");
        Set<Class<? extends AbstractCommand>> abstractCommands = reflections.getSubTypesOf(AbstractCommand.class);
        for (Class<? extends AbstractCommand> c : abstractCommands) {
            try {
                AbstractCommand cmd = c.getConstructor().newInstance();
                String name = cmd.getName();
                if (!commands.containsKey(name)) {
                    commands.put(name, cmd);
                }
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        System.out.println("List of current commands:");
        commands.forEach((k,v) -> System.out.println(k));
    }

    public void process(MessageReceivedEvent event) {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String msg = message.getContent();

        // Don't process message if the author is a bot
        if (author.isBot())
            return;

        // Split message into string array to check if it's a command
        String[] args = msg.split("\\s+");
        for (int i = 0; i < args.length; i++) {
            System.out.println("Arg[" + i + "]: " + "\"" + args[i] + "\"");
        }

        // Check if the message is a command: uses correct prefix and is has valid command name
        if (!args[0].startsWith(Config.COMMAND_PREFIX))
            return;
        String command = args[0].substring(1);
        System.out.println("Command: " + command);
        AbstractCommand cmd = commands.get(command);
        if (cmd == null) {
            System.out.println("Invalid command");
            return;
        }

        // Execute the command
        String[] cmdArgs = Arrays.copyOfRange(args, 1, args.length);
        System.out.println("Execute command: " + "\"" + cmd.getName() + "\"");
        cmd.execute(cmdArgs, channel, author);
    }
}
