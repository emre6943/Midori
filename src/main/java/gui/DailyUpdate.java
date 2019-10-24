package gui;

import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;

public class DailyUpdate {

    private static long currTime = System.currentTimeMillis();

    /**
     * The method compares the latest update time in the db to the current time.
     * if a day has passed add the correct amount of points to the user.
     */
    public static void checkSolarUpdate() {
        try {
            JSONObject js = new JSONObject(ClientMet.getDailyUpdate(Login.getMail()));
            double lastUpdate = js.getDouble("solarTime");
            double solarArea = js.getDouble("solarM2");
            double solarPoints = js.getDouble("solar");

            js = new JSONObject(ClientMet.getConstants());
            double points = js.getDouble("SolarP");

            System.out.println(ClientMet.updateTime(Login.getMail(), "solar",
                "solarTime" , currTime, solarArea * points));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method compares the latest update time in the db to the current time.
     * if a day has passed add the correct amount of points to the user
     */

    public static void checkHeatUpdate() {
        try {
            JSONObject js = new JSONObject(ClientMet.getDailyUpdate(Login.getMail()));
            double lastUpdate = js.getDouble("heatingTime");
            double heatingTemp = js.getDouble("heatingTemp");
            double heatingPoints = js.getDouble("heating");
            js = new JSONObject(ClientMet.getConstants());
            double points = js.getDouble("HeatP");

            ClientMet.updateTime(Login.getMail(), "heating",
                "heatingTime" , currTime, heatingTemp * points);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
