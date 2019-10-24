package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;

public class Login {

    public static Label minButton;
    public static Label closeButton;
    public static AnchorPane anchor;
    private static String mail;
    private static String pw;
    private static boolean darkTheme = false;

    public static String getMail() {
        return mail;
    }

    public static boolean getTheme() {
        return darkTheme;
    }

    public static boolean setTheme(boolean ans) {
        return darkTheme = ans;
    }

    public static String getPassword() {
        return pw;
    }

    public static void setPassword(String ps) {
        pw = ps;
    }

    public static  void clearDetails() {
        mail = "";
        pw = "";
    }

    /**
     * Generates a new login scene.
     * @return the login scene
     */
    public static Scene createScene() {
        AnchorPane anchorPane = createPane();
        Scene scene = new Scene(anchorPane, 440, 550);
        scene.getStylesheets().add("/Login.css");
        return scene;
    }

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
        //password
        JFXPasswordField passwordField = new JFXPasswordField();
        passwordField.setLayoutX(61);
        passwordField.setLayoutY(317);
        passwordField.setPrefHeight(42);
        passwordField.setPrefWidth(330);
        passwordField.setPromptText("password");
        //username
        JFXTextField textField = new JFXTextField();
        textField.setLayoutX(61);
        textField.setLayoutY(275);
        textField.setPrefHeight(42);
        textField.setPrefWidth(330);
        textField.setPromptText("username/email");
        //button
        JFXButton button = new JFXButton();
        button.setLayoutX(116);
        button.setLayoutY(384);
        button.setPrefHeight(50);
        button.setPrefWidth(220);
        button.setText("Sign in");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                login(passwordField, textField);
            }
        });
        //sign up link
        Hyperlink noAccount = new Hyperlink();
        noAccount.setPrefWidth(220);
        noAccount.setText("Don't have an account?");
        noAccount.setLayoutX(116);
        noAccount.setLayoutY(450);
        noAccount.setAlignment(Pos.CENTER);
        noAccount.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                goToSignup();
            }
        });
        //reset password link
        Hyperlink forgotPass = new Hyperlink();
        forgotPass.setPrefWidth(220);
        forgotPass.setText("Forgot your password?");
        forgotPass.setLayoutX(116);
        forgotPass.setLayoutY(474);
        forgotPass.setAlignment(Pos.CENTER);
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

        AnchorPane imageAnchor = new AnchorPane();
        imageAnchor.setPrefSize(100,49);
        imageAnchor.setLayoutX(176);
        imageAnchor.setLayoutY(200);
        ImageView selectedImage = new ImageView(new Image(
                "https://cdn.discordapp.com/attachments/517741749830156298/564055530017128448/square_Recovered.png"));
        selectedImage.setFitWidth(100);
        selectedImage.setFitHeight(49);
        imageAnchor.getChildren().add(selectedImage);

        //add everything to the anchorpane and return it
        anchorPane.getChildren().add(imageAnchor);
        anchorPane.getChildren().add(textField);
        anchorPane.getChildren().add(passwordField);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(noAccount);
        anchorPane.getChildren().add(forgotPass);
        anchorPane.getChildren().add(minButton);
        anchorPane.getChildren().add(closeButton);
        return anchorPane;
    }

    /**
     * prints on gui.
     * @param ssss String to write the gui
     */
    public static void wrongInput(String ssss) {
        Label label = new Label();
        label.setText(ssss);
        label.setLayoutY(366);
        if (ssss.equals("Connection problem")) {
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
     * Attempts to log in using username/email and password.
     * @param pwField password field
     * @param unField username/email field
     */
    public static void login(JFXPasswordField pwField, JFXTextField unField) {
        String email = unField.getText();
        String password = pwField.getText();
        try {
            String answer = ClientMet.logIn(email,email,password);
            JSONObject json = new JSONObject(answer);
            String command = json.getString("command");
            if (command.equals("pass")) {
                mail = email;
                pw = password;
                goToClient();
            } else {
                pwField.setId("WrongInput");
                unField.setId("WrongInput");
                wrongInput("Wrong username or password");
            }
        } catch (IOException e) {
            wrongInput("Connection problem");
            e.printStackTrace();
        }
        //delete this
        //goToClient();
    }

    /**
     * Changes scene to client.
     */
    public static void goToClient() {
        Scene client = gui.Client.createScene();
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(client);
        stage.show();
        Client cli = new Client();
        cli.openHome();
    }



    /**
     * Changes scene to signup.
     */
    public static void goToSignup() {
        Scene signup = gui.Signup.createScene();
        Stage stage = (Stage) anchor.getScene().getWindow();
        stage.setScene(signup);
        stage.show();
    }

}
