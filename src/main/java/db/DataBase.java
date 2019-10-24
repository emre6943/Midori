
package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




/**
 * DataBase Class with all the methods that infuluance database.
 *
 * @see DataBase
 */
public class DataBase {

    // JDBC driver name and database URL
    static final String dbDriver = "com.mysql.jdbc.Driver";
    static final String dbUrl = "jdbc:mysql://localhost:4444/USERS?autoReconnect=true&useSSL=false";

    //  Database credentials
    static final String user = "root";
    static final String pass = "root";

    /**
     * main Just for testing new stuff.
     *
     * @param args automated start
     * @throws Exception mostly sql exception
     */
    public static void main(String[] args) throws Exception {
        //At the beginning run this 1 time
        DataBase.createDb("USERS");
    }

    /**
     * gets the top 10 people that has the highest ranks.
     * @param conn coinnection to db
     * @return returns a result set containg the list
     * @throws SQLException something went wrong in db
     */
    public static ResultSet leaderBoard(Connection conn) throws SQLException {
        String sql = "SELECT ACCOUNTS.user_name ,QUESTIONS.mail "
            + ", QUESTIONS.points FROM QUESTIONS, ACCOUNTS "
            + "WHERE ACCOUNTS.mail = QUESTIONS.mail  ORDER BY points DESC";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }

