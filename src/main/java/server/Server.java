package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import db.DataBase;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server {


    private static HttpServer server;

    private static String dbUrl =
        "jdbc:mysql://localhost:4444/USERS?autoReconnect=true&useSSL=false";
    private static double vegP = selenium.VegetarianMeal.foodEmission();
    private static double solarP = selenium.VegetarianMeal.houseEmission(1);
    private static double heatP = selenium.VegetarianMeal.houseEmission(2);
    private static double trainPl = selenium.VegetarianMeal.publicTransEmission(1,1);
    private static double trainPm = selenium.VegetarianMeal.publicTransEmission(1,2);
    private static double trainPh = selenium.VegetarianMeal.publicTransEmission(1,3);
    private static double busPl = selenium.VegetarianMeal.publicTransEmission(2,1);
    private static double busPm = selenium.VegetarianMeal.publicTransEmission(2,2);
    private static double busPh = selenium.VegetarianMeal.publicTransEmission(2,3);
    private static double carP = selenium.VegetarianMeal.carEmission();

    private static Connection connDB;

    static {
        try {
            connDB = DataBase.connect(dbUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * gets the mails scores in all questions.
     *
     * @param msg send from client
     * @return return in json the scores
     * @throws JSONException something went wrong in json
     */
    public static String getQuestionsHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        String result;
        try {
            if (DataBase.checkMail(mail,connDB)) {
                result = DataBase.getQuestions(mail, connDB);
                if (result != null) {
                    return "{\"command\" : \"scores of the mail\", " + result + "}";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"couldnt get questions\"}";
    }

    /**
     * gets leader Board.
     * @param msg sent from client
     * @return the leader Board
     * @throws JSONException wrong json
     */
    public static String getLeaderHandler(JSONObject msg) throws JSONException {
        String result = " ";
        try {
            ResultSet rs = DataBase.leaderBoard(connDB);
            while (rs.next()) {
                result = result + rs.getString("user_name") + " "
                    + rs.getString("mail") + " "
                    + rs.getDouble("points") + ", ";
            }
            result = "{\"command\" : \"got list\", \"list\" : \""
                + result.substring(0,result.length() - 2) + "\"}";
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"couldnt get leaderList ??????\"}";
    }

    /**
     * gets rewards for the user.
     * @param msg json from client
     * @return returns the rewards
     * @throws JSONException json is wrong
     * @throws SQLException db is wrong
     */
    public static String getRewardsHandler(JSONObject msg) throws JSONException, SQLException {
        String mail = msg.getString("mail");
        System.out.println(DataBase.rewardUpdater(mail, connDB));

        boolean v10 = DataBase.getReward(mail , "vegan10" , connDB);
        boolean v100 = DataBase.getReward(mail , "vegan100" , connDB);
        boolean l10 = DataBase.getReward(mail , "local10" , connDB);
        boolean l100 = DataBase.getReward(mail , "local100" , connDB);
        boolean h100 = DataBase.getReward(mail , "heating100" , connDB);
        boolean s100 = DataBase.getReward(mail , "solar100" , connDB);
        boolean d100 = DataBase.getReward(mail , "donate100" , connDB);
        boolean pt100 = DataBase.getReward(mail , "public_trans100" , connDB);
        boolean b100 = DataBase.getReward(mail , "bike100" , connDB);
        boolean w100 = DataBase.getReward(mail , "walk100" , connDB);

        String result = "{\"command\" : \"got rewards\", \"vegan10\" : \""
            + v10 + "\", \"vegan100\" : \""
            + v100 + "\", \"local10\" : \""
            + l10 + "\", \"local100\" : \""
            + l100 + "\", \"heating100\" : \""
            + h100 + "\", \"solar100\" : \""
            + s100 + "\", \"donate100\" : \""
            + d100 + "\", \"public_trans100\" : \""
            + pt100 + "\", \"bike100\" : \""
            + b100 + "\", \"walk100\" : \""
            + w100 + "\"}";
        return result;
    }

    /**
     * Gets the daily update able attributes from the db.
     * @param msg the JSON message
     * @return returns it in JSON format
     * @throws JSONException if the JSON parsing went wrong
     * @throws SQLException if
     */
    public static String getDailyHandler(JSONObject msg) throws JSONException, SQLException {
        String mail = msg.getString("mail");
        System.out.println(DataBase.rewardUpdater(mail, connDB));

        double solar = DataBase.getAmountQuestion(mail , "solar" , connDB);
        double solarM2 = DataBase.getAmountQuestion(mail , "solarM2" , connDB);
        double solarTime = DataBase.getAmountQuestion(mail , "solarTime" , connDB);
        double solarLate = DataBase.getAmountQuestion(mail , "solarLate" , connDB);

        double heating = DataBase.getAmountQuestion(mail , "heating" , connDB);
        double heatingTemp = DataBase.getAmountQuestion(mail , "heatingTemp" , connDB);
        double heatingTime = DataBase.getAmountQuestion(mail , "heatingTime" , connDB);
        double heatingLate = DataBase.getAmountQuestion(mail , "heatingLate" , connDB);

        String result = "{\"command\" : \"got dailys\", \"solar\" : \""
            + solar + "\", \"solarM2\" : \""
            + solarM2 + "\", \"solarTime\" : \""
            + solarTime + "\", \"solarLate\" : \""
            + solarLate + "\", \"heating\" : \""
            + heating + "\", \"heatingTemp\" : \""
            + heatingTemp + "\", \"heatingTime\" : \""
            + heatingTime + "\", \"heatingLate\" : \""
            + heatingLate + "\" }";

        return result;
    }

    //
    // Friend Stuff
    //

    /**
     * returns friends with points as a json.
     *
     * @param msg mesage from client
     * @return all friends list in json
     * @throws JSONException if the json sent is wrong
     */
    public static String getFriendsPonitsHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        try {
            if (DataBase.checkMail(mail,connDB)) {
                return "{\"command\" : \"friendsPoints\", \"friends\" : \""
                    + db.DataBase.getFriendsPoints(mail, true, connDB)
                    + "\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"couldnt get friednsPoints\"}";
    }

    /**
     * returns friends as a json.
     *
     * @param msg mesage from client
     * @return all friends list in json
     * @throws JSONException if the json sent is wrong
     */
    public static String getFriendsHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                return "{\"command\" : \"all friends\", \"friends\" : \""
                    + db.DataBase.getFriendsStr(mail, true, connDB)
                    + "\", \"waiting\" : \""
                    + db.DataBase.getFriendsStr(mail, false, connDB)
                    + "\", \"approve\" : \""
                    + db.DataBase.getFriendstoApproveStr(mail, false, connDB)
                    + "\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"couldnt get friedns\"}";
    }

    /**
     * delete friend.
     *
     * @param msg sent to server
     * @return if it is executred or not
     * @throws JSONException sent json is wrong
     */
    public static String deleteFriendHandler(JSONObject msg) throws JSONException {
        String mailf = msg.getString("mail_from");
        String mailt = msg.getString("mail_to");
        try {
            if (db.DataBase.deleteFriend(mailf, mailt, connDB)) {
                return "{\"command\" : \"friend removed\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"removing failed\"}";
    }

    /**
     * approve the friend.
     *
     * @param msg msg sent to server
     * @return if added aproved message if not not approved message
     * @throws JSONException something went wrong in jason
     */
    public static String approveFriendHandler(JSONObject msg) throws JSONException {
        String mailf = msg.getString("mail_from");
        String mailt = msg.getString("mail_to");
        try {
            if (db.DataBase.statusFriend(mailf, mailt, true, connDB)) {
                db.DataBase.addFriend(mailt, mailf , connDB);
                db.DataBase.statusFriend(mailt, mailf, true, connDB);
                return "{\"command\" : \"added to friend\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"not approved\"}";
    }

    /**
     * friend request sent.
     *
     * @param msg json mesage that is recived
     * @return true if the friend request is saved
     * @throws JSONException something went wrong in jason
     */
    public static String addFriendHandler(JSONObject msg) throws JSONException {
        String mailf = msg.getString("mail_from");
        String mailt = msg.getString("mail_to");
        try {
            if (db.DataBase.addFriend(mailf, mailt, connDB)) {
                return "{\"command\" : \"request sent\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"request failed\"}";
    }

    //
    // Friends Stuff done
    //

    /**
     * signuo handler.
     * @param msg json mesage that is recived
     * @return true if the account is added to the db
     * @throws JSONException something went wrong in jason
     */
    public static String signupHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        String name = msg.getString("username");
        String pass = msg.getString("pass");
        try {
            if (db.DataBase.addAccount(mail, name, pass, connDB)) {
                return "{\"command\" : \"pass signup\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in sign up\"}";
    }

    /**
     * change password handler.
     * @param msg msg to send to change pass word json
     * @return returns if it succesfullm or not
     * @throws JSONException something is wrong in the sent message
     */
    public static String changePassHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        String newpass = msg.getString("newpass");
        try {
            if (db.DataBase.changePass(mail , newpass, connDB)) {
                return "{\"command\" : \"changed\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in changing pass\"}";
    }

    /**
     * login handler for mail.
     * @param msg json mesage that is recived
     * @return true if the account is authenticated in db
     * @throws JSONException something went wrong in jason
     */
    public static boolean loginHandlerMail(JSONObject msg) throws JSONException {

        String mail = msg.getString("mail");
        String pass = msg.getString("pass");
        boolean ans = false;
        try {
            ans = db.DataBase.checkAccMail(mail, pass, connDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * login handler for username.
     * @param msg json mesage that is recived
     * @return true if the account is authenticated in db
     * @throws JSONException something went wrong in jason
     */
    public static boolean loginHandlerName(JSONObject msg) throws JSONException {

        String name = msg.getString("username");
        String pass = msg.getString("pass");
        boolean ans = false;
        try {
            ans = db.DataBase.checkAccName(name, pass, connDB);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * Login Handler.
     * @param msg Json from client with the data
     * @return returns a message dseppening if it passed or not
     * @throws JSONException wrong json
     */
    public static String loginHandler(JSONObject msg) throws JSONException {
        if (loginHandlerMail(msg) || loginHandlerName(msg)) {
            return "{\"command\" : \"pass\"}";
        }
        return "{\"command\" : \"wrong pass\"}";
    }

    //
    // Entrys start
    //

    /**
     * adds points depending on how many km you traveld and with wath.
     *
     * @param msg from client
     * @return return json sayin how much point earned
     * @throws JSONException something is wrong in the send message
     */
    public static String notCarHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        String type = msg.getString("type");
        int dis = msg.getInt("distance");
        double disP = carP * dis;

        // add for walking and biking
        //add tests
        if (type.equals("train")) {
            if (dis <= 50 ) {
                disP = trainPl;
            } else if (dis <= 100) {
                disP = trainPm;
            } else {
                disP = trainPh;
            }
            type = "public_trans";
        } else if (type.equals("bus")) {
            if (dis <= 25) {
                disP = busPl;
            } else if (dis <= 50) {
                disP = busPm;
            } else {
                disP = busPh;
            }
            type = "public_trans";
        }
        try {
            if (db.DataBase.checkMail(mail , connDB)) {
                db.DataBase.addToQuestion(mail, type, disP, connDB);
                db.DataBase.addPoints(mail, disP, connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, type, connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"added km\", \"Km\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in notCar\"}";
    }

    /**
     * adds points for solar.
     *
     * @param msg from client
     * @return return json sayin how much point earned
     * @throws JSONException something is wrong in the send message
     */
    public static String solarHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        double m2 = msg.getDouble("m2");
        double time = msg.getDouble("time");
        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                System.out.println("ppp" + solarP);
                db.DataBase.addToQuestion(mail, "solar", m2 * solarP, connDB);
                db.DataBase.addToQuestion(mail, "solarM2", m2 , connDB);
                db.DataBase.changeQuestion(mail,"solarTime", time, connDB);
                db.DataBase.addPoints(mail, m2 * solarP, connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, "solar", connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"added solar\", \"totalPointsSolar\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in solar\"}";
    }

    /**
     * adds points for decreasing heat.
     *
     * @param msg from client
     * @return return json sayin how much point earned
     * @throws JSONException something is wrong in the send message
     */
    public static String heatHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        double temp = msg.getDouble("temp");
        double time = msg.getDouble("time");
        System.out.println(heatP);
        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                db.DataBase.addToQuestion(mail, "heating", (temp / 4) * heatP, connDB);
                db.DataBase.addToQuestion(mail, "heatingTemp", temp , connDB);
                db.DataBase.addToQuestion(mail, "heatingTime", time , connDB);
                db.DataBase.addPoints(mail, (temp / 4) * heatP, connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, "heating", connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"added heat\", \"totalPointsHeating\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in heating\"}";
    }

    /**
     * adds points for buying local.
     *
     * @param msg from client
     * @return return json sayin how much point earned
     * @throws JSONException something is wrong in the send message
     */
    public static String buyLocalHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        double num = msg.getDouble("number");

        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                db.DataBase.addToQuestion(mail, "local", num, connDB);
                db.DataBase.addPoints(mail, num * vegP, connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, "local", connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"added local shop\", \"total local\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in localhandler\"}";
    }

    /**
     * add vegan meal to the account.
     * @param msg json mesage that is recived
     * @return returns the stats of the user after adding a vegan meal
     * @throws JSONException something went wrong in jason
     */
    public static String addVeganMeal(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        int num = msg.getInt("number");
        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                db.DataBase.addToQuestion(mail, "vegan", num, connDB);
                db.DataBase.addPoints(mail, vegP * num, connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, "vegan", connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"added vegan meal\", \"total meals\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong\"}";
    }

    /**
     * Updates the time in the database and adds the points.
     * @param msg the JSON string
     * @return a JSON with the new time and new points
     * @throws JSONException if parsing went wrong
     */
    public static String addPointsManualTime(JSONObject msg) throws JSONException {
        double day = 86400000;
        String mail = msg.getString("mail");
        String what = msg.getString("what");
        String timeStr = msg.getString("timeStr");
        double time = msg.getDouble("time");
        double point = msg.getDouble("points");

        try {
            if (db.DataBase.checkMail(mail,connDB)) {
                double number =
                    (time - db.DataBase.getAmountQuestion(mail, timeStr , connDB)) / day;
                if (number > 1) {
                    db.DataBase.addToQuestion(mail, what, number * point, connDB);
                    db.DataBase.addPoints(mail, number * point, connDB);
                    System.out.println(timeStr);
                    db.DataBase.changeQuestion(mail, timeStr, time, connDB);
                    return "{\"command\" : \"doneTime\" , \"newPoints\" : \""
                        + db.DataBase.getAmountQuestion(mail, what, connDB) + "\" }";
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong\"}";
    }


    /**
     * Donete me.
     * @param msg msg sent From Client
     * @return returns total points and donations
     * @throws JSONException wrong json
     */
    public static String donateHandler(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        int num = msg.getInt("money");
        try {
            if (DataBase.checkMail(mail, connDB)) {
                db.DataBase.addToQuestion(mail, "donations", num, connDB);
                db.DataBase.addPoints(mail, num * 10 , connDB);
                double points = db.DataBase.getPoints(mail, connDB);
                double number = db.DataBase.getAmountQuestion(mail, "donations", connDB);
                // reward checking
                db.DataBase.rewardUpdater(mail, connDB);
                boolean earned = db.DataBase.recentRewardUpdate(mail,connDB);

                return "{\"command\" : \"thanks for donation\", \"total donations\" : \""
                    + number + "\" , \"score\" : \"" + points
                    + "\" , \"newReward\" : \"" + earned + "\"}";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "{\"command\" : \"something went wrong in donations\"}";
    }

    //
    //Entries Finished
    //

    /**
     * delets the account from db.
     * @param msg json mesage that is recieved
     * @return true if the account is deleted from the db
     * @throws JSONException something went wrong in jason
     */
    public static boolean deleteAcc(JSONObject msg) throws JSONException {
        String mail = msg.getString("mail");
        String pass = msg.getString("pass");
        try {
            return db.DataBase.removeAcc(mail, pass, connDB);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Gets the points form the database.
     * @return the it in JSON format
     */
    public static String getConstantHandler() {
        String ans = "{ \"SolarP\" : \""
            + solarP + "\" ,\"HeatP\" : \""
            + heatP + "\"}";
        return ans;
    }

    /// Meal Stuff ///

    /**
     * gets Meals.
     * @param js json from client
     * @return returns the meal.
     */
    public static String takeMealsHandler(JSONObject js) {
        String mail = js.getString("mail");
        String ans = "?";
        try {
            ans = "{\"command\" : \"got meals\", \"meals\" : \""
                + db.DataBase.getMeals(mail, connDB) + "\" }";
        } catch (SQLException e) {
            System.out.println("meals " + e);
        }
        return ans;
    }

    /**
     * adds meals to db.
     * @param js json from client
     * @return returns the answer from server
     */
    public static String addMealHandler(JSONObject js) {
        String mail = js.getString("mail");
        String meal = js.getString("meal");
        double price = js.getDouble("price");

        String ans = "? ?????";
        try {
            if (db.DataBase.addMeal(mail,meal , price, connDB)) {
                ans = "{\"command\" : \"added Meal\"}";
            }
        } catch (SQLException e) {
            System.out.println("adding meals " + e);
        }

        return ans;
    }

    /**
     * removes the meal from db.
     * @param js json from client
     * @return returns servers answer
     */
    public static String removeMealHandler(JSONObject js) {
        String mail = js.getString("mail");
        String meal = js.getString("meal");

        String ans = "?";
        try {
            if (db.DataBase.removeMeal(mail,meal,connDB)) {
                ans = "{\"command\" : \"removed Meal\"}";
            }
        } catch (SQLException e) {
            System.out.println("adding meals " + e);
        }
        return ans;
    }

    /**
     * General Friend Handler.
     * @param command command that choses what to oparate
     * @param json data of the operation
     * @return returns answer
     */
    public static String friendHandler(String command , JSONObject json) {
        if (command.equals("sentFriendReq")) {
            return addFriendHandler(json);
        }
        if (command.equals("approveFriend")) {
            return approveFriendHandler(json);
        }
        if (command.equals("removeFriend")) {
            return deleteFriendHandler(json);
        }
        if (command.equals("FriendList")) {
            return getFriendsHandler(json);
        }
        if (command.equals("FriendsPoints")) {
            return getFriendsPonitsHandler(json);
        }
        return "wut";
    }


    /**
     * general Entry handler.
     * @param command commaND THAT CHOOSES THE OPERATION
     * @param json data of the operation
     * @return returns the answer from operation
     */
    public static String entryHandler(String command , JSONObject json) {
        if (command.equals("EntryVeganMeal")) {
            return addVeganMeal(json);
        }
        if (command.equals("EntryLocalProduct")) {
            return buyLocalHandler(json);
        }
        if (command.equals("EntryNotCar")) {
            return notCarHandler(json);
        }
        if (command.equals("EntryHeating")) {
            return heatHandler(json);
        }
        if (command.equals("EntrySolar")) {
            return solarHandler(json);
        }
        if (command.equals("EntryDonate")) {
            return donateHandler(json);
        }
        if (command.equals("EntryUpdate")) {
            return addPointsManualTime(json);
        }
        return "wut";
    }

    /**
     * handler for gets.
     * @param command send from client
     * @param json send json from client
     * @return returns the gets functions
     * @throws JSONException json wrong
     * @throws SQLException db wtong
     */
    public static String getHandler(String command , JSONObject json)
        throws JSONException,SQLException  {
        if (command.equals("getQuestions")) {
            return getQuestionsHandler(json);
        }
        if (command.equals("getLeaderBoard")) {
            return getLeaderHandler(json);
        }
        if (command.equals("getRewards")) {
            return getRewardsHandler(json);
        }
        if (command.equals("getDailyUpdate")) {
            return getDailyHandler(json);
        }
        if (command.equals("getPass")) {
            return changePassHandler(json);
        }
        if (command.equals("getConstants")) {
            return getConstantHandler();
        }
        if (command.equals("getremoveAcc")) {
            if (loginHandlerMail(json) && deleteAcc(json)) {
                return "{\"command\" : \"rip acc\"}";
            }
            return "{\"command\" : \"something went wrong in deleting\"}";
        }
        return "wut";
    }

    /**
     * handler for meals.
     * @param command send from client
     * @param json send json from client
     * @return returns the gets functions
     * @throws JSONException json wrong
     */
    public static String mealHandler(String command , JSONObject json)
        throws JSONException  {
        if (command.equals("takeMeals")) {
            return takeMealsHandler(json);
        }
        if (command.equals("addMeal")) {
            return addMealHandler(json);
        }
        if (command.equals("removeMeal")) {
            return removeMealHandler(json);
        }
        return "wut Meals";
    }

    /**
     * main handeler.
     *
     * @param msgg json mesage that is recived
     * @return true if the account is added to the db
     * @throws JSONException something went wrong in jason
     */
    public static String handler(String msgg) throws JSONException {
        JSONObject json = new JSONObject(msgg);
        String command = json.getString("command");
        if (command.equals("login")) {
            return loginHandler(json);
        } else if (command.equals("signup")) {
            return signupHandler(json);
        } else if (command.contains("Entry")) {
            return entryHandler(command , json);
        } else if (command.contains("Meal")) {
            return mealHandler(command , json);
        } else if (command.contains("Friend")) {
            return friendHandler(command , json);
        } else if (command.contains("get")) {
            try {
                return getHandler(command , json);
            } catch (SQLException E) {
                return "Problem in dataBase" + E ;
            }
        } else {
            return command + " get this";
        }
    }

    /**
     * turning byte to string.
     *
     * @param ex HttpExchange
     * @return return the transfered byte to string
     * @throws IOException server error
     */
    public static String bytetoStr(HttpExchange ex) throws IOException {
        // Transform the input stream and read it to a string
        InputStreamReader reader = new InputStreamReader(
            ex.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder req = new StringBuilder();
        int num;
        while ((num = bufferedReader.read()) != -1) {
            req.append((char) num);
        }
        bufferedReader.close();
        reader.close();

        return req.toString();
    }

    /**
     * sets the server up and runs it.
     * @param args random arg
     */
    public static void main(String[] args) {
        try {
            // Set up the server object
            server = HttpServer.create(new InetSocketAddress(3399), 0);

            System.out.println("Connected");
            // Create the context and set handlers
            server.createContext("/hello", (HttpExchange exchange) -> {
                // Transform the input stream and read it to a string
                // Forward to handler
                byte[] responseBytes = new byte[1];
                try {
                    responseBytes = handler(bytetoStr(exchange)).getBytes();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Send 200
                exchange.sendResponseHeaders(200, responseBytes.length);
                exchange.getResponseBody().write(responseBytes);
            });
            // Start the server
            server.setExecutor(null);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}