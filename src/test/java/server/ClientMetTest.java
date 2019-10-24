package server;

import db.Account;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

public class ClientMetTest {

    @Test
    public void testsignUp() throws IOException {
        String answer = ClientMet.signUp("test" , "test", "test");
        System.out.println(answer);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(answer.equals("{\"command\" : \"pass signup\"}"));
    }




    @Test
    public void testloginMail() throws IOException {
        ClientMet.signUp("test" , "test", "test");

        String answer = ClientMet.logIn("test" , "test", "test");

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(answer.equals("{\"command\" : \"pass\"}"));
    }




    @Test
    public void testgetQuestions() throws IOException {

        ClientMet.signUp("test" , "test", "test");

        String ans ="{\"command\" : \"scores of the mail\", \"points\" : \"50.0\", \"vegan\" : \"1.0\", \"bike\" : \"0.0\", \"walk\" : \"0.0\", \"trans\" : \"0.0\", \"local\" : \"0.0\", \"heating\" : \"0.0\", \"solar\" : \"0.0\", \"donations\" : \"0.0\"}";
        System.out.println(ClientMet.addVeganMeal("test", 1));

        String answer = ClientMet.getQuestions("test");
        System.out.println(answer);

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(answer.equals(ans));

    }

    @Test
    public void testsendFriendReq() throws IOException {

        ClientMet.signUp("test" , "test", "test");
        ClientMet.signUp("test2" , "test2", "test2");


        String ans ="{\"command\" : \"request sent\"}";

        String ans1 = ClientMet.sendFriendReq("test", "test2");

        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testapproveFriend() throws IOException {

        ClientMet.signUp("test" , "test", "test");
        ClientMet.signUp("test2" , "test2", "test2");


        String ans ="{\"command\" : \"added to friend\"}";
        ClientMet.sendFriendReq("test", "test2");

        String ans1 = ClientMet.approveFriend("test", "test2");

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));


