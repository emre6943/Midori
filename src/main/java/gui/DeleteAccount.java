package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ClientMet;

import java.io.IOException;

public class DeleteAccount {
    public static AnchorPane anchor;

    /**
     * Creates a pane for the scene.
     *
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

        Label text = new Label("Are you sure, you want to delete your account?");
        text.setLayoutX(57);
        text.setLayoutY(80);
        //button
        // Delete Account
        JFXButton deleteAcc = new JFXButton();
        deleteAcc.setLayoutX(40);
        deleteAcc.setLayoutY(150);
        deleteAcc.setPrefHeight(60.0);
        deleteAcc.setPrefWidth(300.0);
        deleteAcc.setText("yes");
        deleteAcc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    ClientMet.removeAcc(Login.getMail(), Login.getPassword());
                    logOut();
                } catch (IOException e) {
                    e.printStackTrace();
                    Settings.logOut();
                }

            }
        });
        //button
        JFXButton backButton = new JFXButton();
        backButton.setText("Back");
        backButton.setLayoutY(425);
        backButton.setLayoutX(40);
        backButton.setPrefSize(300, 60);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client cl = new Client();
                cl.openSettings();
            }
        });
        //add everything to the anchorpane and return it

        anchorPane.getChildren().add(text);
        anchorPane.getChildren().add(deleteAcc);
        anchorPane.getChildren().add(backButton);
        return anchorPane;
    }

    /**
     * TO print oin the gui.
     *
     * @param sss mesage to print
     */
    public static void wrongInput(String sss) {
        Label label = new Label();
        label.setText(sss);
        label.setLayoutY(240);
        if (sss.equals("Connection problem")) {
            label.setLayoutX(172);
        } else {
            label.setLayoutX(75);
        }
        if (Login.getTheme()) {
            label.setId("darkTextColor");
        } else {
            label.setId("WrongInputText");
        }

        anchor.getChildren().add(label);
    }


    /**
     * Loguot from the account deletes the data in the client.
     */
    public static void logOut() {
        Login.clearDetails();
        Scene login = gui.Login.createScene();
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(login);
        stage.show();
    }
}