    /**
     * delets the friendship.
     *
     * @param mailFrom mail that sent
     * @param mailto   mail that received
     * @param conn     connection to db
     * @return return true if it is executed
     * @throws SQLException something went wrong in db
     */
    public static boolean deleteFriend(String mailFrom, String mailto,
                                       Connection conn) throws SQLException {
        if (DataBase.checkMail(mailFrom, conn) && DataBase.checkMail(mailto, conn)
            && DataBase.checkRelation(mailFrom, mailto, conn)) {
            Statement st = conn.createStatement();
            String sql = "DELETE FROM USERS.FRIENDS WHERE FRIENDS.mail_from = '"
                + mailFrom + "' and FRIENDS.mail_to= '" + mailto + "'";
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }


    /**
     * changes the status of the friendship.
     *
     * @param mailFrom mail that sent
     * @param mailto   mail that received
     * @param sta      status to change
     * @param conn     connection to db
     * @return return true if it is executed
     * @throws SQLException something went wrong in db
     */
    public static boolean statusFriend(String mailFrom, String mailto,
                                       Boolean sta, Connection conn) throws SQLException {
        if (DataBase.checkMail(mailFrom, conn) && DataBase.checkMail(mailto, conn)
            && DataBase.checkRelation(mailFrom, mailto, conn)) {
            String sql = "UPDATE FRIENDS SET approved = "
                + sta + " WHERE FRIENDS.mail_from = '" + mailFrom
                + "' and FRIENDs.mail_to = '" + mailto + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * changing the loged in status.
     *
     * @param mail   mail
     * @param pass   password
     * @param status status to change
     * @param conn   Connection to db
     * @return return true if executed
     * @throws SQLException something went wrong in db
     */
    public static boolean logedStatus(String mail, String pass, boolean status,
                                      Connection conn) throws SQLException {
        if (DataBase.checkAccMail(mail, pass, conn)) {
            String sql = "UPDATE ACCOUNTS SET loged = "
                + status + " WHERE ACCOUNTS.mail = '" + mail
                + "' and ACCOUNTS.pass= '" + pass + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * adds friend relationship.
     *
     * @param mailFrom requested friendship
     * @param mailto   got friendship
     * @param conn     to db
     * @return if it is added or not
     * @throws SQLException something went wrong in db
     */
    public static boolean addFriend(String mailFrom, String mailto,
                                    Connection conn) throws SQLException {
        if (DataBase.checkMail(mailFrom, conn) && DataBase.checkMail(mailto, conn)
            && !DataBase.checkRelation(mailFrom, mailto, conn)) {
            String sql = "INSERT INTO USERS.FRIENDS VALUES ( '" + mailFrom + "' ,"
                + " '" + mailto + "' , false)";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }


    /**
     * gets frieds points in String.
     *
     * @param mail that has friends
     * @param conn connection to db
     * @return resultset of friends
     * @throws SQLException something went wrong in db
     */
    public static String getFriendsPoints(String mail, Boolean sta, Connection conn)
        throws SQLException {
        String sql = "SELECT mail_to FROM USERS.FRIENDS WHERE mail_from = '"
            + mail + "' and approved = " + sta;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String list = "";
        String fmail = "";
        while (rs.next()) {
            fmail = rs.getString("mail_to");
            list = list + DataBase.getUserName(fmail,conn)  + " " + fmail + " "
                + DataBase.getPoints(fmail, conn) + ", ";
        }
        return list;
    }

    /**
     * Gets Friends.
     *
     * @param mail mail that has friends
     * @param sta  if the friends approved or not
     * @param conn Connection  to db
     * @return resultset of the friends
     * @throws SQLException something went wrong in db
     */
    public static ResultSet getFriends(String mail, Boolean sta, Connection conn)
        throws SQLException {
        String sql = "SELECT * FROM USERS.FRIENDS WHERE mail_from = '"
            + mail + "' and approved = " + sta;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }

    /**
     * gives the friends to approve.
     * @param mail mail that send the req
     * @param sta false for aproving
     * @param conn connection to db
     * @return returs a result set to approve for a mail
     * @throws SQLException something went wrong in db
     */
    public static ResultSet getFriendstoApp(String mail, Boolean sta, Connection conn)
        throws SQLException {
        String sql = "SELECT * FROM USERS.FRIENDS WHERE mail_to = '"
            + mail + "' and approved = " + sta;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }

    /**
     * get the friends in string format.
     *
     * @param mail that has friends
     * @param conn connection to db
     * @return friends relation
     * @throws SQLException something went wrong in db
     */
    public static String getFriendsStr(String mail, Boolean sta, Connection conn)
        throws SQLException {
        ResultSet rs = DataBase.getFriends(mail, sta, conn);
        String ans = "";
        while (rs.next()) {
            ans = ans + rs.getString("mail_from") + " "
                + rs.getString("mail_to") + " "
                + rs.getBoolean("approved") + ", ";
        }
        return ans;
    }

    /**
     * turns the rs to string.
     * @param mail mail that send req
     * @param sta false
     * @param conn connection to db
     * @return returns a string format to go to scanner
     * @throws SQLException went wrong something in db
     */
    public static String getFriendstoApproveStr(String mail, Boolean sta, Connection conn)
        throws SQLException {
        ResultSet rs = DataBase.getFriendstoApp(mail, sta, conn);
        String ans = "";
        while (rs.next()) {
            ans = ans + rs.getString("mail_from") + " "
                + rs.getString("mail_to") + " "
                + rs.getBoolean("approved") + ", ";
        }
        return ans;
    }


    /**
     * adds points.
     *
     * @param mail points to adds mail
     * @param add  amount of points to add
     * @param conn connection to db
     * @return true if added
     * @throws SQLException something went wrong in db
     */
    public static boolean addPoints(String mail, double add, Connection conn) throws SQLException {
        double points = DataBase.getPoints(mail, conn);
        if (points != -1) {
            points = points + add;
            String sql = "UPDATE USERS.QUESTIONS SET QUESTIONS.points = " + points
                + " WHERE QUESTIONS.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * add amount to question.
     *
     * @param mail     the mail to add
     * @param question question to add amount
     * @param add      howmany do you wanna add
     * @param conn     connection to db
     * @return true if it is added
     * @throws SQLException something went wrong in db
     */
    public static boolean addToQuestion(String mail, String question, double add,
                                        Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            double amount = DataBase.getAmountQuestion(mail, question, conn);
            String sql = "UPDATE QUESTIONS SET QUESTIONS." + question
                + " = " + (amount + add) + " WHERE QUESTIONS.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * Changes a reward to whatever you give.
     * @param mail mail
     * @param reward the reward to change
     * @param sta what to change true or false
     * @param conn connection to db
     * @return returns true if executed
     * @throws SQLException something went wrong in db
     */
    public static boolean changeReward(String mail, String reward, boolean sta,
                                        Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "UPDATE REWARDS SET REWARDS." + reward
                + " = " + sta + " WHERE REWARDS.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * CHANGE REWARRD PAST.
     * @param mail mail
     * @param reward reward to check
     * @param sta what to change
     * @param conn connection to db
     * @return return true id executed
     * @throws SQLException something went wrong in db
     */
    public static boolean changeRewardPast(String mail, String reward, boolean sta,
                                       Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "UPDATE REWARDS_PAST SET REWARDS_PAST." + reward
                + " = " + sta + " WHERE REWARDS_PAST.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * Sets a new value to the question.
     * @param mail the users email
     * @param question the question you want to change
     * @param tochange the value you want to replace it with
     * @param conn connection to db
     * @return returns true if executed
     * @throws SQLException something went wrong in db
     */
    public static boolean changeQuestion(String mail, String question, double tochange,
                                       Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "UPDATE QUESTIONS SET QUESTIONS." + question
                + " = " + tochange + " WHERE QUESTIONS.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * reduces the complexity of reward updater.
     * @param mail mail
     * @param conn connection to db
     * @return returns true if something is anchived
     * @throws SQLException something went wrong in db
     */
    public static boolean rewardHelper(String mail, Connection conn) throws SQLException  {
        final double walk = DataBase.getAmountQuestion(mail , "walk" , conn);
        final double heat = DataBase.getAmountQuestion(mail , "heating" , conn);
        final double pubT = DataBase.getAmountQuestion(mail , "public_trans" , conn);
        final double solar = DataBase.getAmountQuestion(mail , "solar" , conn);
        final double donate = DataBase.getAmountQuestion(mail , "donations" , conn);

        if (walk >= 100) {
            DataBase.changeReward(mail, "walk100" , true , conn);
        }
        if (pubT >= 100) {
            DataBase.changeReward(mail, "public_trans100" , true , conn);
        }
        if (heat >= 100) {
            DataBase.changeReward(mail, "heating100" , true , conn);
        }
        if (solar >= 100) {
            DataBase.changeReward(mail, "solar100" , true , conn);
        }
        if (donate >= 100) {
            DataBase.changeReward(mail, "donate100" , true , conn);
        }
        return false;
    }

    /**
     * updates the rewards.
     * @param mail user mail to update
     * @param conn connection too db
     * @return returns true if it is checked
     * @throws SQLException something went wrong in db
     */
    public static boolean rewardUpdater(String mail,
                                       Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            double vega = DataBase.getAmountQuestion(mail , "vegan" , conn);
            double bike = DataBase.getAmountQuestion(mail , "bike" , conn);
            final double local = DataBase.getAmountQuestion(mail , "local" , conn);

            if (vega >= 10) {
                DataBase.changeReward(mail, "vegan10" , true , conn);
                if (vega >= 100) {
                    DataBase.changeReward(mail, "vegan100" , true , conn);
                }
            }
            if (local >= 10) {
                DataBase.changeReward(mail, "local10" , true , conn);
                if (local >= 100) {
                    DataBase.changeReward(mail, "local100" , true , conn);
                }
            }
            if (bike >= 100) {
                DataBase.changeReward(mail, "bike100" , true , conn);
            }
            return rewardHelper(mail, conn);
        }
        return false;
    }

    /**
     * checks if there is a recent update in rewards.
     * @param mail mail
     * @param conn connection to db
     * @return returns true if there is a recent change in rewards
     * @throws SQLException database went wrong
     */
    public static boolean recentRewardUpdate(String mail,
                                        Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            if (DataBase.getReward(mail , "vegan10" , conn)
                != db.DataBase.getRewardPast(mail , "vegan10" , conn)) {
                db.DataBase.changeRewardPast(mail, "vegan10" ,
                    db.DataBase.getReward(mail, "vegan10", conn), conn);
                return true;
            }
            if (DataBase.getReward(mail , "vegan100" , conn)
                != db.DataBase.getRewardPast(mail , "vegan100" , conn)) {
                db.DataBase.changeRewardPast(mail, "vegan100" ,
                    db.DataBase.getReward(mail, "vegan100", conn), conn);
                return true;
            }
            if (DataBase.getReward(mail , "local10" , conn)
                != db.DataBase.getRewardPast(mail , "local10" , conn)) {
                db.DataBase.changeRewardPast(mail, "local10" ,
                    db.DataBase.getReward(mail, "local10", conn), conn);
                return true;
            }
            if (DataBase.getReward(mail , "local100" , conn)
                != db.DataBase.getRewardPast(mail , "local100" , conn)) {
                db.DataBase.changeRewardPast(mail, "local100" ,
                    db.DataBase.getReward(mail, "local100", conn), conn);
                return true;
            }
            if (DataBase.getReward(mail , "bike100" , conn)
                != db.DataBase.getRewardPast(mail , "bike100" , conn)) {
                db.DataBase.changeRewardPast(mail, "bike100" ,
                    db.DataBase.getReward(mail, "bike100", conn), conn);
                return true;
            }
            return rewardUpdateHelper(mail, conn);
        }
        return false;
    }

    /**
     * Reward updater Helper.
     * @param mail mail
     * @param conn connection to db.
     * @return true if recent update
     * @throws SQLException something went wrong in db
     */
    public static boolean rewardUpdateHelper(String mail, Connection conn) throws SQLException {
        if (DataBase.getReward(mail , "walk100" , conn)
            != db.DataBase.getRewardPast(mail , "walk100" , conn)) {
            db.DataBase.changeRewardPast(mail, "walk100" ,
                db.DataBase.getReward(mail, "walk100", conn), conn);
            return true;
        }
        if (DataBase.getReward(mail , "public_trans100" , conn)
            != db.DataBase.getRewardPast(mail , "public_trans100" , conn)) {
            db.DataBase.changeRewardPast(mail, "public_trans100" ,
                db.DataBase.getReward(mail, "public_trans100", conn), conn);
            return true;
        }
        if (DataBase.getReward(mail , "heating100" , conn)
            != db.DataBase.getRewardPast(mail , "heating100" , conn)) {
            db.DataBase.changeRewardPast(mail, "heating100" ,
                db.DataBase.getReward(mail, "heating100", conn), conn);
            return true;
        }
        if (DataBase.getReward(mail , "solar100" , conn)
            != db.DataBase.getRewardPast(mail , "solar100" , conn)) {
            db.DataBase.changeRewardPast(mail, "solar100" ,
                db.DataBase.getReward(mail, "solar100", conn), conn);
            return true;
        }
        if (DataBase.getReward(mail , "donate100" , conn)
            != db.DataBase.getRewardPast(mail , "donate100" , conn)) {
            db.DataBase.changeRewardPast(mail, "donate100" ,
                db.DataBase.getReward(mail, "donate100", conn), conn);
            return true;
        }
        return false;
    }

    /**
     * gets the reward.
     * @param mail mail
     * @param reward the reward to check
     * @param conn connection to db
     * @return reward is true or false
     * @throws SQLException something went wrong in db
     */
    public static boolean getReward(String mail, String reward,
                                        Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "SELECT " + reward
                + " FROM REWARDS WHERE REWARDS.mail= '" + mail + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getBoolean(reward);
            }
        }
        return false;
    }

    /**
     * gets if the reward is achived or not.
     * @param mail mail
     * @param reward reward
     * @param conn connection to db
     * @return returns the reward achived or not
     * @throws SQLException something went wrong in db
     */
    public static boolean getRewardPast(String mail, String reward,
                                    Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "SELECT " + reward
                + " FROM REWARDS_PAST WHERE REWARDS_PAST.mail= '" + mail + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getBoolean(reward);
            }
        }
        return false;
    }

    /**
     * gets the amount in question.
     *
     * @param mail     to get the ammount
     * @param question which question you want to get the amount
     * @param conn     connection to db
     * @return the amount in the question
     * @throws SQLException something went wrong in db
     */
    public static double getAmountQuestion(String mail, String question,
                                        Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "SELECT " + question
                + " FROM QUESTIONS WHERE QUESTIONS.mail= '" + mail + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getDouble(question);
            }
        }
        return -1;
    }

    /**
     * get points.
     *
     * @param mail to add the points
     * @param conn connection to db
     * @return the new amoiunt of points
     * @throws SQLException something went wrong in db
     */
    public static double getPoints(String mail, Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "SELECT points FROM QUESTIONS WHERE QUESTIONS.mail= '" + mail + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getDouble("points");
            }
        }
        return -1;
    }

    // get username

    /**
     * get user name.
     *
     * @param mail mail to get the username
     * @param conn connection to db
     * @return the user name
     * @throws SQLException something went wrong in db
     */
    public static String getUserName(String mail, Connection conn) throws SQLException {
        if (DataBase.checkMail(mail, conn)) {
            String sql = "SELECT user_name FROM ACCOUNTS WHERE ACCOUNTS.mail = '" + mail + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                return rs.getString("user_name");
            }
        }
        return "";
    }

    /**
     * turns the result to string for accounts.
     *
     * @param rs result set that has the users
     * @return string format of the users
     * @throws SQLException something went wrong in db
     */
    public static String resultstrAcc(ResultSet rs) throws SQLException {
        String ans = "";
        while (rs.next()) {
            ans = ans + rs.getString("mail") + " "
                + rs.getString("user_name") + " "
                + rs.getString("pass") + " "
                + rs.getBoolean("loged") + "\n";
        }
        return ans;
    }

    /**
     * turns results to a meal string.
     * @param rs result set with meals
     * @return string format of meals
     * @throws SQLException if something goes wrong
     */
    public static String mealToString(ResultSet rs) throws SQLException {
        String ans = "";
        while (rs.next()) {
            ans = ans + rs.getString("meal") + " "
                    + rs.getDouble("price") + ", ";
        }
        return ans;
    }

    /**
     * gives all the acounts in result set.
     *
     * @param conn connection to db
     * @return returns the users in result set form
     * @throws SQLException something went wrong in db
     */
    public static String getAccounts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM ACCOUNTS";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return resultstrAcc(rs);
    }


