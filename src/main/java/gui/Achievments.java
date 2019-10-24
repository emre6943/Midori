package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;

public class Achievments {
    /**
     * Creates achievements screen.
     * @return a vbox
     */
    public static AnchorPane createPane() {
        javafx.scene.control.ListView listView = new ListView();
        listView.setPrefSize(380,480);
        AnchorPane overlay = new AnchorPane();
        overlay.setPrefSize(370,480);
        if (Login.getTheme()) {
            listView.setId("darkHamburger");
        } else {
            overlay.setId("overlay");
        }
        JSONObject js = getAcchivements(Login.getMail());
        AnchorPane vegan10 = new AnchorPane();
        setLockID(vegan10);
        vegan10.setPrefHeight(60);
        setUnlockID(vegan10,"vegan10" );

        vegan10.getChildren().add(createLabel("Eat 10 euros worth of vegetarian meals"));
        AnchorPane vegan100 = new AnchorPane();
        setLockID(vegan100);
        vegan100.setPrefHeight(60);
        setUnlockID(vegan100,"vegan100" );

        vegan100.getChildren().add(createLabel("Eat 100 euros worth of vegetarian meals"));
        AnchorPane local10 = new AnchorPane();
        setLockID(local10);
        local10.setPrefHeight(60);
        setUnlockID(local10 , "local10");
        local10.getChildren().add(createLabel("Buy 10 euros worth of local products"));

        AnchorPane local100 = new AnchorPane();
        setLockID(local100);
        local100.setPrefHeight(60);
        setUnlockID(local100, "local100");
        local100.getChildren().add(createLabel("Buy 100 euros worth of local products"));

        AnchorPane heating = new AnchorPane();
        setLockID(heating);
        heating.setPrefHeight(60);
        setUnlockID(heating,"heating100" );
        heating.getChildren().add(createLabel("Get 100 points from heating"));

        AnchorPane solar = new AnchorPane();
        setLockID(solar);
        solar.setPrefHeight(60);
        setUnlockID(solar, "solar100");
        solar.getChildren().add(createLabel("Get 100 points from solar panels"));

        AnchorPane donation = new AnchorPane();
        setLockID(donation);
        donation.setPrefHeight(60);
        setUnlockID(donation,"donate100" );
        donation.getChildren().add(createLabel("Donate to charity"));

        AnchorPane transport = new AnchorPane();
        setLockID(transport);
        transport.setPrefHeight(60);
        setUnlockID(transport, "public_trans100");
        transport.getChildren().add(createLabel("Get 100 points from public transport"));

        AnchorPane bike = new AnchorPane();
        setLockID(bike);
        bike.setPrefHeight(60);
        setUnlockID(bike, "bike100");
        bike.getChildren().add(createLabel("Get 100 points from biking"));

        AnchorPane walk = new AnchorPane();
        setLockID(walk);
        walk.setPrefHeight(60);
        setUnlockID(walk, "walk100");

        walk.getChildren().add(createLabel("Get 100 points from walking"));
        ObservableList<AnchorPane> list = FXCollections.observableArrayList(vegan10,
            vegan100, local10, local100, heating, solar, donation, transport, bike, walk );
        listView.setItems(list);
        AnchorPane vbox = new AnchorPane();
        vbox.getChildren().add(listView);
        vbox.getChildren().add(overlay);
        return vbox;
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
        label.setLayoutY(10);

        return label;
    }

    /**
     * gets the achivements.
     * @param mail mail
     * @return json format of the achivements
     */
    public static JSONObject getAcchivements(String mail) {
        String ans = "";
        try {
            ans = ClientMet.getRewards(mail);
            System.out.println(ans);
        } catch (IOException e) {
            System.out.println(e);
        }
        return new JSONObject(ans);
    }

    /**
     * checks the if the achievements is completed and, sets the id for unlocked achievements.
     * @param anchorPane the anchorpane you want to change the id of
     */
    public static void setUnlockID(AnchorPane anchorPane, String key) {
        JSONObject js = getAcchivements(Login.getMail());
        if (js.getBoolean(key)) {
            if (Login.getTheme()) {
                anchorPane.setId("darkAchived");
            } else {
                anchorPane.setId("UnlockedAchievement");
            }
        }
    }

    /**
     * sets the id for locked achievements.
     * @param anchorPane the anchorpane you want to change the id of
     */
    public static void setLockID(AnchorPane anchorPane) {
        if (Login.getTheme()) {
            anchorPane.setId("darkButton");
        } else {
            anchorPane.setId("LockedAchievement");
        }
    }
}
