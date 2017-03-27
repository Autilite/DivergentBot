package command;

import database.CarryController;
import database.CarryModel;
import javafx.util.Pair;
import main.Main;
import main.Utils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.*;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CarryList extends AbstractCommand {
    @Override
    public String getName() {
        return "carrylist";
    }

    @Override
    public String getDescription() {
        return "Show the list of people you will carry (and their respective boss(es))";
    }

    @Override
    public String[] getUsage() {
        return new String[]{
                getName() + " [@target]"
        };
    }

    @Override
    public void execute(String[] args, MessageChannel ch, User user) {
        if (!(args.length == 0) && !(args.length == 1)) {
            ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
            return;
        }
        StringBuilder response = new StringBuilder();

        // Sanitize input
        String target = user.getId();   // use current user if none is specified
        if (args.length == 1) {
            target = Utils.stripId(args[0]);
            if (!Main.isGuildMember(target)){
                ch.sendMessage("Usage: " + Utils.usageToString(getUsage())).queue();
                return;
            }
        }

        Set<String> carrylist = CarryController.getCarryList(target);
        if (carrylist.size() == 0) {
            response.append("\nEmpty carry list");
        } else {
            HashMap<String, Set<Pair<String, Integer>>> carries = new HashMap<>();
            carrylist.forEach(s -> {
                        // Grab the value from the key and store it in a hash table
                        // Leecher -> [{Boss, Amount}....]
                        CarryModel model = CarryController.getValue(s);
                        String leecher = model.getLeecherId();
                        Pair<String, Integer> boss = new Pair<>(model.getBoss(), model.getNumCarries());
                        Set<Pair<String, Integer>> set = carries.get(model.getLeecherId());
                        if (set == null)
                            set = new HashSet<>();
                        set.add(boss);
                        carries.put(leecher, set);
                    }
            );
            carries.forEach((leecher, bossPair) -> {
                // Aggregate the hash table into a single line for each leecher
                // Leecher: boss (amount), boss (amount), ...
                response.append("\n• ").append(Main.jda.getUserById(leecher).getName()).append(":");
                Iterator<Pair<String, Integer>> it = bossPair.iterator();
                response.append(Utils.bossPairToString(it.next()));
                while(it.hasNext()) {
                    response.append(",");
                    response.append(Utils.bossPairToString(it.next()));
                };
            });
        }
        EmbedBuilder b = new EmbedBuilder();
        b.setTitle("Carry List for " + Main.jda.getUserById(target).getName(), null);
        b.setDescription(response.toString());
        b.setThumbnail("http://vignette3.wikia.nocookie.net/maplestory/images/2/25/Item_Entrance_Ticket.png/revision/latest?cb=20091226110320");
        b.setColor(new Color(51,255,51));
        ch.sendMessage(b.build()).queue();
    }

}
