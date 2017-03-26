package main;

/**
 * Created by Kelvin on 25/03/2017.
 */
public enum Bosses {
    CZAK,
    CHT,
    HMAG,
    HHILLA,
    CQUEEN,
    CPIERRE,
    CVB,
    CVELL,
    CPB,
    NCYG,
    LOTUS,
    DAMIEN,
    HARDLLUX,
    HELLUX,
    MADMAN;

    public static boolean isBoss(String boss) {
        String b = boss.trim().toUpperCase();
        for (Bosses aBoss : Bosses.values()) {
            if (aBoss.name().equals(b)){
                return true;
            }
        }
        return false;
    }
}
