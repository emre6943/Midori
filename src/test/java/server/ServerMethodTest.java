package server;

import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.junit.Assert.assertTrue;

public class ServerMethodTest {

    static Client c = new Client();


    @Test
    public void testRecivenotKnown() throws IOException {
        assertTrue(Server.handler("{\"command\" : \"test\" }").equals("test get this"));

    }

    @Test
    public void testConstants() throws IOException {
        Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");

        String ans = Server.handler("{\"command\" : \"getConstants\" }");
        System.out.println(ans);

        System.out.println(Server.handler("{\"command\" : \"removeAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(ans.equals("{ \"SolarP\" : \"0.4657534246575342\" ,\"HeatP\" : \"0.9041095890410958\"}"));

    }

    @Test
    public void testManualUpdate() throws IOException {
        Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");

        double time = System.currentTimeMillis();

        String ans = Server.handler("{\"command\" : \"EntryUpdate\" , \"mail\" : \""
            + "test" + "\" , \"what\" : \""
            + "solar" + "\" , \"timeStr\" : \""
            + "solarTime" + "\" , \"points\" : \""
            + 20 + "\" , \"time\" : \""
            + System.currentTimeMillis() + "\"}");
        System.out.println(ans);

        JSONObject js =  new JSONObject(ans);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(js.getString("command").equals("doneTime"));

    }

    @Test
    public void testManualUpdateFail() throws IOException {

        double time = System.currentTimeMillis();

        String ans = Server.handler("{\"command\" : \"EntryUpdate\" , \"mail\" : \""
            + "test" + "\" , \"what\" : \""
            + "solar" + "\" , \"timeStr\" : \""
            + "solarTime" + "\" , \"points\" : \""
            + 20 + "\" , \"time\" : \""
            + time + "\"}");
        System.out.println(ans);

        assertTrue(ans.equals("{\"command\" : \"something went wrong\"}"));

    }

    @Test
    public void testremoveFail() throws IOException {
        String answer = Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);

        assert(answer.equals("{\"command\" : \"something went wrong in deleting\"}"));
    }

    @Test
    public void testgetwut() throws IOException {
        String answer = Server.handler("{\"command\" : \"getwut\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);
        String ans = "wut";
        assert(answer.equals(ans));
    }

    @Test
    public void testgetwut2() throws IOException {
        String answer = Server.handler("{\"command\" : \"Entrywut\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);
        String ans = "wut";
        assert(answer.equals(ans));
    }

    @Test
    public void testgetwut3() throws IOException {
        String answer = Server.handler("{\"command\" : \"Friendwut\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);
        String ans = "wut";
        assert(answer.equals(ans));
    }

    @Test
    public void testsignUp() throws IOException {
        String answer = Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(answer.equals("{\"command\" : \"pass signup\"}"));
    }


    @Test
    public void testsignUpFailed() throws IOException {
        Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");

        String answer = Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }");
        System.out.println(answer);
        String ans1 = "{\"command\" : \"something went wrong in sign up\"}";

        HttpURLConnection conn = c.connectServer(c.path);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(ans1.equals(answer));
    }

    @Test
    public void testloginMailFailed() throws IOException {

        String answer = Server.handler("{\"command\" : \"login\", \"mail\" : \"test\", \"username\" : \"none\", \"pass\" : \"test\" }");
        System.out.println(answer);
        String ans = "{\"command\" : \"wrong pass\"}";

        assert(answer.equals(ans));
    }

    @Test
    public void testloginMail() throws IOException {
        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String answer = Server.handler("{\"command\" : \"login\", \"mail\" : \"test\", \"username\" : \"none\", \"pass\" : \"test\" }");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(answer.equals("{\"command\" : \"pass\"}"));
    }

    @Test
    public void testloginName() throws IOException {
        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String answer = Server.handler("{\"command\" : \"login\", \"mail\" : \"none\", \"username\" : \"test\", \"pass\" : \"test\" }");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(answer.equals("{\"command\" : \"pass\"}"));
    }

    @Test
    public void testloginNameFailed() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));


