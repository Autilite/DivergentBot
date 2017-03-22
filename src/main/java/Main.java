import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;

/**
 * Created by Kelvin on 21/03/2017.
 */
public class Main {

    public static JDA jda;
    public static void main (String[] args) {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(Config.BOT_TOKEN).buildBlocking();
            jda.addEventListener(new MessageListener());
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        }
    }
}
