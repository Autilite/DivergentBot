package handler;

import command.AbstractCommand;
import main.Main;
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
        Reflections reflections = new Reflections("command");
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
        String msg = message.getRawContent();

        // Don't process message if the author is a bot
        if (author.isBot())
            return;

        // Split message into string array to check if it's a command
        String[] args = msg.split("\\s+");
        for (int i = 0; i < args.length; i++) {
            System.out.println("Arg[" + i + "]: " + "\"" + args[i] + "\"");
        }

        // Check if the message is a command:
        //  1) Uses correct prefix
        //  2) Prefix is followed by a command
        //  3) First argument is a valid command name
        if (!args[0].equals(Main.getBotAsMention()) || args.length == 1)
            return;
        String command = args[1];
        System.out.println("command: " + command);
        AbstractCommand cmd = commands.get(command);
        if (cmd == null) {
            channel.sendMessage("Invalid command. Please enter \""
                    + Main.getBotAsMention()
                    + " help\" for a list of commands.").queue();
            return;
        }

        // Execute the command with the given arguments
        String[] cmdArgs = Arrays.copyOfRange(args, 2, args.length);
        System.out.println("Execute command: " + "\"" + cmd.getName() + "\"");
        cmd.execute(cmdArgs, channel, author);
    }
}
