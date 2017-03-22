import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Kelvin on 21/03/2017.
 */
public class MessageListener extends ListenerAdapter {
    public void onMessageReceived(MessageReceivedEvent event)
    {
        User author = event.getAuthor();
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String msg = message.getContent();

        if (msg.startsWith("!help")) {
            channel.sendMessage(author.getAsMention() + " please, How can I Help you if I can't even solo chaos vellum yet?")
                    .queue();
        }

    }
}
