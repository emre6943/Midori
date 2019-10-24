package gui;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Client {

    public static Label minButton;
    public static Label closeButton;
    public static JFXHamburger jfxhamburger;
    public static JFXDrawer jfxDrawer;
    public static AnchorPane anchor;
    public static AnchorPane anchor2;

    /**
     * Creates the main client scene.
     * @return  the scene
     */
    public static Scene createScene() {
        AnchorPane anchorPane = createPane();
        anchor2 = anchorPane;
        Scene scene = new Scene(anchorPane, 440, 550);
        scene.getStylesheets().add("/Client.css");
        scene.getStylesheets().add("/Achievements.css");
        return scene;
    }

    /**
     * Creates a pane for the createScene method.
     * @return  the anchorpane
     */
    public static AnchorPane createPane() {
        AnchorPane anchorPane = new AnchorPane();
        if (Login.getTheme()) {
            anchorPane.setId("darkTheme");
        } else {
            anchorPane.setId("Anchor");
        }
        //content pane
        AnchorPane inAnchor = new AnchorPane();
        inAnchor.setPrefSize(380,480);
        inAnchor.setLayoutY(45);
        inAnchor.setLayoutX(30);
        anchor = inAnchor;
        //hamburger please
        JFXHamburger hamburger = new JFXHamburger();
        jfxhamburger = hamburger;
        hamburger.setLayoutX(14);
        hamburger.setLayoutY(14);
        hamburger.setPrefHeight(40);
        hamburger.setPrefWidth(40);
        if (Login.getTheme()) {
            hamburger.setId("darkHamburger");
        } else {
            hamburger.setId("Hamburger");
        }
        hamburger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openDrawer();
            }
        });
        //drawer
        JFXDrawer drawer = new JFXDrawer();
        jfxDrawer = drawer;
        drawer.setDefaultDrawerSize(205);
        drawer.setPrefHeight(550);
        drawer.setPrefWidth(205);
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
        //put everything on the anchorPane and then return it
        anchorPane.getChildren().add(inAnchor);
        anchorPane.getChildren().add(drawer);
        anchorPane.getChildren().add(hamburger);
        anchorPane.getChildren().add(minBtn);
        anchorPane.getChildren().add(closeBtn);
        return anchorPane;
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
     * opens drawer.
     */
    public static void openDrawer() {
        SidePanel sidePanel = new SidePanel();
        VBox box = sidePanel.createPane();
        jfxDrawer.setSidePane(box);
        HamburgerBackArrowBasicTransition transition = new
            HamburgerBackArrowBasicTransition(jfxhamburger);
        //sets the hamburger state to "normal"
        // transition.setRate(-1);
        //toggles state between 1 and -1 when clicked
        transition.setRate(transition.getRate() * -1);
        if (jfxDrawer.isOpened()) {
            jfxDrawer.setMouseTransparent(true);
            transition.play();
            jfxDrawer.close();
        } else if (jfxDrawer.isClosed()) {
            jfxDrawer.setMouseTransparent(false);
            transition.play();
            jfxDrawer.open();
        }
    }

    /**
     * opens NewEntry.
     */
    public void openNe() {
        anchor.getChildren().clear();
        NewEntry newEntry = new NewEntry();
        AnchorPane pane = newEntry.createPane();
        anchor.getChildren().add(pane);
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
    }

    /**
     * opens home.
     */
    public void openHome() {
        anchor.getChildren().clear();
        Home home = new Home();
        AnchorPane pane = Home.createPane();
        anchor.getChildren().add(pane);
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
    }

    /**
     * opens NewEntry food.
     */
    public void openNeF() {
        anchor.getChildren().clear();
        NewEntry newEntry = new NewEntry();
        AnchorPane pane = newEntry.createPaneFood();
        anchor.getChildren().add(pane);
    }

    /**
     * opens NewEntry transport.
     */
    public void openNeT() {
        anchor.getChildren().clear();
        NewEntry newEntry = new NewEntry();
        AnchorPane pane = newEntry.createPaneTransport();
        anchor.getChildren().add(pane);
    }

    /**
     * opens NewEntry Energy.
     */
    public void openNeE() {
        anchor.getChildren().clear();
        NewEntry newEntry = new NewEntry();
        AnchorPane pane = newEntry.createPaneEnergy();
        anchor.getChildren().add(pane);
    }

    /**
     * opens change password.
     */
    public void openCp() {
        anchor.getChildren().clear();
        ChangePass newEntry = new ChangePass();
        AnchorPane pane = ChangePass.createPane();
        anchor.getChildren().add(pane);
    }

    /**
     * opens achievements.
     */
    public void openAchievements() {
        anchor.getChildren().clear();
        Achievments ach = new Achievments();
        AnchorPane pane = Achievments.createPane();
        anchor.getChildren().add(pane);
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
    }

    /**
     * opens friends.
     */
    public void openFriends() {
        anchor.getChildren().clear();
        Friends friends = new Friends();
        AnchorPane pane = Friends.createPane();
        anchor.getChildren().add(pane);
        Friends.populateList();
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
    }

    /**
     * opens leaderboards.
     */
    public void openLeaderboard() {
        anchor.getChildren().clear();
        LeaderBoard friends = new LeaderBoard();
        AnchorPane pane = LeaderBoard.createPane();
        anchor.getChildren().add(pane);
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
    }

    /**
     * opens settings.
     */
    public void openSettings() {
        anchor.getChildren().clear();
        Settings settings = new Settings();
        AnchorPane pane = settings.createPane();
        anchor.getChildren().add(pane);
        if (jfxDrawer.isOpened()) {
            openDrawer();
        }
        if (Login.getTheme()) {
            anchor2.setId("darkTheme");
        } else {
            anchor2.setId("Anchor");
        }
    }

    /**
     * opens delete acc.
     */
    public void openDeleteAcc() {
        anchor.getChildren().clear();
        DeleteAccount dacc = new DeleteAccount();
        AnchorPane pane = DeleteAccount.createPane();
        anchor.getChildren().add(pane);
    }
}
