package db;

public class Account {

    private String username;
    private String mail;
    private double point;

    /**
     * Costructer of Account creates account.
     * @param username username
     * @param mail mail
     * @param points points
     */
    public Account(String username, String mail, double points) {
        this.username = username;
        this.mail = mail;
        this.point = points;
    }

    public String getUsername() {
        return username;
    }

    public String getMail() {
        return mail;
    }

    public double getPoint() {
        return point;
    }

}
