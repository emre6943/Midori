package server;

import db.Account;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientMet {


    /**
     * sends the right json for signing up.
     * @param mail mail
     * @param username username
     * @param pass password
     * @return returns back servers answer
     * @throws IOException if jason is missing but not possible
     */
    public static String signUp(String mail, String username, String pass) throws IOException {
        Client client = new Client();
        HttpURLConnection connection = client.connectServer(client.path);
        String answer = client.sendJson("{\"command\" : \"signup\", \"mail\" : \"" + mail + "\","
            + " \"username\" : \"" + username + "\",  \"pass\" : \"" + pass + "\" }", connection);
        return answer;
    }


    /**
     * sends the right jason for login.
     * @param mail mail
     * @param username username
     * @param pass password
     * @return returns back servers answer
     * @throws IOException if jason is missing but not possible
     */
    public static String logIn(String mail, String username, String pass) throws IOException {
        Client client = new Client();
        HttpURLConnection connection = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"login\", \"mail\" : \""
            + mail + "\", \"username\" : \""
            + username + "\",  \"pass\" : \""
            + pass + "\" }", connection);
        return answer;
    }


    /**
     * sends the right json to get the questions.
     * @param mail mail
     * @return returns back servers answer
     * @throws IOException if jason is missing but not possible
     */
    public static String getQuestions(String mail) throws IOException {
        Client client = new Client();
        HttpURLConnection connection = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"getQuestions\", \"mail\" : \""
            + mail + "\" }", connection);

        return answer;

    }

    /**
     * sends the right json to send frined request.
     * @param mailFrom mail from
     * @param mailTo mail to
     * @return returns back servers answer
     * @throws IOException if jason is missing but not possible
     */
    public static String sendFriendReq(String mailFrom, String mailTo) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"sentFriendReq\", \"mail_from\" : \""
            + mailFrom + "\", \"mail_to\" : \""
            + mailTo + "\" }", connect);

        return answer;
    }

    /**
     * sends the right json to approve friend.
     * @param mailFrom mail from
     * @param mailTo mail to
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String approveFriend(String mailFrom, String mailTo) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"approveFriend\", \"mail_from\" : \""
            + mailFrom + "\", \"mail_to\" : \""
            + mailTo + "\" }", connect);

        return answer;
    }

    /**
     * sends the right json to get leader board.
     * @return returns servers answer
     * @throws IOException if json is wrong but not possible
     */
    public static String getLeader() throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"getLeaderBoard\"}", connect);

        return answer;
    }


    /**
     * sends the right json to get friends.
     * @param mail mail
     * @return returns servers answer
     * @throws IOException if json is wrong but not possible
     */
    public static String getFriends(String mail) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"FriendList\", \"mail\" : \""
            + mail + "\"}", connect);

        return answer;
    }

    /**
     * sends the right json to get friends points.
     * @param mail mail
     * @return returns servers answer
     * @throws IOException if json is wrong but not possible
     */
    public static String getFriendsPoints(String mail) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"FriendsPoints\", \"mail\" : \""
            + mail + "\" }", connect);

        return answer;
    }

    /**
     * sends right json to remove friend.
     * @param mailFrom mail form
     * @param mailTo mail to
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String removeFriend(String mailFrom, String mailTo) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"removeFriend\", \"mail_from\" : \""
            + mailFrom + "\", \"mail_to\" : \""
            + mailTo + "\" }", connect);

        return answer;
    }

    /**
     * sends right json to add vegatarian meal.
     * @param mail mail
     * @param num number of meals
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addVeganMeal(String mail, int num) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"EntryVeganMeal\", \"mail\" : \""
            + mail + "\",\"number\" : "
            + num + "}", connect);

        return answer;
    }


    /**
     * sends right json to add donation.
     * @param mail mail
     * @param money money donated
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String donate(String mail, int money) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"EntryDonate\", \"mail\" : \""
            + mail + "\",\"money\" : "
            + money + "}", connect);

        return answer;
    }

    /**
     * sends right json to add heat decrease.
     * @param mail mail
     * @param temp temp
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addHeat(String mail, double temp, double time) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{'command' : \"EntryHeating\", \"mail\" : \""
            + mail + "\", \"temp\" : "
            + temp + ", \"time\" : "
            + time + "}", connect);

        return answer;
    }


    /**
     * sends right json to add train.
     * @param mail mail
     * @param km distance
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addTrain(String mail, double km) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson(
            "{'command' : \"EntryNotCar\", \"type\" : \"train\", \"mail\" : \""
            + mail + "\", \"distance\" : "
            + km + "}", connect);

        return answer;
    }

    /**
     * sends right json to add buss.
     * @param mail mail
     * @param km distance
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addBus(String mail, double km) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson(
            "{'command' : \"EntryNotCar\", \"type\" : \"bus\", \"mail\" : \""
            + mail + "\", \"distance\" : "
            + km + "}", connect);

        return answer;
    }


    /**
     * sends right json to add walk.
     * @param mail mail
     * @param km km
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addWalk(String mail, int km) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson(
            "{'command' : \"EntryNotCar\", \"type\" : \"walk\", \"mail\" : \""
            + mail + "\", \"distance\" : "
            + km + "}", connect);

        return answer;
    }

    /**
     * sends right json to add bike.
     * @param mail mail
     * @param km km
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addBike(String mail, int km) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson(
            "{'command' : \"EntryNotCar\", \"type\" : \"bike\", \"mail\" : \""
            + mail + "\", \"distance\" : "
            + km + "}", connect);

        return answer;
    }


    /**
     * sends right json to add local Product.
     * @param mail mail
     * @param num number shoping
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addLocal(String mail, int num) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{'command' : \"EntryLocalProduct\", \"mail\" : \""
            + mail + "\", \"number\" : "
            + num + "}", connect);

        return answer;
    }


    /**
     * sends right json to add solar.
     * @param mail mail
     * @param m2 num of panles
     * @return returns server answer
     * @throws IOException if json is wrong but not possible
     */
    public static String addSolar(String mail, double m2, double time) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"EntrySolar\", \"mail\" : \""
            + mail + "\", \"m2\" : \""
            + m2 + "\", \"time\" : \""
            + time + "\"}", connect);

        return answer;
    }


    /**
     * sends right json to get rewards.
     * @param mail the current user
     * @return returns servers answer
     * @throws IOException if json is wrong but not possible
     */
    public static String getRewards(String mail) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{'command' : \"getRewards\", \"mail\" : \""
            + mail + "\"}", connect);

        return answer;
    }

    /**
     * Gets info regarding solar and heating.
     * @param mail the current user
     * @return the info in JSON format
     * @throws IOException if something went wrong
     */
    public static String getDailyUpdate(String mail) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{ 'command' : \"getDailyUpdate\", \"mail\" : \""
            + mail + "\" }", connect);
        System.out.println("{\"command\" : \"getDailyUpdate\", \"mail\" : \""
            + mail + "\"}");
        return answer;
    }

    /**
     * Sets the new values for the db columns.
     * @param mail the current user
     * @param update the attribute
     * @param timeStr database formatting
     * @param point base points* m2 or temp
     * @return JSON with the new values
     * @throws IOException if something went wrong
     */
    public static String updateTime(String mail, String update,
                                    String timeStr, double time , double point) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"EntryUpdate\" , \"mail\" : \""
            + mail + "\" , \"what\" : \""
            + update + "\" , \"time\" : \""
            + time + "\" , \"timeStr\" : \""
            + timeStr + "\" , \"points\" : \""
            + point + "\"}", connect);
        return answer;
    }

    /**
     * Gets the the points from the db.
     * @return the points in JSON format.
     * @throws IOException if
     */
    public static String getConstants()throws IOException {
        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"getConstants\"}", connect);

        return answer;
    }


    /**
     * sends right json to remove acc.
     * @param mail mail
     * @param pass password
     * @return returns servers answer
     * @throws IOException if json is wrong but not possible
     */
    public static String removeAcc(String mail, String pass) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"getremoveAcc\", \"mail\" : \""
            + mail + "\" , \"pass\" : \""
            + pass + "\"}", connect);

        return answer;
    }

    /**
     * change password.
     * @param mail mail
     * @param newpass new password
     * @return the answer from server
     * @throws IOException something went wrong
     */
    public static String changePass(String mail, String  newpass) throws IOException {

        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"getPass\", \"mail\" : \""
            + mail + "\" , \"newpass\" : \""
            + newpass + "\"}", connect);

        return answer;
    }

    /**
     * adds Meal.
     * @param mail mail
     * @param meal meal
     * @param price price
     * @return returns server's answer
     * @throws IOException json is wrong
     */
    public static String addMeal(String mail, String meal , double price) throws IOException {
        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"addMeal\", \"mail\" : \""
            + mail + "\" , \"meal\" : \""
            + meal + "\" , \"price\" : \""
            + price + "\"}", connect);

        return answer;
    }

    /**
     * remove meal.
     * @param mail mail
     * @param meal meal to remove
     * @return returns servers answer
     * @throws IOException json is wrong
     */
    public static String removeMeal(String mail, String meal ) throws IOException {
        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"removeMeal\", \"mail\" : \""
            + mail + "\" , \"meal\" : \""
            + meal + "\"}", connect);

        return answer;
    }

    /**
     * getMeals in json.
     * @param mail mail
     * @return returns json form
     * @throws IOException json error
     */
    public static String getMeals(String mail) throws IOException {
        Client client = new Client();
        HttpURLConnection connect = client.connectServer(client.path);

        String answer = client.sendJson("{\"command\" : \"takeMeals\", \"mail\" : \""
            + mail + "\"}", connect);

        return answer;
    }

    /**
     * gets the Meals cleaner.
     * @param js json from ClientMet
     * @return returns arraylist of Meals
     */
    public static ArrayList<db.Meal>  cleanerMeals(String js) {
        ArrayList<db.Meal> meals = new ArrayList<>();
        JSONObject json = new JSONObject(js);
        String list = json.getString("meals");
        Scanner sc = new Scanner(list).useDelimiter(", ");
        String name ;
        double price;
        while (sc.hasNext()) {
            Scanner sc2 = new Scanner(sc.next()).useDelimiter(" ");
            name =  sc2.next();
            price =  sc2.nextDouble();
            meals.add(new db.Meal(name,price));
        }
        return meals;
    }

    /**
     * puts the json from the server into an arraylist.
     * @param js the json string that gets turned into an arraylist
     */
    public static ArrayList<Account>  cleanerLeader(String js) {
        ArrayList<Account> accs = new ArrayList<>();
        JSONObject json = new JSONObject(js);
        String list = json.getString("list");
        Scanner sc = new Scanner(list).useDelimiter(", ");
        String name ;
        String mail ;
        double point;
        while (sc.hasNext()) {
            Scanner sc2 = new Scanner(sc.next()).useDelimiter(" ");
            name =  sc2.next();
            mail =  sc2.next();
            point =  sc2.nextDouble();
            accs.add(new Account(name,mail,point));
        }
        return accs;
    }

    /**
     * Helps in gui gets friend list from json to a array list.
     * @param js json that has friends with points
     * @return the new list of accounts
     */
    public static ArrayList<Account>  cleanerFriendP(String js) {
        ArrayList<Account> accs = new ArrayList<>();
        JSONObject json = new JSONObject(js);
        String list = json.getString("friends");
        Scanner sc = new Scanner(list).useDelimiter(", ");
        String name ;
        String mail ;
        double point;
        while (sc.hasNext()) {
            Scanner sc2 = new Scanner(sc.next()).useDelimiter(" ");
            name =  sc2.next();
            mail =  sc2.next();
            point =  sc2.nextDouble();
            accs.add(new Account(name ,mail ,point));
        }
        return accs;
    }

    /**
     * Helps in gui gets waiting friend list from json to a array list.
     * @param js json that has friends
     * @return the new list of accounts
     */
    public static ArrayList<Account>  cleanerFriendW(String js) {
        ArrayList<Account> accs = new ArrayList<>();
        JSONObject json = new JSONObject(js);
        String list = json.getString("waiting");
        Scanner sc = new Scanner(list).useDelimiter(", ");
        String name ;
        String mail ;
        double point;
        while (sc.hasNext()) {
            Scanner sc2 = new Scanner(sc.next()).useDelimiter(" ");
            name =  sc2.next();
            mail =  sc2.next();
            point =  0;
            accs.add(new Account(name,mail,point));
        }
        return accs;
    }

    /**
     * Helps in gui gets waiting friend list from json to a array list.
     * @param js json that has friends
     * @return the new list of accounts
     */
    public static ArrayList<Account>  cleanerFriendA(String js) {
        ArrayList<Account> accs = new ArrayList<>();
        JSONObject json = new JSONObject(js);
        String list = json.getString("approve");
        Scanner sc = new Scanner(list).useDelimiter(", ");
        String name = "";
        String mail = "";
        double point;
        while (sc.hasNext()) {
            Scanner sc2 = new Scanner(sc.next()).useDelimiter(" ");
            mail =  sc2.next();
            name = "null";
            point =  -1;
            accs.add(new Account(name,mail,point));
        }
        return accs;
    }

}
