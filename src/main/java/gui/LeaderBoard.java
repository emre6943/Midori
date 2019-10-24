package gui;

import db.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LeaderBoard {


    /**
     * leader board with friends.
     * @return the menu
     */
    public static  AnchorPane createPane() {

        AnchorPane anc = new AnchorPane();
        anc.setPrefSize(380,480);

        //javafx.scene.control.TableColumn<Account,String> mailColum = new TableColumn<>("Mail");
        // mailColum.setMinWidth(200);
        // mailColum.setCellValueFactory(new PropertyValueFactory<>("mail"));

        javafx.scene.control.TableColumn<Account,String>
            userNameColum = new TableColumn<>("Username");
        userNameColum.setMinWidth(190);
        userNameColum.setCellValueFactory(new PropertyValueFactory<>("username"));

        javafx.scene.control.TableColumn<Account,Double> pointColum = new TableColumn<>("Points");
        pointColum.setMinWidth(190);
        pointColum.setCellValueFactory(new PropertyValueFactory<>("point"));
        javafx.scene.control.TableView<db.Account> friendP;

        friendP = new TableView<>();
        friendP.setItems(getFriendsP(Login.getMail()));
        friendP.getColumns().addAll(userNameColum, pointColum);

        friendP.setPrefSize(380,480);
        anc.getChildren().add(friendP);
        return anc;

    }


    /**
     * GETS friends list to approve.
     * @param mail mail of one that got the requests
     * @return returns account arraylist
     */
    public static  ObservableList<Account> getFriendsP(String mail) {
        ObservableList<Account> accs = null;
        try {
            accs = FXCollections.observableArrayList(
                server.ClientMet.cleanerFriendP(server.ClientMet.getFriendsPoints(mail)));
        } catch (IOException e) {
            System.out.println("?");
        }
        return accs;
    }
}
