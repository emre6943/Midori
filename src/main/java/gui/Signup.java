package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;

public class Signup {

    public static AnchorPane anchor;
    public static Label minButton;
    public static Label closeButton;

    /**
     * Creates the signup scene.
     * @return the scene
     */
    public static Scene createScene() {
        AnchorPane anchorPane = createPane();
        Scene scene = new Scene(anchorPane, 440, 550);
        scene.getStylesheets().add("/Signup.css");
        return scene;
    }

    /**
     * Creates a pane for the createScene method.
     * @return  the pane
     */
    public static AnchorPane createPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setId("Anchor");
        anchor = anchorPane;
        //username field
        JFXTextField unField = new JFXTextField();
        unField.setPromptText("Username");
        unField.setPrefSize(330,42);
        unField.setLayoutX(55);
        unField.setLayoutY(211);
        //email field
        JFXTextField mailField = new JFXTextField();
        mailField.setPromptText("Email");
        mailField.setPrefSize(330,42);
        mailField.setLayoutX(55);
        mailField.setLayoutY(255);
        //password field 1
        JFXPasswordField pwField1 = new JFXPasswordField();
        pwField1.setPromptText("Password");
        pwField1.setPrefSize(330,42);
        pwField1.setLayoutX(55);
        pwField1.setLayoutY(308);
        //password field 2
        JFXPasswordField pwField2 = new JFXPasswordField();
        pwField2.setPromptText("Confirm Password");
        pwField2.setPrefSize(330,42);
        pwField2.setLayoutX(55);
        pwField2.setLayoutY(352);
        //button
        JFXButton button = new JFXButton();
        button.setText("Sign Up");
        button.setLayoutX(110);
        button.setLayoutY(420);
        button.setPrefSize(220,50);
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (pwField1.getText().equals(pwField2.getText())) {
                    signup(mailField,unField,pwField1);
                } else {
                    wrongInput("Passwords don't match");
                }
            }
        });
        //hyperlink
        Hyperlink hyper = new Hyperlink();
        hyper.setText("Already have an account?");
        hyper.setLayoutX(148);
        hyper.setLayoutY(490);
        hyper.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                goToLogin();
            }
        });
        //minimize button
        Label minBtn = new Label();
        minBtn.setText("_");
        minBtn.setLayoutX(386);
        minBtn.setLayoutY(-4);
        minBtn.setId("minButton");
        minButton = minBtn;
        minBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                minimizeWindow();
            }
        });
        //close button
        Label closeBtn = new Label();
        closeBtn.setText("x");
        closeBtn.setLayoutX(414);
        closeBtn.setLayoutY(0);
        closeBtn.setId("closeButton");
        closeButton = closeBtn;
        closeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeWindow();
            }
        });

        anchorPane.getChildren().add(unField);
        anchorPane.getChildren().add(mailField);
        anchorPane.getChildren().add(pwField1);
        anchorPane.getChildren().add(pwField2);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(hyper);
        anchorPane.getChildren().add(minButton);
        anchorPane.getChildren().add(closeButton);
        return anchorPane;
    }

    /**
     * Changes scene to login.
     */
    public static void goToLogin() {
        Scene login = gui.Login.createScene();
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(login);
        stage.show();
    }

    /**
     * Closes the window.
     */
    public static void closeWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Minimizes the window.
     */

    public static void minimizeWindow() {
        Stage stage = (Stage) minButton.getScene().getWindow();
        stage.setIconified(true);
    }

    /**
     * SIGNUp.
     * @param mail mail
     * @param name username
     * @param pw password
     */
    public static void signup(JFXTextField mail, JFXTextField name , JFXPasswordField pw) {
        String email = mail.getText();
        String username = name.getText();
        String password = pw.getText();
        try {
            String answer = ClientMet.signUp(email,username,password);
            JSONObject json = new JSONObject(answer);
            String command = json.getString("command");
            if (command.equals("pass signup")) {
                goToLogin();
            } else {
                mail.setId("WrongInput");
                wrongInput("account exists");
            }
        } catch (IOException e) {
            wrongInput("Connection problem");
            e.printStackTrace();
        }
    }

    /**
     * creates text for when a pproblem in signup occurs.
     * @param sss String to write the gui
     */
    public static void wrongInput(String sss) {
        Label label = new Label();
        label.setText(sss);
        label.setLayoutY(402);
        if (sss.equals("Connection problem")) {
            label.setLayoutX(166);
        } else {
            label.setLayoutX(155);
        }
        label.setId("WrongInputText");
        anchor.getChildren().add(label);
    }


}
