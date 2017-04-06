package main;

import handler.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import redis.clients.jedis.Jedis;

import javax.security.auth.login.LoginException;

/**
 * Created by Kelvin on 21/03/2017.
 */
public class Main {

    public static JDA jda;
    public static Jedis jedis;
    public static void main (String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(Config.BOT_TOKEN).buildBlocking();
            CommandHandler handler = new CommandHandler();
            jda.addEventListener(new MessageListener(handler));
            if (Config.STATUS_MESSAGE.isEmpty()) {
                jda.getPresence().setGame(null);
            } else {
                jda.getPresence().setGame(Game.of(Config.STATUS_MESSAGE));
            }
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
        jedis = new Jedis(Config.REDIS_HOST);
    }

    public static String getBotAsMention () {
        return jda.getSelfUser().getAsMention();
    }

    public static boolean isGuildMember(String userId) {
        return jda.getUsers().contains(jda.getUserById(userId));
    }
}
