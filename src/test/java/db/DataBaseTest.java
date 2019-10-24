package db;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class DataBaseTest {


    static final String link = "jdbc:mysql://localhost:4444/USERS?autoReconnect=true&useSSL=false";

    @Test
    public void testcreateDB() throws SQLException , ClassNotFoundException {
        Connection conn = null;
        conn = DataBase.createDb("TEST");

        assert(DataBase.closeConnect(conn));
    }


    @Test
    public void testRewardUpdaterV10() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "vegan" , 10 , conn);

        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "vegan10" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterB100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "bike" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "bike100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterW100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "walk" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "walk100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterPT100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "public_trans" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "public_trans100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterV100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "vegan" , 101 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "vegan100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterL10() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "local" , 11 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "local10" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterL100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "local" , 101 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "local100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterH100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "heating" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "heating100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterS100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "solar" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "solar100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testRewardUpdaterD100() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test", "test" ,"test" , conn);
        DataBase.addToQuestion("test" , "donations" , 100 , conn);
        DataBase.rewardUpdater("test" , conn);
        DataBase.recentRewardUpdate("test" , conn);

        boolean ans = DataBase.getReward("test" , "donate100" , conn);

        DataBase.removeAcc("test","test" ,conn);
        assert(ans);
    }

    @Test
    public void testgetAccs() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("first", "first" ,"first", conn);
        DataBase.addAccount("sec", "sec" ,"sec", conn);

        String ans1 = DataBase.getAccounts(conn);
        System.out.println(ans1);
        String ans = "first first 8b04d5e3775d298e78455efc5ca404d5 false\n" +
            "sec sec 74459ca3cf85a81df90da95ff6e7a207 false\n";

        DataBase.removeAcc("first" , "first" , conn);
        DataBase.removeAcc("sec" , "sec" , conn);

        assert(ans.equals(ans1));

    }

    @Test
    public void testGetMeal() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test1","test1","test1", conn);
        DataBase.addAccount("test2","test2","test2", conn);

        DataBase.addMeal("test1", "meal1" ,42, conn);
        DataBase.addMeal("test2", "meal2" ,69, conn);

        String ans1 = DataBase.getMeals("test1",conn);
        String ans2 = DataBase.getMeals("test2", conn);
        System.out.println(ans1);
        String ans = "meal1 42.0, ";
        System.out.println(ans);


        DataBase.removeMeal("test1" , "meal1" , conn);
        DataBase.removeMeal("test2" , "meal2" , conn);

        DataBase.removeAcc("test1" , "test1" , conn);
        DataBase.removeAcc("test2" , "test2" , conn);

        assert (ans.equals(ans1));
    }

    @Test
    public void testAddMeal() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test1","test1","test1", conn);
        DataBase.addAccount("test2","test2","test2", conn);

        DataBase.addMeal("test1", "meal1" ,42, conn);
        DataBase.addMeal("test2", "meal2" ,69, conn);

        String ans1 = DataBase.getMeals("test1",conn);
        String ans2 = DataBase.getMeals("test2", conn);
        System.out.println(ans1);
        String ans = "meal1 42.0, ";

        System.out.println(ans1);

        DataBase.removeMeal("test1" , "meal1" , conn);
        DataBase.removeMeal("test2" , "meal2" , conn);

        DataBase.removeAcc("test1" , "test1" , conn);
        DataBase.removeAcc("test2" , "test2" , conn);

        assert(ans.equals(ans1));
    }

    @Test
    public void testaddAccFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("first", "first" ,"first", conn);

        boolean ans = DataBase.addAccount("first", "first" ,"first", conn);

        DataBase.removeAcc("first" , "first" , conn);

        assert(!ans);

    }

    @Test
    public void testGetRewardPast() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.getRewardPast("aaaa" , "nope" , conn));
    }

    @Test
    public void testRemoveAccFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.removeAcc("aaaa" , "nope" , conn));
    }

    @Test
    public void testRewardUpdaterFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.rewardUpdater("aaaa" , conn));
    }

    @Test
    public void testgetRewardFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.getReward("aaaa" ,"aaa",  conn));
    }

    @Test
    public void testgetUserNameFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(DataBase.getUserName("aaaa", conn).equals(""));
    }

    @Test
    public void testaddAccRewardsFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addAccRewards("aaaa", conn));
    }

    @Test
    public void testaddAccRewardsPastFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addAccRewardsPast("aaaa", conn));
    }

    @Test
    public void testaddMealFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addMeal("aaaa", "aa", 20, conn));
    }

    @Test
    public void testremoveMealFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.removeMeal("aaaa", "aa", conn));
    }

    @Test
    public void testaddAccQuestionsFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addAccQuestion("aaaa", conn));
    }

    @Test
    public void testcolseConnectionFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.closeConnect(null));
    }

    @Test
    public void testChangeRewardFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.changeReward("aaaaaa", "asasa" , true ,conn));
    }

    @Test
    public void testChangePassFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.changePass("aaaaaa" , "nope" , conn));
    }

    @Test
    public void testStatusFriendsFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.statusFriend("aa" , "nope" ,false, conn));

    }

    @Test
    public void testLogedStatusFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.logedStatus("aa" , "a" ,false, conn));

    }

    @Test
    public void testaddFriendFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addFriend("aa" , "a", conn));

    }


    @Test
    public void testaddPointsFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addPoints("aa", 44, conn));

    }

    @Test
    public void testAddToQFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.addToQuestion("aa", "vegan", 44, conn));

    }

    @Test
    public void testgetAmountQFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(DataBase.getAmountQuestion("aaaa", "vegan", conn) == -1);

    }

    @Test
    public void testgetPointsFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(DataBase.getPoints("aa",conn) == -1);

    }

    @Test
    public void testleaderBoard() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("first", "first" ,"first", conn);
        DataBase.addPoints("first" , 999 , conn);

        ResultSet rs = DataBase.leaderBoard(conn);
        boolean ans = false;
        if(rs.next()) {
            String name = rs.getString("user_name");
            String mail = rs.getString("mail");
            double points = rs.getDouble("points");
            System.out.println(mail);
            System.out.println(name);
            System.out.println(points);
            ans = (mail.equals("first") && (points == 999));
        }
        DataBase.removeAcc("first" , "first" , conn);
        assert(ans);

    }

    @Test
    public void testaddAccount() throws SQLException {
        Connection conn = null;
        try {
            conn = DataBase.connect(link);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        boolean ans = DataBase.addAccount("tsss", "test1" ,"test1", conn);
        DataBase.removeAcc("tsss" , "test1" , conn);
        assert(ans);

    }


    @Test
    public void testcheckUsername() throws SQLException {
        Connection conn = null;
        try {
            conn = DataBase.connect(link);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DataBase.addAccount("testname" , "testname", "testname" , conn );
        boolean ans = DataBase.checkUsername("testname", conn);
        DataBase.removeAcc("testname","testname",conn);

        assert(ans);
    }

    @Test
    public void testcheckAccUsername() throws SQLException {
        Connection conn = null;
        try {
            conn = DataBase.connect(link);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DataBase.addAccount("test2", "test2" ,"test2", conn);
        boolean ans = DataBase.checkAccName("test2", "test2", conn);
        DataBase.removeAcc("test2" , "test2" , conn);
        assert(ans);

    }

    @Test
    public void testcheckAccMail() throws SQLException {
        Connection conn = null;
        try {
            conn = DataBase.connect(link);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DataBase.addAccount("test3", "test3" ,"test3", conn);
        boolean ans = DataBase.checkAccMail("test3","test3" ,conn);
        DataBase.removeAcc("test3" , "test3" , conn);
        assert(ans);
    }

    @Test
    public void testpassChange() throws SQLException {
        Connection conn = null;
        try {
            conn = DataBase.connect(link);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DataBase.addAccount("test4", "test4" ,"test4", conn);
        boolean ans = DataBase.changePass("test4", "newtest", conn);
        DataBase.removeAcc("test4" , "newtest" , conn);

        assert(ans);
    }

    @Test
    public void testaddFreind1() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test55", "test55" ,"test55", conn);
        DataBase.addAccount("test65", "test65" ,"test65", conn);
        boolean ans = DataBase.addFriend("test55", "test65", conn);
        DataBase.removeAcc("test55" , "test55" , conn);
        DataBase.removeAcc("test65" , "test65" , conn);
        assert(ans);

    }


    @Test
    public void testremoveFriendTrue() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test7", "test7" ,"test7", conn);
        DataBase.addAccount("test8", "test8" ,"test8", conn);
        DataBase.addFriend("test7", "test8", conn);
        boolean ans = DataBase.deleteFriend("test7", "test8", conn);
        DataBase.removeAcc("test7" , "test7" , conn);
        DataBase.removeAcc("test8" , "test8" , conn);
        assertTrue(ans);
    }


    @Test
    public void testremoveFriendFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        assert(!DataBase.deleteFriend("test1", "ettt", conn));

    }


    @Test
    public void testgetpoints() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test88", "test88" ,"test88", conn);
        boolean ans = (DataBase.getPoints("test88", conn) == 0);
        DataBase.removeAcc("test88" , "test88" , conn);
        assert(ans);
    }

    @Test
    public void testFriendStaTrue() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test01", "test01" ,"test01", conn);
        DataBase.addAccount("test02", "test02" ,"test02", conn);
        DataBase.addFriend("test01", "test02", conn);
        boolean ans = DataBase.statusFriend("test01", "test02", true , conn);
        DataBase.removeAcc("test01" , "test01" , conn);
        DataBase.removeAcc("test02" , "test02" , conn);
        assert(ans);

    }

    @Test
    public void testRewardStaTrue() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test01", "test01" ,"test01", conn);

        boolean ans = DataBase.changeReward("test01" , "vegan10" , true , conn);

        DataBase.removeAcc("test01" , "test01" , conn);

        assert(ans);

    }

    @Test
    public void testFriendsPoints() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test11", "test11" ,"test11", conn);
        DataBase.addAccount("test12", "test12" ,"test12", conn);
        DataBase.addFriend("test11", "test12", conn);
        DataBase.statusFriend("test11", "test12", true , conn);
        String ans1 = DataBase.getFriendsPoints("test11" , true , conn);
        DataBase.removeAcc("test11" , "test11" , conn);
        DataBase.removeAcc("test12" , "test12" , conn);

        System.out.println(ans1);
        String ans2 = "test11 test11 0.0, test12 test12 0.0, ";
        assert(ans1.equals(ans2));

    }

    @Test
    public void testFriendstoApproveStr() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test11", "test11" ,"test11", conn);
        DataBase.addAccount("test12", "test12" ,"test12", conn);
        DataBase.addFriend("test11", "test12", conn);

        String ans1 = DataBase.getFriendstoApproveStr("test12" , false, conn);
        DataBase.removeAcc("test11" , "test11" , conn);
        DataBase.removeAcc("test12" , "test12" , conn);

        System.out.println(ans1);
        String ans2 = "test11 test12 false, ";
        assert(ans1.equals(ans2));

    }

    @Test
    public void testgetSolars() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test130", "test100" ,"test100", conn);

        String ans = "\"points\" : \"0.0\", \"solarPoints\" : \"0.0\", \"solarLast\" : \"0.0\", \"solarM2\" : \"0.0\", \"solarTime\" : \"0.0\"";
        String ans1 = DataBase.getSolars("test130", conn);
        System.out.println(ans1);

        DataBase.removeAcc("test130" , "test100" , conn);
        assert(ans1.equals(ans));

    }

    @Test
    public void testgetHeat() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test130", "test100" ,"test100", conn);

        String ans = "\"points\" : \"0.0\", \"heatingPoints\" : \"0.0\", \"heatingLast\" : \"0.0\", \"heatingTemp\" : \"0.0\", \"heatingTime\" : \"0.0\"";
        String ans1 = DataBase.getHeats("test130", conn);
        System.out.println(ans1);

        DataBase.removeAcc("test130" , "test100" , conn);
        assert(ans1.equals(ans));

    }

    @Test
    public void testgetQuestions() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test130", "test100" ,"test100", conn);
        String ans = "\"points\" : \"0.0\", \"vegan\" : \"0.0\", \"bike\" : \"0.0\", \"walk\" : \"0.0\", \"trans\" : \"0.0\", \"local\" : \"0.0\", \"heating\" : \"0.0\", \"solar\" : \"0.0\", \"donations\" : \"0.0\"";
        String ans1 = DataBase.getQuestions("test130", conn);
        System.out.println(ans1);
        DataBase.removeAcc("test130" , "test100" , conn);
        assert(ans1.equals(ans));

    }

    @Test
    public void testChangeRewardFail() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        boolean ans1 = DataBase.changeRewardPast("testtt" , "walk100" , true , conn);
        System.out.println(ans1);

        assert(!ans1);

    }

    @Test
    public void testChangeQuestionFail() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        boolean ans1 = DataBase.changeQuestion("testtt" , "walk" , 22 , conn);
        System.out.println(ans1);

        assert(!ans1);

    }

    @Test
    public void testGetFriendsStr() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("test21", "test21" ,"test21", conn);
        DataBase.addAccount("test22", "test22" ,"test22", conn);
        DataBase.addFriend("test21", "test22", conn);
        DataBase.statusFriend("test21", "test22", true , conn);
        String answer = "test21 test21 true, test21 test22 true, ";
        String ans = DataBase.getFriendsStr("test21",true , conn);
        System.out.println(ans);
        DataBase.removeAcc("test21" , "test21" , conn);
        DataBase.removeAcc("test22" , "test22" , conn);
        assert(answer.equals(ans));
    }


    @Test
    public void testlogedStaTrue() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test31", "test31" ,"test31", conn);
        boolean ans = DataBase.logedStatus("test31", "test31", true , conn);
        DataBase.removeAcc("test31" , "test31", conn);
        assert(ans);

    }

    @Test
    public void testlogedStaFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test31", "test31" ,"test31", conn);
        boolean ans = DataBase.logedStatus("test31", "test31", false , conn);
        DataBase.removeAcc("test31" , "test31", conn);
        assert(ans);

    }

    @Test
    public void testFriendStaFalse() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test32", "test32" ,"test32", conn);
        DataBase.addAccount("test33", "test33" ,"test33", conn);
        DataBase.addFriend("test32" , "test33" , conn);
        boolean ans = DataBase.statusFriend("test32", "test33", false , conn);
        DataBase.removeAcc("test32", "test32" , conn);
        DataBase.removeAcc("test33", "test33" , conn);
        assert(ans);

    }

    @Test
    public void testgetAmount() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("t01", "t01" ,"t01", conn);
        boolean ans = (DataBase.getAmountQuestion("t01", "vegan", conn) == 0);
        DataBase.removeAcc("t01" , "t01" , conn);
        assert(ans);

    }

    @Test
    public void testaddAmount() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);
        DataBase.addAccount("testamm", "test33" ,"test33", conn);
        boolean ans = DataBase.addToQuestion("testamm", "vegan", 1, conn);
        DataBase.removeAcc("testamm" , "test33" , conn);
        assert(ans);

    }


    @Test
    public void testgetUserName() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test34", "test34" ,"test34", conn);
        boolean ans = DataBase.getUserName("test34", conn).equals("test34");
        DataBase.removeAcc("test34" , "test34" , conn);
        assert(ans);

    }

    @Test
    public void testaddPoints() throws SQLException, ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("testpo", "testpo" ,"test666", conn);
        boolean ans = DataBase.addPoints("testpo", 10, conn);
        DataBase.removeAcc("testpo" , "test666" , conn);
        assert(ans);
    }



    @Test
    public void testcheckMail() throws SQLException , ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("aa", "aa" ,"aa", conn);
        boolean ans = DataBase.checkMail("aa", conn);
        DataBase.removeAcc("aa" , "aa" , conn);
        assert(ans);

    }

    @Test
    public void testcheckRealtion() throws SQLException , ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test41c", "test41" ,"test41", conn);
        DataBase.addAccount("test42c", "test42" ,"test42", conn);
        DataBase.addFriend("test41c", "test42c", conn);
        DataBase.statusFriend("test41c", "test42c", true , conn);
        boolean ans = DataBase.checkRelation("test41c", "test42c", conn);
        DataBase.removeAcc("test41c","test41" , conn);
        DataBase.removeAcc("test42c","test42" , conn);
        assert(ans);

    }

    @Test
    public void testdeleteFriend() throws SQLException , ClassNotFoundException  {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test51", "test51" ,"test51", conn);
        DataBase.addAccount("test52", "test52" ,"test52", conn);
        DataBase.addFriend("test51", "test52", conn);
        DataBase.statusFriend("test51", "test52", true , conn);
        boolean ans = DataBase.deleteFriend("test51" , "test52" , conn);
        DataBase.removeAcc("test51" , "test51" , conn);
        DataBase.removeAcc("test52" , "test52" , conn);
        assert(ans);
    }

    @Test
    public void testRemoveAccount2() throws SQLException , ClassNotFoundException {
        Connection conn = DataBase.connect(link);

        DataBase.addAccount("test556", "test55" ,"test55", conn);
        boolean ans = DataBase.removeAcc("test556", "test55", conn);
        assert(ans);

    }


    @Test
    public void testConnectClose() throws SQLException , ClassNotFoundException  {
        Connection conn = DataBase.connect(link);
        assert(DataBase.closeConnect(conn));
    }


}