    /**
     * gives the question scores NEEDS TO BE UPDATED.
     *
     * @param mail mail that we want the scores
     * @param conn connection to db
     * @return returns a result set of the scores
     * @throws SQLException something went wrong in db
     */
    public static String getQuestions(String mail, Connection conn) throws SQLException {
        String sql = "SELECT * FROM QUESTIONS WHERE mail = '" + mail + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String ans = "";
        if (rs.next()) {
            ans = "\"points\" : \""
                + rs.getDouble("points")
                + "\", \"vegan\" : \""
                + rs.getDouble("vegan")
                + "\", \"bike\" : \""
                + rs.getDouble("bike")
                + "\", \"walk\" : \""
                + rs.getDouble("walk")
                + "\", \"trans\" : \""
                + rs.getDouble("public_trans")
                + "\", \"local\" : \""
                + rs.getDouble("local")
                + "\", \"heating\" : \""
                + rs.getDouble("heating")
                + "\", \"solar\" : \""
                + rs.getDouble("solar")
                + "\", \"donations\" : \""
                + rs.getDouble("donations") + "\"";
        }
        return ans;
    }

    /**
     * For the current user returns the details regarding solar energy.
     * @param mail the users email
     * @param conn connection to the database
     * @return The information regarding solar energy
     * @throws SQLException something went wrong in db
     */
    public static String getSolars(String mail, Connection conn) throws SQLException {
        String sql = "SELECT * FROM QUESTIONS WHERE mail = '" + mail + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String ans = "";
        if (rs.next()) {
            ans = "\"points\" : \""
                + rs.getDouble("points")
                + "\", \"solarPoints\" : \""
                + rs.getDouble("solar")
                + "\", \"solarLast\" : \""
                + rs.getDouble("solarLate")
                + "\", \"solarM2\" : \""
                + rs.getDouble("solarM2")
                + "\", \"solarTime\" : \""
                + rs.getDouble("solarTime")
                + "\"";
        }
        return ans;
    }

