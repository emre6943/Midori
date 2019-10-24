package gui;

import com.jfoenix.controls.JFXButton;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SidePanel {
    /**
     * Creates a pane for the Sidepanel and adds buttons for the different.
     * pages and the logo as an image.
     * @return returns the pane with the buttons and the image.
     */
    public VBox createPane() {
        VBox vbox = new VBox();
        vbox.setPrefSize(205,550);
        // home button
        JFXButton homeButton = createButton("Home");
        homeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openHome();
            }
        });
        if (Login.getTheme()) {
            homeButton.setId("darkButton");
        } else {
            homeButton.setId("sidePanel");
        }
        // new Entry Button
        JFXButton newEntryButton = createButton("New Entry");
        newEntryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openNe();
            }
        });
        if (Login.getTheme()) {
            newEntryButton.setId("darkButton");
        } else {
            newEntryButton.setId("sidePanel");
        }
        //leaderboard button
        JFXButton leaderboardButton = createButton("Leaderboard");
        leaderboardButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openLeaderboard();
            }
        });
        if (Login.getTheme()) {
            leaderboardButton.setId("darkButton");
        } else {
            leaderboardButton.setId("sidePanel");
        }

        // achievement button

        JFXButton achievementButton = createButton("Achievements");
        achievementButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openAchievements();
            }
        });
        if (Login.getTheme()) {
            achievementButton.setId("darkButton");
        } else {
            achievementButton.setId("sidePanel");
        }
        JFXButton friendButton = createButton("Friends");
        friendButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openFriends();
            }
        });
        if (Login.getTheme()) {
            friendButton.setId("darkButton");
        } else {
            friendButton.setId("sidePanel");
        }
        JFXButton settingsButton = createButton("Settings");
        settingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openSettings();
            }
        });
        if (Login.getTheme()) {
            settingsButton.setId("darkButton");
        } else {
            settingsButton.setId("sidePanel");
        }

        AnchorPane imageAnchor = new AnchorPane();
        imageAnchor.setId("sidePanel");
        imageAnchor.setPrefHeight(165);
        ImageView selectedImage = new ImageView(new Image(
            "https://cdn.discordapp.com/attachments/517741749830156298/564055530017128448/square_Recovered.png"));
        selectedImage.setFitWidth(100);
        selectedImage.setFitHeight(49);
        selectedImage.setLayoutY(62);
        selectedImage.setLayoutX(50.125);
        imageAnchor.getChildren().add(selectedImage);

        vbox.getChildren().add(imageAnchor);
        vbox.getChildren().addAll(homeButton, newEntryButton, leaderboardButton,
            friendButton, achievementButton, settingsButton);
        return vbox;
    }

    /**
     * Creates button.
     * @param text what button to create
     * @return returns a button
     */
    public static JFXButton createButton(String text) {
        JFXButton button = new JFXButton();
        button.setPrefSize(205, 70);
        button.setText(text);
        return button;
    }

}
