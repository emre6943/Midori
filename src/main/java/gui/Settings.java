package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import server.ClientMet;

import java.awt.Desktop;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Settings {

    public static AnchorPane anchor;

    /**
     * Creates a pane for the Settings and adds buttons for the different
     * pages and the logo as an image.
     * @return returns the pane with the buttons and the image.
     */

    public AnchorPane createPane() {
        AnchorPane settings = new AnchorPane();
        anchor = settings;
        if (Login.getTheme()) {
            settings.setId("darkTheme");
        } else {
            settings.setId("settings");
        }
        settings.setPrefSize(380,480);

        // Change password
        JFXButton changePass = createButton("Change Password");
        changePass.setLayoutY(75);
        changePass.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openCp();
            }
        });
        if (Login.getTheme()) {
            changePass.setId("darkButton");
        }
        // Delete Account
        JFXButton deleteAcc = createButton("Delete your account");
        deleteAcc.setLayoutY(150);
        deleteAcc.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openDeleteAcc();
            }
        });
        if (Login.getTheme()) {
            deleteAcc.setId("darkButton");
        }
        // Dark mode
        JFXButton changeDark = createButton("Change theme");
        changeDark.setLayoutY(225);
        changeDark.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Login.getTheme()) {
                    Login.setTheme(false);
                } else {
                    Login.setTheme(true);
                }
                Client client = new Client();
                client.openSettings();
            }
        });
        if (Login.getTheme()) {
            changeDark.setId("darkButton");
        }
        // Donate
        JFXButton donate = createButton("Donate to Greenpeace");
        donate.setLayoutX(40);
        donate.setLayoutY(300);
        donate.setPrefHeight(60.0);
        donate.setPrefWidth(300.0);
        donate.setText("Donate");
        donate.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    donate();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        if (Login.getTheme()) {
            donate.setId("darkButton");
        }
        // Log out
        JFXButton logOut = createButton("Log Out");
        logOut.setLayoutY(375);
        logOut.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logOut();
            }
        });
        if (Login.getTheme()) {
            logOut.setId("darkButton");
        }
        settings.getChildren().addAll(changePass, changeDark,
            deleteAcc, logOut, donate);
        return settings;
    }

    /**
     * sends to the donation page.
     */
    public static void donate() throws IOException {
        try {
            if (Desktop.isDesktopSupported()
                && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI("https://secure.greenpeaceusa.org/"));
            }
            ClientMet.donate(Login.getMail(), 100);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
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

    /**
     * Creates button.
     * @param text what button to create
     * @return returns a button
     */
    public static JFXButton createButton(String text) {
        JFXButton button = new JFXButton();
        button.setPrefSize(300, 60);
        button.setText(text);
        button.setLayoutX(40);
        return button;
    }
}
