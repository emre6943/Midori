package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.Account;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;
import java.util.ArrayList;

public class Friends {

    public static AnchorPane anchor;
    public static AnchorPane feedbackPane;
    public static ListView list;

    /**
     * Creates new entry menu ..  . .. . .. . ...
     * @return the menu
     */
    public static  AnchorPane createPane() {

        AnchorPane anc = new AnchorPane();
        anchor = anc;

        JFXTextField textField = new JFXTextField();
        textField.setPromptText("Your friend's mail");
        textField.setTooltip(new Tooltip("Enter the email of your friend"));
        textField.setLayoutX(14);
        textField.setLayoutY(90);
        textField.setPrefSize(300, 35);

        JFXButton button = new JFXButton("Send");
        button.setLayoutX(314);
        button.setLayoutY(90);
        button.setPrefSize(50, 35);
        button.setTooltip(new Tooltip("Sends a friend request"));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addFriend(textField);
            }
        });
        //incoming requests
        javafx.scene.control.ListView listView = new ListView();
        list = listView;
        listView.setLayoutX(15);
        listView.setLayoutY(150);
        listView.setPrefSize(350,300);

        AnchorPane feed = createFeedbackPane();
        feedbackPane = feed;
        anc.getChildren().addAll(feed, button, textField, listView);
        // array list that has the list
        ArrayList<Account> ac = getFriendsToApprove();
        return anc;

    }

    /**
     * Creates a pane to hold feedback.
     * @return the same pane
     */
    public static AnchorPane createFeedbackPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(380,40);
        anchorPane.setLayoutY(10);

        return anchorPane;
    }

    /**
     * adds stuff to feedbackpane.
     */
    public static void showFeedback(String text) {
        feedbackPane.getChildren().clear();
        Label label = new Label(text);
        label.setLayoutX(50);
        label.setLayoutY(10);
        feedbackPane.getChildren().add(label);
    }

    /**
     * adds a friend.
     */
    public static void addFriend(JFXTextField textField) {
        String mailto = textField.getText();
        String ans = "";
        try {
            ans = ClientMet.sendFriendReq(Login.getMail(),mailto);
            showFeedback("Sent a friend request to " + textField.getText());
            textField.clear();
        } catch (IOException e) {
            wrongInput("email not found");
        }
        JSONObject js = new JSONObject(ans);
        System.out.println(js.getString("command"));
    }

    /**
     * to print on the gui.
     * @param sss sTRING TO print
     */
    public static void wrongInput(String sss) {
        Label label = new Label(sss);
        label.setLayoutY(125);
        if (sss.equals("Connection problem")) {
            label.setLayoutX(172);
        } else {
            label.setLayoutX(146);
        }

        if (Login.getTheme()) {
            label.setId("darkTextColor");
        } else {
            label.setId("WrongInputText");
        }

        anchor.getChildren().add(label);
    }

    /**
     * fills listView with friend requests.
     */
    public static void populateList() {
        ArrayList<Account> accounts = getFriendsToApprove();
        String username = "";
        String mail = "";

        if (accounts != null) {
            for (int i = 0; i < accounts.size(); i++) {
                username = accounts.get(i).getUsername();
                mail = accounts.get(i).getMail();
                AnchorPane request = createFriendRequest(username, mail);
                list.getItems().add(request);
            }
        }

    }

    /**
     * creates a friend request.
     */
    public static AnchorPane createFriendRequest(String username, String mail) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefHeight(60);
        //label
        Label label = new Label(mail + " wants to be your friend");
        label.setLayoutY(22);
        label.setLayoutX(7);
        //accept
        JFXButton accept = new JFXButton("Accept");
        accept.setId("acceptButton");
        accept.setPrefSize(60,30);
        accept.setLayoutX(210);
        accept.setLayoutY(15);
        accept.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                approveFriend(mail);
                showFeedback(mail + " is now your friend");
            }
        });
        //decline
        JFXButton decline = new JFXButton("Decline");
        decline.setId("declineButton");
        decline.setPrefSize(60,30);
        decline.setLayoutX(273);
        decline.setLayoutY(15);
        decline.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeFriend(mail);
                showFeedback("Rejected " + mail + "'s friend request");
            }
        });
        pane.getChildren().addAll(label, accept, decline);
        return pane;
    }

    /**
     * gets friends to approve.
     */
    public static ArrayList<Account> getFriendsToApprove() {
        String ans = "{\"approve\" : \"\"}";
        ArrayList<Account> ac = null;
        try {
            ans = ClientMet.getFriends(Login.getMail());
            ac = ClientMet.cleanerFriendA(ans);
        } catch (IOException e) {
            System.out.println("no email like that");
            return null;
        }
        return ac;
    }

    /**
     * approves friend a friend.
     */
    public static void approveFriend(String mailfrom) {
        String ans = "";
        try {
            ans = ClientMet.approveFriend(mailfrom, Login.getMail());
            Client client = new Client();
            client.openFriends();
        } catch (IOException e) {
            System.out.println("no email like that");
        }
        JSONObject js = new JSONObject(ans);
        System.out.println(js.getString("command"));
    }

    /**
     * removes friend a friend.
     */
    public static void removeFriend(String mailfrom) {
        String ans = "";
        try {
            ans = ClientMet.removeFriend(mailfrom, Login.getMail());
            Client client = new Client();
            client.openFriends();
        } catch (IOException e) {
            System.out.println("no email like that");
        }
        JSONObject js = new JSONObject(ans);
        System.out.println(js.getString("command"));
    }

}
