package database;

import main.Config;
import main.Main;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.*;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CarryController {
    /**
     * Returns a set of keys that {@code atkerId} will be carrying
     *
     * @param atkerId The id of the person doing the carry
     * @return Set of keys
     */
    public static Set<String> getCarryList(String atkerId) {
        String key = getRedisKey(Config.REDIS_CARRY_KEYWORD, atkerId);
        String keypPattern = key + Config.REDIS_KEY_SEPARATOR + Config.REDIS_WILDCARD;
        System.out.println(keypPattern);
        Set<String> list = Main.jedis.keys(keypPattern);
        System.out.println(list.toString());
        return list;
    }

    /**
     * Returns the set of keys of people requesting a carry from {@code atkerId}
     *
     * @param atkerId The id of the person doing the carry
     * @return Keys of everyone requesting a boss carry. Key points to the value for the number of carries for the
     *   particular boss.
     */
    public static Set<String> getRequestList(String atkerId) {
        String key = getRedisKey(Config.REDIS_REQUEST_KEYWORD, atkerId);
        String keypPattern = key + Config.REDIS_KEY_SEPARATOR + Config.REDIS_WILDCARD;
        System.out.println(keypPattern);
        Set<String> results = Main.jedis.keys(keypPattern);
        System.out.println(results.toString());

        return results;
    }

    public static void requestCarry(String requesterId, String atkerId, String boss, int amount) {
        String atkerRequestKey = getRedisKey(Config.REDIS_REQUEST_KEYWORD, atkerId, requesterId, boss);
        System.out.println("REDIS KEY:");
        System.out.println(atkerRequestKey);
        Main.jedis.incrBy(atkerRequestKey, amount);
    }

    public static void acceptCarry(String atkerId, String requesterId, String boss) {
        String atkerRequestKey = getRedisKey(Config.REDIS_REQUEST_KEYWORD, atkerId, requesterId, boss);
        String atkerCarryKey = getRedisKey(Config.REDIS_CARRY_KEYWORD, atkerId, requesterId, boss);

        Main.jedis.watch(atkerRequestKey);
        Transaction t = Main.jedis.multi();
        Response<String> amount = t.get(atkerRequestKey);
        t.del(atkerRequestKey);
        t.exec();

        int amt = Integer.parseInt(amount.get());
        Main.jedis.incrBy(atkerCarryKey, amt);
    }

    public static void addCarry(String atkerId, String requesterId, String boss, int amount) {
        String atkerCarryKey = getRedisKey(Config.REDIS_CARRY_KEYWORD, atkerId, requesterId, boss);
        System.out.println("REDIS KEY:");
        System.out.println(atkerCarryKey);
        Main.jedis.incrBy(atkerCarryKey, amount);
    }

    public static String getKey(String key) {
        return Main.jedis.get(key);
    }

    /**
     * Returns a CarryModel containing the data at the key. Example carry:202394082340:12831903821031:hilla
     * @param key A carry model key
     * @return
     */
    public static CarryModel getValue(String key) {
        int numCarries = Integer.parseInt(getKey(key));
        CarryModel model = new CarryModel();
        String[] s = key.split(Config.REDIS_KEY_SEPARATOR);
        if (s.length > 0)
            model.setType(s[0]);
        if (s.length > 1)
            model.setAtkerId(s[1]);
        if(s.length > 2)
            model.setLeecherId(s[2]);
        if(s.length > 3)
            model.setBoss(s[3]);
        model.setNumCarries(numCarries);
        return model;
    }

    private static String getRedisKey(String keyword, String atkerId, String requesterId, String boss) {
        String key = keyword + Config.REDIS_KEY_SEPARATOR
                + atkerId + Config.REDIS_KEY_SEPARATOR
                + requesterId + Config.REDIS_KEY_SEPARATOR
                + boss;
        return key;
    }

    private static String getRedisKey(String keyword, String atkerId, String requesterId) {
        String key = keyword + Config.REDIS_KEY_SEPARATOR
                + atkerId + Config.REDIS_KEY_SEPARATOR
                + requesterId;
        return key;
    }


    private static String getRedisKey(String keyword, String atkerId) {
        String key = keyword + Config.REDIS_KEY_SEPARATOR + atkerId;
        return key;
    }
}