        assert(ans1.equals(ans));
    }

    @Test
    public void testgetLeader() throws IOException {

        ClientMet.signUp("test" , "test", "test");
        ClientMet.signUp("test2" , "test2", "test2");

        System.out.println(ClientMet.addVeganMeal("test" , 1));

        String ans = "{\"command\" : \"got list\", \"list\" : \" test test 50.0, test2 test2 0.0\"}";

        String ans1 = ClientMet.getLeader();
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));
        assert(ans1.equals(ans));
    }


    @Test
    public void testgetFriends() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));
        System.out.println(ClientMet.signUp("test2" , "test2", "test2"));
        System.out.println(ClientMet.sendFriendReq("test" , "test2"));
        System.out.println(ClientMet.approveFriend("test" , "test2"));

        String ans = "{\"command\" : \"all friends\", \"friends\" : \"test test true, test test2 true, \", \"waiting\" : \"\", \"approve\" : \"\"}";
        String ans1 = ClientMet.getFriends("test") ;
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetFriendsPoints() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));
        System.out.println(ClientMet.signUp("test2" , "test2", "test2"));
        System.out.println(ClientMet.sendFriendReq("test" , "test2"));
        System.out.println(ClientMet.approveFriend("test" , "test2"));

        String ans = "{\"command\" : \"friendsPoints\", \"friends\" : \"test test 0.0, test2 test2 0.0, \"}";
        String ans1 = ClientMet.getFriendsPoints("test") ;
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveFriend() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));
        System.out.println(ClientMet.signUp("test2" , "test2", "test2"));
        System.out.println(ClientMet.sendFriendReq("test" , "test2"));
        System.out.println(ClientMet.approveFriend("test" , "test2"));

        String ans = "{\"command\" : \"friend removed\"}";

        String ans1 = ClientMet.removeFriend("test","test2");

        System.out.println(ClientMet.removeAcc("test", "test"));
        System.out.println(ClientMet.removeAcc("test2", "test2"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddVeganMeal() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added vegan meal\", \"total meals\" : \"1.0\" , \"score\" : \"50.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addVeganMeal("test", 1);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddMeal() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added Meal\"}";
        String ans1 = ClientMet.addMeal("test" , "testMeal" , 44);
        System.out.println(ans1);
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetMeals() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"got meals\", \"meals\" : \"testMeal 40.0, \" }";
        ClientMet.addMeal("test" , "testMeal" , 40);
        String ans1 = ClientMet.getMeals("test");
        System.out.println(ans1);
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveMeal() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"removed Meal\"}";
        ClientMet.addMeal("test" , "testMeal" , 44);
        String ans1 = ClientMet.removeMeal("test" , "testMeal" );
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetMealsCleaner() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"got meals\", \"meals\" : \"testMeal 40.0, \" }";
        ClientMet.addMeal("test" , "testMeal" , 40);
        ArrayList<db.Meal> meals = ClientMet.cleanerMeals(ClientMet.getMeals("test"));
        System.out.println(meals.get(0).getMeal());
        System.out.println(meals.get(0).getPrice());

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(meals.get(0).getMeal().equals("testMeal") && meals.get(0).getPrice() == 40.0);
    }

    @Test
    public void testDonate() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"thanks for donation\", \"total donations\" : \"100.0\" , \"score\" : \"1000.0\" , \"newReward\" : \"true\"}";
        String ans1 = ClientMet.donate("test", 100);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testheat() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added heat\", \"totalPointsHeating\" : \"2.26027\" , \"score\" : \"2.26027\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addHeat("test", 10,0);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));

    }

    @Test
    public void testTime() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans1 = ClientMet.updateTime("test", "heating", "heatingTime" , System.currentTimeMillis(), 10);
        System.out.println(ans1 + " as ");

        JSONObject js = new JSONObject(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(js.getString("command").equals("doneTime"));

    }

    @Test
    public void testaddKmTrain1() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addTrain("test", 1);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmTrain2() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"20.0\" , \"score\" : \"20.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addTrain("test", 60);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmTrain3() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"30.0\" , \"score\" : \"30.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addTrain("test", 110);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus1() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addBus("test", 10);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus2() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addBus("test",  10);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus3() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"20.0\" , \"score\" : \"20.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addBus("test", 150);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmWalk() throws IOException {
        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"3.0\" , \"score\" : \"3.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addWalk("test", 10);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBike() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"3.0\" , \"score\" : \"3.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addBike("test", 10);

        System.out.println(ans1);
        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testaddLocal() throws IOException {
        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added local shop\", \"total local\" : \"1.0\" , \"score\" : \"50.0\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addLocal("test", 1);
        System.out.println(ans1);

        System.out.println(ClientMet.removeAcc("test", "test"));
        assert(ans1.equals(ans));
    }

    ///////////
    @Test
    public void testaddSolar() throws IOException {

        System.out.println(ClientMet.signUp("test" , "test", "test"));

        String ans = "{\"command\" : \"added solar\", \"totalPointsSolar\" : \"9.31507\" , \"score\" : \"9.31507\" , \"newReward\" : \"false\"}";
        String ans1 = ClientMet.addSolar("test", 20 , System.currentTimeMillis());
        System.out.println(ans1);

        ClientMet.removeAcc("test","test");
        assert(ans1.equals(ans));
    }

    @Test
    public void testrewards() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));
        System.out.println(ClientMet.addVeganMeal("test" , 11));

        String ans = "{\"command\" : \"got rewards\", \"vegan10\" : \"true\", \"vegan100\" : \"false\", \"local10\" : \"false\", \"local100\" : \"false\", \"heating100\" : \"false\", \"solar100\" : \"false\", \"donate100\" : \"false\", \"public_trans100\" : \"false\", \"bike100\" : \"false\", \"walk100\" : \"false\"}";
        String ans1 = ClientMet.getRewards("test");

        System.out.println(ans1);
        ClientMet.removeAcc("test","test");

        assert(ans1.equals(ans));
    }

    @Test
    public void testrewards2() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));
        System.out.println(ClientMet.addVeganMeal("test" , 110));

        String ans = "{\"command\" : \"got rewards\", \"vegan10\" : \"true\", \"vegan100\" : \"true\", \"local10\" : \"false\", \"local100\" : \"false\", \"heating100\" : \"false\", \"solar100\" : \"false\", \"donate100\" : \"false\", \"public_trans100\" : \"false\", \"bike100\" : \"false\", \"walk100\" : \"false\"}";
        String ans1 = ClientMet.getRewards("test");

        ClientMet.removeAcc("test","test");

        assert(ans1.equals(ans));
    }


    @Test
    public void testremoveAcc1() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));

        String ans = "{\"command\" : \"rip acc\"}";
        String ans1 = ClientMet.removeAcc("test","test");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testCealerLeader() throws IOException {

        ArrayList<Account> acc = ClientMet.cleanerLeader("{\"command\" : \"got list\", \"list\" : \" test test 50, test2 test2 0\"}");
        System.out.println(acc.get(0).getMail());
        System.out.println(acc.get(0).getUsername());
        System.out.println(acc.get(1).getPoint());
        assert(acc.get(0).getMail().equals("test"));
    }

    @Test
    public void testCealerFriendsP() throws IOException {

        ArrayList<Account> acc = ClientMet.cleanerFriendP("{\"command\" : \"friendsPoints\", \"friends\" : \"test2 test2 0, \"}");
        System.out.println(acc.get(0).getMail());
        System.out.println(acc.get(0).getUsername());
        System.out.println(acc.get(0).getPoint());
        assert(acc.get(0).getMail().equals("test2"));
    }

    @Test
    public void testCealerFriendsW() throws IOException {

        ArrayList<Account> acc = ClientMet.cleanerFriendW("{\"command\" : \"all friends\", \"friends\" : \"test2 test2 true, \", \"waiting\" : \"test3 test3 false, \"}");
        System.out.println(acc.get(0).getMail());
        System.out.println(acc.get(0).getUsername());
        System.out.println(acc.get(0).getPoint());
        assert(acc.get(0).getMail().equals("test3"));
    }

    @Test
    public void testCealerFriendsA() throws IOException {

        ArrayList<Account> acc = ClientMet.cleanerFriendA("{\"command\" : \"all friends\", \"friends\" : \"test2 test2 true, \", \"waiting\" : \"test3 test3 false, \" , \"approve\" : \"test3 test2 false, \"}");
        System.out.println(acc.get(0).getMail());
        System.out.println(acc.get(0).getUsername());
        System.out.println(acc.get(0).getPoint());
        assert(acc.get(0).getMail().equals("test3"));
    }


    @Test
    public void testChangePass() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));
        System.out.println(ClientMet.addVeganMeal("test" , 110));

        String ans = "{\"command\" : \"changed\"}";
        String ans1 = ClientMet.changePass("test", "aaa");

        System.out.println(ans1);
        ClientMet.removeAcc("test","aaa");

        assert(ans1.equals(ans));
    }

    @Test
    public void testdailyupdate() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));
        double time = System.currentTimeMillis();
        System.out.println(ClientMet.addHeat("test" , 40 , time));

        String ans = "{\"command\" : \"got dailys\", \"solar\" : \"0.0\", \"solarM2\" : \"0.0\", \"solarTime\" : \"0.0\", \"solarLate\" : \"0.0\", \"heating\" : \"9.0411\", \"heatingTemp\" : \"40.0\", \"heatingTime\" : \"" + time + "\", \"heatingLate\" : \"0.0\" }";
        String ans1 = ClientMet.getDailyUpdate("test");

        System.out.println(ans1);
        ClientMet.removeAcc("test","test");

        assert(ans1.equals(ans));
    }

    @Test
    public void testgetCons() throws IOException {

        System.out.println(ClientMet.signUp("test", "test" , "test"));
        System.out.println(ClientMet.addHeat("test" , 40 , 500));

        String ans = "{ \"SolarP\" : \"0.4657534246575342\" ,\"HeatP\" : \"0.9041095890410958\"}";
        String ans1 = ClientMet.getConstants();

        System.out.println(ans1);
        ClientMet.removeAcc("test","test");

        assert(ans1.equals(ans));
    }
}
