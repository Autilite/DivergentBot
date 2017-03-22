import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * Created by Kelvin on 21/03/2017.
 */
public class MessageListener extends ListenerAdapter {

    private CommandHandler handler;

    public MessageListener(CommandHandler handler) {
        this.handler = handler;
    }

    public void onMessageReceived(MessageReceivedEvent event) {
        handler.process(event);
    }
}
