package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {

    static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the gui.
     * @param stage main stage
     */
    @Override
    public void start(Stage stage) {
        Start.stage = stage;
        //get the scenes
        Scene login = gui.Login.createScene();
        //show the stage
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(login);
        stage.show();
    }

}