    /**
     * For the current user returns the details regarding Heating.
     * @param mail the users email
     * @param conn connection to the database
     * @return The information regarding Heating energy
     * @throws SQLException something went wrong in db
     */
    public static String getHeats(String mail, Connection conn) throws SQLException {
        String sql = "SELECT * FROM QUESTIONS WHERE mail = '" + mail + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        String ans = "";
        if (rs.next()) {
            ans = "\"points\" : \""
                + rs.getDouble("points")
                + "\", \"heatingPoints\" : \""
                + rs.getDouble("heating")
                + "\", \"heatingLast\" : \""
                + rs.getDouble("heatingLate")
                + "\", \"heatingTemp\" : \""
                + rs.getDouble("heatingTemp")
                + "\", \"heatingTime\" : \""
                + rs.getDouble("heatingTime")
                + "\"";
        }
        return ans;
    }

    /**
     * check if the mail is in the db.
     *
     * @param mail mailt to check
     * @param conn connection to db
     * @return if it exists return true
     * @throws SQLException something went wrong in db
     */
    public static boolean checkMail(String mail, Connection conn) throws SQLException {
        String sql = "SELECT * FROM USERS.ACCOUNTS "
            + "WHERE ACCOUNTS.mail = '" + mail + "' ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }

    /**
     * check if the friendship exists.
     *
     * @param mailFrom 1.mail
     * @param mailto   sec mail
     * @param conn     connection to db
     * @return true if the relation exists
     * @throws SQLException something went wrong in db
     */
    public static boolean checkRelation(String mailFrom, String mailto,
                                        Connection conn) throws SQLException {
        String sql = "SELECT * FROM USERS.FRIENDS WHERE FRIENDS.mail_from = '"
            + mailFrom + "' and  FRIENDS.mail_to = '" + mailto + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }

    //if username is here true

    /**
     * check if the username is there.
     *
     * @param name name to check
     * @param conn connection to db
     * @return if exists return true
     * @throws SQLException something went wrong in db
     */
    public static boolean checkUsername(String name, Connection conn) throws SQLException {
        String sql = "SELECT * FROM USERS.ACCOUNTS WHERE ACCOUNTS.user_name = '" + name + "' ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();
    }

    /**
     * authenticate the acc with mail and pass.
     *
     * @param mail mail
     * @param pass password
     * @param conn connection to db
     * @return if the combination is right true
     * @throws SQLException something went wrong in db
     */
    public static boolean checkAccMail(String mail, String pass, Connection conn)
        throws SQLException {
        String sql = "SELECT * FROM USERS.ACCOUNTS WHERE ACCOUNTS.mail = '"
            + mail + "' and ACCOUNTS.pass = '" + hashPass(pass) + "' ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();

    }

    /**
     * authenticate with the username and password.
     *
     * @param name username
     * @param pass password
     * @param conn connection to db
     * @return true if the combination is right
     * @throws SQLException something went wrong in db
     */
    public static boolean checkAccName(String name, String pass, Connection conn)
        throws SQLException {
        String sql = "SELECT * FROM USERS.ACCOUNTS WHERE ACCOUNTS.user_name = '"
            + name + "' and ACCOUNTS.pass = '" + hashPass(pass) + "' ";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs.next();

    }

    /**
     * add an account to the db.
     *
     * @param mail mail to add
     * @param name username to add
     * @param pass password to add
     * @param conn connection to db
     * @return if it completed return true
     * @throws SQLException something went wrong in db
     */
    public static boolean addAccount(String mail, String name, String pass,
                                     Connection conn) throws SQLException {
        if (!checkMail(mail, conn)) {
            Statement st = conn.createStatement();
            String hashedpass = hashPass(pass);
            String sql = "INSERT INTO USERS.ACCOUNTS VALUES ( '"
                + mail + "' , '" + name + "' , '" + hashedpass + "' , false)";
            st.executeUpdate(sql);
            DataBase.addFriend(mail,mail,conn);
            DataBase.statusFriend(mail,mail,true,conn);
            return DataBase.addAccQuestion(mail, conn) && DataBase.addAccRewards(mail, conn)
                && DataBase.addAccRewardsPast(mail,conn);
        }
        return false;
    }

    /**
     * adds acount to the reward table.
     *
     * @param mail mail to add
     * @param conn connection to db
     * @return if it is added return true
     * @throws SQLException something went wrong in db
     */
    public static boolean addAccRewards(String mail, Connection conn) throws SQLException {
        if (checkMail(mail, conn)) {
            String sql = "INSERT INTO USERS.REWARDS VALUES ('"
                + mail + "' , false , false, false, false, "
                + "false, false, false, false, false, false)";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * is the reward already earned table adding.
     * @param mail mail
     * @param conn connection to db
     * @return adds account to the table
     * @throws SQLException something went wrong in db
     */
    public static boolean addAccRewardsPast(String mail, Connection conn) throws SQLException {
        if (checkMail(mail, conn)) {
            String sql = "INSERT INTO USERS.REWARDS_PAST VALUES ('"
                + mail + "' , false , false, false, false, "
                + "false, false, false, false, false, false)";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * adds a meal to meals table.
     * @param mail mail of current user
     * @param conn connection
     * @return true if added
     * @throws SQLException if something went wrong
     */
    public static boolean addMeal(String mail, String meal,
                                  double price, Connection conn) throws SQLException {
        if (checkMail(mail, conn)) {
            String sql = "INSERT INTO USERS.MEALS VALUES ( 0 ,'"
                    + mail + "','" + meal + "'," + price + ")";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * returns meals of current user.
     * @param mail current user
     * @param conn connection
     * @return meals & prices
     * @throws SQLException if something goes wrong
     */
    public static String getMeals(String mail, Connection conn) throws SQLException {
        String sql = "SELECT meal, price FROM MEALS WHERE mail='" + mail + "'";
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        return mealToString(rs);
    }

    /**
     * adds account to the question table.
     *
     * @param mail mail to add
     * @param conn connection to db
     * @return if it is completed return true
     * @throws SQLException something went wrong in db
     */
    public static boolean addAccQuestion(String mail, Connection conn) throws SQLException {
        if (checkMail(mail, conn)) {
            String sql = "INSERT INTO USERS.QUESTIONS VALUES ('"
                + mail + "', 0 , 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)";
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * remove an account from the db.
     *
     * @param mail mail
     * @param pass password
     * @param conn connection to db
     * @return return true if it is completed
     * @throws SQLException something went wrong in db
     */
    public static boolean removeAcc(String mail, String pass, Connection conn)
        throws SQLException {
        if (checkAccMail(mail, pass, conn)) {
            Statement st = conn.createStatement();
            String sql = "DELETE FROM USERS.QUESTIONS WHERE QUESTIONS.mail = '" + mail + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM USERS.ACCOUNTS WHERE ACCOUNTS.mail = '"
                + mail + "' and ACCOUNTS.pass = '" + hashPass(pass) + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM USERS.FRIENDS WHERE FRIENDS.mail_from = '"
                + mail + "' or FRIENDS.mail_to= '" + mail + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM USERS.REWARDS WHERE REWARDS.mail = '" + mail + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM USERS.REWARDS_PAST WHERE REWARDS_PAST.mail = '" + mail + "'";
            st.executeUpdate(sql);
            sql = "DELETE FROM USERS.MEALS WHERE MEALS.mail = '" + mail + "'";
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * remove a meal from the db.
     *
     * @param mail mail
     * @param conn connection to db
     * @return return true if it is completed
     * @throws SQLException something went wrong in db
     */
    public static boolean removeMeal(String mail, String meal,  Connection conn)
            throws SQLException {
        if (checkMail(mail, conn)) {
            Statement st = conn.createStatement();
            String sql = "DELETE FROM USERS.MEALS WHERE MEALS.mail = '"
                + mail + "' AND MEALS.meal = '" + meal + "'";
            st.executeUpdate(sql);
            return true;
        }
        return false;
    }

    /**
     * change the password.
     *
     * @param mail mail to change
     * @param pass new password
     * @param conn connection to db
     * @return true if completed
     * @throws SQLException something went wrong in db
     */
    public static boolean changePass(String mail, String pass, Connection conn)
        throws SQLException {
        if (checkMail(mail, conn)) {
            Statement st = conn.createStatement();
            String sql = "UPDATE ACCOUNTS SET pass = '"
                + hashPass(pass) + "' WHERE mail = '" + mail + "'";
            st.executeUpdate(sql);
            st.close();
            return true;
        }
        return false;
    }

    /**
     * connect to the db.
     *
     * @return return the connection
     * @throws SQLException           something went wrong in db
     * @throws ClassNotFoundException something wrong with driver
     */
    public static Connection connect(String url) throws SQLException, ClassNotFoundException {
        Class.forName(dbDriver);
        Connection conn = DriverManager.getConnection(url, user, pass);
        System.out.println("connected");
        return conn;
    }

    /**
     * colose the connection to the db.
     *
     * @param con connection to close
     * @return return true if it is closed
     * @throws SQLException something went wrong in db
     */
    public static boolean closeConnect(Connection con) throws SQLException {
        if (con != null) {
            con.close();
            System.out.println("Goodbye!");
            return true;
        }
        return false;
    }

    /**
     * Creates a new database with every table.
     *
     * @param name name of the database to create use "USERS"
     * @return returns connection to that db
     * @throws SQLException something went wrong in db
     */
    public static Connection createDb(String name) throws SQLException {
        Statement stmt = null;
        Connection conn = null;
        String url = "jdbc:mysql://localhost:4444/?autoReconnect=true&useSSL=false";
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            System.out.println("connected ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt = conn.createStatement();

        String sql = "CREATE DATABASE " + name;
        stmt.executeUpdate(sql);
        // must connect to the db created
        conn.close();
        stmt.close();
        url = "jdbc:mysql://localhost:4444/" + name + "?autoReconnect=true&useSSL=false";
        try {
            conn = DriverManager.getConnection(url, "root", "root");
            System.out.println("connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        stmt = conn.createStatement();
        // creating accounts table
        sql = "CREATE TABLE ACCOUNTS "
            + "(mail VARCHAR(255) not NULL, "
            + " user_name VARCHAR(255) not NULL, "
            + " pass VARCHAR(255) not NULL, "
            + " loged BOOLEAN not NULL, "
            + " PRIMARY KEY ( mail ))";
        stmt.executeUpdate(sql);

        //creating questions table
        sql = "CREATE TABLE QUESTIONS "
            + "( mail VARCHAR(255) not NULL, "
            + " points DOUBLE(20,5) not NULL, "
            + " vegan DOUBLE(20,5), "
            + " bike DOUBLE(20,5), "
            + " walk DOUBLE(20,5), "
            + " public_trans DOUBLE(20,5), "
            + " local DOUBLE(20,5), "
            + " heating DOUBLE(20,5), "
            + " heatingTemp DOUBLE(20,5), "
            + " heatingLate DOUBLE(20,5), "
            + " heatingTime DOUBLE(20,5), "
            + " solar DOUBLE(20,5), "
            + " solarM2 DOUBLE(20,5), "
            + " solarLate DOUBLE(20,5), "
            + " solarTime DOUBLE(20,5), "
            + " donations DOUBLE(20,5), "
            + " PRIMARY KEY ( mail ))";
        stmt.executeUpdate(sql);

        //creating questions friends
        sql = "CREATE TABLE FRIENDS "
            + "( mail_from VARCHAR(255) not NULL, "
            + " mail_to VARCHAR(255) not NULL,  "
            + " approved BOOLEAN not NULL)  ";
        stmt.executeUpdate(sql);

        //creating questions rewards
        sql = "CREATE TABLE REWARDS "
            + "( mail VARCHAR(255) not NULL, "
            + " vegan10 BOOLEAN not NULL, "
            + " vegan100 BOOLEAN not NULL, "
            + " bike100 BOOLEAN not NULL, "
            + " walk100 BOOLEAN not NULL, "
            + " public_trans100 BOOLEAN not NULL, "
            + " local10 BOOLEAN not NULL, "
            + " local100 BOOLEAN not NULL, "
            + " heating100 BOOLEAN not NULL, "
            + " solar100 BOOLEAN not NULL, "
            + " donate100 BOOLEAN not NULL, "
            + " PRIMARY KEY ( mail ))";
        stmt.executeUpdate(sql);

        //creating questions rewards past
        sql = "CREATE TABLE REWARDS_PAST "
            + "( mail VARCHAR(255) not NULL, "
            + " vegan10 BOOLEAN not NULL, "
            + " vegan100 BOOLEAN not NULL, "
            + " bike100 BOOLEAN not NULL, "
            + " walk100 BOOLEAN not NULL, "
            + " public_trans100 BOOLEAN not NULL, "
            + " local10 BOOLEAN not NULL, "
            + " local100 BOOLEAN not NULL, "
            + " heating100 BOOLEAN not NULL, "
            + " solar100 BOOLEAN not NULL, "
            + " donate100 BOOLEAN not NULL, "
            + " PRIMARY KEY ( mail ))";
        stmt.executeUpdate(sql);

        //create meals table
        sql = "CREATE TABLE MEALS "
                + "(meal_id INT AUTO_INCREMENT,"
                + "mail VARCHAR(255) not NULL, "
                + " meal VARCHAR(255) not NULL, "
                + " price DOUBLE(20,5), "
                + " PRIMARY KEY (meal_id))";
        stmt.executeUpdate(sql);

        return conn;
    }

    /**
     * encripts the password.
     * @param pass password to encript
     * @return returns the ecrypted password
     */
    public static String hashPass(String pass) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(pass.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length ; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }

}