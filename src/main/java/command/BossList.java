package command;

import main.Bosses;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Created by Kelvin on 26/03/2017.
 */
public class BossList extends AbstractCommand {
    @Override
    public String getName() {
        return "bosslist";
    }

    @Override
    public String getDescription() {
        return "Displays a full list of available bosses for carry";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName()
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        String bosses = bossesToString();
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Boss List", null);
        b.setDescription(bosses);
        ch.sendMessage(b.build()).queue();
    }

    private String bossesToString() {
        Bosses[] bosses = Bosses.values();
        if (bosses.length == 0)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(bosses[i]));
            if (i == (bosses.length - 1))
                return b.toString();
            b.append("\n");
        }
    }

}