        String answer = Server.handler("{\"command\" : \"login\", \"mail\" : \"none\", \"username\" : \"test\", \"pass\" : \"tesasat\" }");
        String ans = "{\"command\" : \"wrong pass\"}";


        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(answer.equals(ans));
    }

    @Test
    public void testloginwrong() throws IOException {
        assert(Server.handler("{\"command\" : \"login\", \"mail\" : \"test\",\"username\" : \"none\", \"pass\" : \"wrong\" }").equals("{\"command\" : \"wrong pass\"}"));
    }

    @Test
    public void testgetQuestionsoftheMail() throws IOException {
        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));
        String ans ="{\"command\" : \"scores of the mail\", \"points\" : \"50.0\", \"vegan\" : \"1.0\", \"bike\" : \"0.0\", \"walk\" : \"0.0\", \"trans\" : \"0.0\", \"local\" : \"0.0\", \"heating\" : \"0.0\", \"solar\" : \"0.0\", \"donations\" : \"0.0\"}";

        System.out.println(Server.handler("{'command' : \"EntryVeganMeal\", \"mail\" : \"test\", \"pass\" : \"test\" , \"number\" : 1  }"));

        String answer = Server.handler("{\"command\" : \"getQuestions\", \"mail\" : \"test\" }");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(answer.equals(ans));

    }

    @Test
    public void testgetQuestionsoftheMailFailed() throws IOException {
        String ans ="{\"command\" : \"couldnt get questions\"}";

        String answer = Server.handler("{\"command\" : \"getQuestions\", \"mail\" : \"test\" }");
        System.out.println(answer);
        assert(answer.equals(ans));

    }

    @Test
    public void testsendFriendReq() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        String ans ="{\"command\" : \"request sent\"}";

        String ans1 = Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testsendFriendReqFailed() throws IOException {
        String ans ="{\"command\" : \"request failed\"}";

        String ans1 = Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testapproveFriend() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        String ans ="{\"command\" : \"added to friend\"}";

        String ans1 = Server.handler("{\"command\" : \"approveFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testapproveFriendFailed() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        String ans ="{\"command\" : \"not approved\"}";

        String ans1 = Server.handler("{\"command\" : \"approveFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testgetLeader() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        Server.handler("{\"command\" : \"EntryVeganMeal\", \"mail\" : \"test\",\"number\" : 1  }");

        String ans = "{\"command\" : \"got list\", \"list\" : \" test test 50.0, test2 test2 0.0\"}";

        String ans1 = Server.handler("{\"command\" : \"getLeaderBoard\"}");
        System.out.println(ans1);


        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));
        assert(ans1.equals(ans));
    }



    @Test
    public void testgetFriends() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"approveFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        String ans = "{\"command\" : \"all friends\", \"friends\" : \"test test true, test test2 true, \", \"waiting\" : \"\", \"approve\" : \"\"}";

        String ans1 = Server.handler("{\"command\" : \"FriendList\", \"mail\" : \"test\"}") ;

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetFriendsFailed() throws IOException {
        String ans = "{\"command\" : \"couldnt get friedns\"}";

        String ans1 = Server.handler("{\"command\" : \"FriendList\", \"mail\" : \"test\"}") ;

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testgetFriendsPoints() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"approveFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        String ans = "{\"command\" : \"friendsPoints\", \"friends\" : \"test test 0.0, test2 test2 0.0, \"}";

        String ans1 = Server.handler("{\"command\" : \"FriendsPoints\", \"mail\" : \"test\" }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetFriendsPointsFailed() throws IOException {

        String ans = "{\"command\" : \"couldnt get friednsPoints\"}";

        String ans1 = Server.handler("{\"command\" : \"FriendsPoints\", \"mail\" : \"test\" }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveFriend() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test2\", \"username\" : \"test2\",  \"pass\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"sentFriendReq\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        System.out.println(Server.handler("{\"command\" : \"approveFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }"));

        String ans = "{\"command\" : \"friend removed\"}";

        String ans1 = Server.handler("{\"command\" : \"removeFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");


        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test2\" , \"pass\" : \"test2\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveFriendFailed() throws IOException {
        String ans = "{\"command\" : \"removing failed\"}";

        String ans1 = Server.handler("{\"command\" : \"removeFriend\", \"mail_from\" : \"test\", \"mail_to\" : \"test2\" }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddVeganMeal() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added vegan meal\", \"total meals\" : \"1.0\" , \"score\" : \"50.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{\"command\" : \"EntryVeganMeal\", \"mail\" : \"test\",\"number\" : 1  }");
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddVeganMealFail() throws IOException {

        String ans = "{\"command\" : \"something went wrong\"}";

        String ans1 = Server.handler("{\"command\" : \"EntryVeganMeal\", \"mail\" : \"test\",\"number\" : 1  }");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testDonate() throws IOException {
        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"thanks for donation\", \"total donations\" : \"100.0\" , \"score\" : \"1000.0\" , \"newReward\" : \"true\"}";

        String ans1 = Server.handler("{\"command\" : \"EntryDonate\", \"mail\" : \"test\",\"money\" : 100  }");
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testDonateFail() throws IOException {
        String ans = "{\"command\" : \"something went wrong in donations\"}";

        String ans1 = Server.handler("{\"command\" : \"EntryDonate\", \"mail\" : \"test\",\"money\" : 100  }");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testheat() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added heat\", \"totalPointsHeating\" : \"9.0411\" , \"score\" : \"9.0411\" , \"newReward\" : \"false\"}";

        double time = System.currentTimeMillis();
        String ans1 = Server.handler("{'command' : \"EntryHeating\", \"mail\" : \""
            + "test" + "\", \"temp\" : "
            + 40.0 + ", \"time\" : "
            + time + "}");
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));

    }

    @Test
    public void testheatFail() throws IOException {

        String ans = "{\"command\" : \"something went wrong in heating\"}";

        double time = System.currentTimeMillis();
        String ans1 = Server.handler("{'command' : \"EntryHeating\", \"mail\" : \""
            + "test" + "\", \"temp\" : "
            + 40.0 + ", \"time\" : "
            + time + "}");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmWalk() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"3.0\" , \"score\" : \"3.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"walk\", \"mail\" : \"test\", \"distance\" : 10  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmTrain1() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"train\", \"mail\" : \"test\", \"distance\" : 1  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmTrain2() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"20.0\" , \"score\" : \"20.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"train\", \"mail\" : \"test\", \"distance\" : 80  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmTrain3() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"30.0\" , \"score\" : \"30.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"train\", \"mail\" : \"test\", \"distance\" : 106  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus1() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"bus\", \"mail\" : \"test\", \"distance\" : 1  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus2() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"10.0\" , \"score\" : \"10.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"bus\", \"mail\" : \"test\", \"distance\" : 40  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBus3() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"20.0\" , \"score\" : \"20.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"bus\", \"mail\" : \"test\", \"distance\" : 60  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmWalkFail() throws IOException {
        String ans = "{\"command\" : \"something went wrong in notCar\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"walk\", \"mail\" : \"test\", \"distance\" : 10  }");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBike() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added km\", \"Km\" : \"3.0\" , \"score\" : \"3.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"bike\", \"mail\" : \"test\", \"distance\" : 10  }");

        System.out.println(ans1);
        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddKmBikeFail() throws IOException {
        String ans = "{\"command\" : \"something went wrong in notCar\"}";

        String ans1 = Server.handler("{'command' : \"EntryNotCar\", \"type\" : \"bike\", \"mail\" : \"test\", \"distance\" : 10  }");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddLocal() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added local shop\", \"total local\" : \"1.0\" , \"score\" : \"50.0\" , \"newReward\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"EntryLocalProduct\", \"mail\" : \"test\", \"number\" : 1 }");

        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddLocalFail() throws IOException {

        String ans = "{\"command\" : \"something went wrong in localhandler\"}";

        String ans1 = Server.handler("{'command' : \"EntryLocalProduct\", \"mail\" : \"test\", \"number\" : 1 }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddSolar() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added solar\", \"totalPointsSolar\" : \"4.65753\" , \"score\" : \"4.65753\" , \"newReward\" : \"false\"}";

        double time = System.currentTimeMillis();
        String ans1 = Server.handler("{\"command\" : \"EntrySolar\", \"mail\" : \""
            + "test" + "\", \"m2\" : \""
            + 10 + "\", \"time\" : \""
            + time + "\"}");
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddSolarFail() throws IOException {

        String ans = "{\"command\" : \"something went wrong in solar\"}";

        double time = System.currentTimeMillis();
        String ans1 = Server.handler("{\"command\" : \"EntrySolar\", \"mail\" : \""
            + "test" + "\", \"m2\" : \""
            + 10 + "\", \"time\" : \""
            + time + "\"}");
        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testrewards1() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{'command' : \"EntryVeganMeal\", \"mail\" : \"test\", \"number\" : 11 }"));

        String ans = "{\"command\" : \"got rewards\", \"vegan10\" : \"true\", \"vegan100\" : \"false\", \"local10\" : \"false\", \"local100\" : \"false\", \"heating100\" : \"false\", \"solar100\" : \"false\", \"donate100\" : \"false\", \"public_trans100\" : \"false\", \"bike100\" : \"false\", \"walk100\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"getRewards\", \"mail\" : \"test\"}");

        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testdailyUpdate() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{'command' : \"EntryVeganMeal\", \"mail\" : \"test\", \"number\" : 11 }"));

        String ans = "{\"command\" : \"got dailys\", \"solar\" : \"0.0\", \"solarM2\" : \"0.0\", \"solarTime\" : \"0.0\", \"solarLate\" : \"0.0\", \"heating\" : \"0.0\", \"heatingTemp\" : \"0.0\", \"heatingTime\" : \"0.0\", \"heatingLate\" : \"0.0\" }";

        String ans1 = Server.handler("{ 'command' : \"getDailyUpdate\", \"mail\" : \"test\" }");

        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testdailyUpdate2() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        double time = System.currentTimeMillis();
        System.out.println(Server.handler("{'command' : \"EntryHeating\", \"mail\" : \"test\", \"temp\" : "
            + 22 + ", \"time\" : "
            + time + "}"));

        String ans = "{\"command\" : \"got dailys\", \"solar\" : \"0.0\", \"solarM2\" : \"0.0\", \"solarTime\" : \"0.0\", \"solarLate\" : \"0.0\", \"heating\" : \"4.9726\", \"heatingTemp\" : \"22.0\", \"heatingTime\" : \"" + time + "\", \"heatingLate\" : \"0.0\" }";

        String ans1 = Server.handler("{ 'command' : \"getDailyUpdate\", \"mail\" : \"test\" }");

        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testrewards2() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        System.out.println(Server.handler("{'command' : \"EntryVeganMeal\", \"mail\" : \"test\", \"number\" : 110}"));

        String ans = "{\"command\" : \"got rewards\", \"vegan10\" : \"true\", \"vegan100\" : \"true\", \"local10\" : \"false\", \"local100\" : \"false\", \"heating100\" : \"false\", \"solar100\" : \"false\", \"donate100\" : \"false\", \"public_trans100\" : \"false\", \"bike100\" : \"false\", \"walk100\" : \"false\"}";

        String ans1 = Server.handler("{'command' : \"getRewards\", \"mail\" : \"test\"}");

        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }


    @Test
    public void testremoveAcc1() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"rip acc\"}";

        String ans1 = Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveAcc2() throws IOException {

       String ans = "{\"command\" : \"something went wrong in deleting\"}" ;

        String ans1 = Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }");

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testChangePass() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"changed\"}";

        String ans1 = Server.handler("{\"command\" : \"getPass\", \"mail\" : \""
            + "test" + "\" , \"newpass\" : \""
            + "aaa" + "\"}");

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"aaa\"  }"));

        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testChangePassFail() throws IOException {

        String ans = "{\"command\" : \"something went wrong in changing pass\"}";

        String ans1 = Server.handler("{\"command\" : \"getPass\", \"mail\" : \""
            + "test" + "\" , \"newpass\" : \""
            + "aaa" + "\"}");


        System.out.println(ans1);

        assert(ans1.equals(ans));
    }

    @Test
    public void testaddMeal() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"added Meal\"}";
        String ans1 =Server.handler("{\"command\" : \"addMeal\", \"mail\" : \"test\", \"meal\" : \"testMeal\",  \"price\" : \"40\" }");
        System.out.println(ans1);
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testMealWut() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "wut Meals";
        String ans1 =Server.handler("{\"command\" : \"wutMeal\", \"mail\" : \"test\", \"meal\" : \"testMeal\",  \"price\" : \"40\" }");
        System.out.println(ans1);
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));
        assert(ans1.equals(ans));
    }

    @Test
    public void testgetMeals() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"got meals\", \"meals\" : \"testMeal 40.0, \" }";
        Server.handler("{\"command\" : \"addMeal\", \"mail\" : \"test\", \"meal\" : \"testMeal\",  \"price\" : \"40\" }");
        String ans1 = Server.handler("{\"command\" : \"takeMeals\", \"mail\" : \"test\"}");
        System.out.println(ans1);
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"test\"  }"));

        assert(ans1.equals(ans));
    }

    @Test
    public void testremoveMeal() throws IOException {

        System.out.println(Server.handler("{\"command\" : \"signup\", \"mail\" : \"test\", \"username\" : \"test\",  \"pass\" : \"test\" }"));

        String ans = "{\"command\" : \"removed Meal\"}";
        Server.handler("{\"command\" : \"addMeal\", \"mail\" : \"test\", \"meal\" : \"testMeal\",  \"price\" : \"40\" }");
        String ans1 = Server.handler("{\"command\" : \"removeMeal\", \"mail\" : \"test\", \"meal\" : \"testMeal\"}");
        System.out.println(ans1);

        System.out.println(Server.handler("{\"command\" : \"getremoveAcc\", \"mail\" : \"test\" , \"pass\" : \"aaa\"  }"));
        assert(ans1.equals(ans));
    }


}
