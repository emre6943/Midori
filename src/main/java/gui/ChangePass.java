package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import server.ClientMet;

import java.io.IOException;

public class ChangePass {
    public static AnchorPane anchor;

    /**
     * Creates a pane for the scene.
     * @return a pane
     */
    public static AnchorPane createPane() {
        AnchorPane anchorPane = new AnchorPane();
        if (Login.getTheme()) {
            anchorPane.setId("darkTheme");
        } else {
            anchorPane.setId("anchor");
        }

        anchor = anchorPane;
        //old password
        JFXPasswordField passwordFieldOld = new JFXPasswordField();
        passwordFieldOld.setLayoutX(30);
        passwordFieldOld.setLayoutY(100);
        passwordFieldOld.setPrefHeight(35);
        passwordFieldOld.setPrefWidth(330);
        passwordFieldOld.setPromptText("Old password");
        //new password
        JFXPasswordField passwordField1 = new JFXPasswordField();
        passwordField1.setLayoutX(30);
        passwordField1.setLayoutY(150);
        passwordField1.setPrefHeight(35);
        passwordField1.setPrefWidth(330);
        passwordField1.setPromptText("New password");
        //new again password
        JFXPasswordField passwordField2 = new JFXPasswordField();
        passwordField2.setLayoutX(30);
        passwordField2.setLayoutY(200);
        passwordField2.setPrefHeight(35);
        passwordField2.setPrefWidth(330);
        passwordField2.setPromptText("Confirm new password");
        //button
        JFXButton button = new JFXButton();
        button.setLayoutX(75);
        button.setLayoutY(285);
        button.setPrefHeight(50);
        button.setPrefWidth(220);
        button.setText("change password");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (passwordField1.getText().equals(passwordField2.getText())
                    && passwordFieldOld.getText().equals(Login.getPassword())) {
                    changepass(passwordField1);
                    wrongInput("Password Changed");
                } else {
                    wrongInput("Wrong password or passwords don't match");
                }
            }
        });
        //button
        JFXButton backButton = new JFXButton();
        backButton.setText("Back");
        backButton.setLayoutY(425);
        backButton.setLayoutX(40);
        backButton.setPrefSize(300,60);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client cl = new Client();
                cl.openSettings();
            }
        });
        //add everything to the anchorpane and return it

        anchorPane.getChildren().add(passwordFieldOld);
        anchorPane.getChildren().add(passwordField1);
        anchorPane.getChildren().add(passwordField2);
        anchorPane.getChildren().add(backButton);
        anchorPane.getChildren().add(button);
        return anchorPane;
    }

    /**
     * to write in clean format.
     * @param sss what to write to gui
     */
    public static void wrongInput(String sss) {
        Label label = new Label();
        label.setText(sss);
        label.setLayoutY(240);
        if (sss.equals("Connection problem")) {
            label.setLayoutX(172);
        } else {
            label.setLayoutX(75);
            if (Login.getTheme()) {
                label.setId("darkTextColor");
            } else {
                label.setId("WrongInputText");
            }

        }

        anchor.getChildren().add(label);
    }


    /**
     * change pass.
     * @param pw new password
     */
    public static void changepass(JFXPasswordField pw) {
        try {
            ClientMet.changePass(Login.getMail(),pw.getText());
        } catch (IOException e) {
            System.out.println(e);
        }
        Login.setPassword(pw.getText());
    }
}
