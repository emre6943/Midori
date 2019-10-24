package gui;

import db.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;

public class Home {

    protected static AnchorPane feedbackPane;
    protected static AnchorPane anchor;
    protected static int heatupdate;
    protected static int solarupdate;

    /**
     * Creates home screen.
     * @return a vbox
     */
    public static AnchorPane createPane() {

        if (checkUpdate()) {
            AnchorPane feed = createFeedbackPane();
            feedbackPane = feed;
            feed.setLayoutX(20);
            daily();
        }

        AnchorPane overlay = new AnchorPane();
        overlay.setPrefSize(370,480);
        javafx.scene.control.ListView listView = new ListView();
        listView.setPrefSize(380,480);


        AnchorPane average = new AnchorPane();
        average.setPrefHeight(60);
        String form = "Avarage points -- " + avgall();
        Label averages = createLabel(form);
        average.getChildren().add(averages);

        JSONObject js = getQuestions();
        AnchorPane point = new AnchorPane();
        point.setPrefHeight(60);
        form = "Your Points -- " + js.getDouble("points");
        Label points = createLabel(form);
        point.getChildren().add(points);

        AnchorPane vegan = new AnchorPane();
        vegan.setPrefHeight(60);
        form = "Money spent on Vegetarian Meals -- " + js.getDouble("vegan");
        Label veg = createLabel(form);
        vegan.getChildren().add(veg);

        AnchorPane bike = new AnchorPane();
        bike.setPrefHeight(60);
        form = "Points earned from biking -- " + js.getDouble("bike");
        Label bikes = createLabel(form);
        bike.getChildren().add(bikes);

        AnchorPane walk = new AnchorPane();
        walk.setPrefHeight(60);
        form = "Points earned from walking -- " + js.getDouble("walk");
        Label walkes = createLabel(form);
        walk.getChildren().add(walkes);

        AnchorPane transport = new AnchorPane();
        transport.setPrefHeight(60);
        form = "Points earned from public transportation -- " + js.getDouble("trans");
        Label trans = createLabel(form);
        transport.getChildren().add(trans);

        AnchorPane local = new AnchorPane();
        local.setPrefHeight(60);
        form = "Money spent on local products -- " + js.getDouble("local");
        Label localP = createLabel(form);
        local.getChildren().add(localP);

        AnchorPane heat = new AnchorPane();
        heat.setPrefHeight(60);
        form = "Points earned from changing the heat -- " + js.getDouble("heating");
        Label heats = createLabel(form);
        heat.getChildren().add(heats);

        AnchorPane solar = new AnchorPane();
        solar.setPrefHeight(60);
        form = "Points earned from solar panels -- " + js.getDouble("solar");
        Label solars = createLabel(form);
        solar.getChildren().add(solars);

        AnchorPane donate = new AnchorPane();
        donate.setPrefHeight(60);
        form = "Points earned from donations -- " + js.getDouble("donations");
        Label donates = createLabel(form);
        donate.getChildren().add(donates);

        ObservableList<AnchorPane> list =
            FXCollections.observableArrayList(point, average , vegan, bike, walk,
                transport, local, heat, solar, donate);
        listView.setItems(list);

        AnchorPane anchorPane = new AnchorPane();
        anchor = anchorPane;
        anchorPane.getChildren().add(listView);
        anchorPane.getChildren().add(overlay);
        if (feedbackPane != null) {
            anchorPane.getChildren().add(feedbackPane);
        }
        return anchorPane;
    }

    /**
     * Creates a label.
     * @param succ text to put in the label
     * @return the label
     */
    public static Label createLabel(String succ) {
        Label label = new Label();
        label.setText(succ);
        label.setLayoutX(50);
        label.setLayoutY(20);

        return label;
    }

    /**
     * Creates a pane to hold feedback.
     * @return the same pane
     */
    public static AnchorPane createFeedbackPane() {
        AnchorPane feedAnc = new AnchorPane();
        feedAnc.setPrefSize(340,40);
        feedAnc.setLayoutY(420);
        feedAnc.setId("popup");

        Label closeButton = new Label("x");
        closeButton.setId("closeButton");
        closeButton.setLayoutX(320);
        closeButton.setLayoutY(0);
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeFeedback();
            }
        });

        feedAnc.getChildren().add(closeButton);
        return feedAnc;
    }

    /**
     * adds heat stuff to feedbackpane.
     */
    public static void showFeedbackHeat(String text) {
        Label label = new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(20);
        feedbackPane.getChildren().add(label);
    }

    /**
     * adds solar stuff to feedbackpane.
     */
    public static void showFeedbackSolar(String text) {
        Label label = new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(0);
        feedbackPane.getChildren().add(label);
    }

    /**
     * shows updates on solar and heat.
     */
    public static void daily() {
        showFeedbackHeat("You have earned " + heatupdate + " points for temperature");
        showFeedbackSolar("You have earned " + solarupdate + " points for solar panels");

    }

    /**
     * checks if the daily score has been updated.
     */
    public static boolean checkUpdate() {
        try {
            JSONObject json = new JSONObject(ClientMet.getQuestions(Login.getMail()));
            double initHeat = json.getDouble("heating");
            double initSolar = json.getDouble("solar");
            DailyUpdate.checkHeatUpdate();
            DailyUpdate.checkSolarUpdate();

            double heatCopy = initHeat;
            double solarCopy = initSolar;
            json = new JSONObject(ClientMet.getQuestions(Login.getMail()));
            double heatPoints = json.getDouble("heating");
            double solarPoints = json.getDouble("solar");
            double pts = heatPoints - heatCopy;
            int pts2 = (int)pts;
            heatupdate = pts2;
            double ptsd = solarPoints - solarCopy;
            int ptsd2 = (int)ptsd;
            solarupdate = ptsd2;

            return pts2 != 0 && ptsd2 != 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * closes popup thing.
     */
    public static void closeFeedback() {
        anchor.getChildren().remove(anchor.getChildren().size() - 1);
    }

    /**
     * gets the questions.
     * @return returns the json of the points
     */
    public static JSONObject getQuestions() {
        String ans = "";
        JSONObject js = new JSONObject();
        try {
            ClientMet.getDailyUpdate(Login.getMail());
            ans = ClientMet.getQuestions(Login.getMail());
            js = new JSONObject(ans);
        } catch (IOException e) {
            System.out.println("That email does not exist");
        }
        return js;
    }

    /**
     * average points.
     * @return the avg points from all users
     */
    public static double avgall() {
        ObservableList<Account> accs = null;
        try {
            accs = FXCollections.observableArrayList(
                server.ClientMet.cleanerLeader(ClientMet.getLeader()));
        } catch (IOException e) {
            System.out.println("?");
        }
        double sum = 0;
        for (int i = 0 ; i < accs.size() ; i++) {
            sum = sum + accs.get(i).getPoint();
        }
        return sum / accs.size();
    }


}
