package database;

/**
 * Created by Kelvin on 22/03/2017.
 */
public class CarryModel {
    private String type;
    private String atkerId;
    private String leecherId;
    private String boss;
    private int numCarries;

    public CarryModel(String type, String atkerId, String leecherId, String boss, int numCarries) {
        this.type = type;
        this.atkerId = atkerId;
        this.leecherId = leecherId;
        this.boss = boss;
        this.numCarries = numCarries;
    }

    public CarryModel() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAtkerId() {
        return atkerId;
    }

    public void setAtkerId(String atkerId) {
        this.atkerId = atkerId;
    }

    public String getLeecherId() {
        return leecherId;
    }

    public void setLeecherId(String leecherId) {
        this.leecherId = leecherId;
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }

    public int getNumCarries() {
        return numCarries;
    }

    public void setNumCarries(int numCarries) {
        this.numCarries = numCarries;
    }

}
