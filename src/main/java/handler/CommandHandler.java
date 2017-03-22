package handler;

import command.AbstractCommand;
import main.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List<String> args = splitSpaceExceptQuotes(msg);
        for (int i = 0; i < args.size(); i++) {
            System.out.println("Arg[" + i + "]: " + "\"" + args.get(i) + "\"");
        }

        // Check if the message is a command:
        //  1) Uses correct prefix
        //  2) Command contains more than just the prefix
        //  3) First argument is a valid command name
        if (!args.get(0).equals(Main.getBotAsMention()) || args.size() == 1)
            return;
        String command = args.get(1);
        System.out.println("command: " + command);
        AbstractCommand cmd = commands.get(command);
        if (cmd == null) {
            channel.sendMessage("Invalid command. Please enter \""
                    + Main.getBotAsMention()
                    + " help\" for a list of commands.").queue();
            return;
        }

        // Execute the command with the given arguments
        args.remove(0); // Remove prefix
        args.remove(0); // and commend
        String[] cmdArgs = args.toArray(new String[0]);
        System.out.println("Execute command: " + "\"" + cmd.getName() + "\"");
        cmd.execute(cmdArgs, channel, author);
    }

    private List<String> splitSpaceExceptQuotes(String string) {
        // https://stackoverflow.com/questions/7804335/split-string-on-spaces-in-java-except-if-between-quotes-i-e-treat-hello-wor
        LinkedList<String> list = new LinkedList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(string);
        while (m.find()) {
            list.add(m.group(1).replace("\"", ""));
        }
        return list;
    }
}
