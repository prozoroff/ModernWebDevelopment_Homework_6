package streams.auction;

/**
 * Created by yfain11 on 4/4/14.
 */
public class User {
    public int id;
    public String name;
    public String email;
    public boolean getOverbidNotifications;

    public User(int id, String name, String email, boolean getOverbidNotifications)
    {
        this.id = id;
        this.name = name;
        this.email = email;
        this.getOverbidNotifications = getOverbidNotifications;
    }

}
