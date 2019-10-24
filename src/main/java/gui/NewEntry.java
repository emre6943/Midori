package gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import server.ClientMet;

import java.io.IOException;
import java.util.ArrayList;

public class NewEntry {

    public static AnchorPane feedbackPane;
    public static AnchorPane popup;
    public static AnchorPane anchor;

    /**
     * Creates new entry menu ..  . .. . .. . ...
     * @return the menu
     */
    public  AnchorPane createPane() {
        AnchorPane anchorPane = new AnchorPane();
        anchor = anchorPane;
        anchorPane.setPrefSize(380,480);
        //food button
        JFXButton foodButton = new JFXButton("Food");
        foodButton.setTooltip(new Tooltip("Food-related actions"));
        setLayoutButton(100, 40, foodButton);
        foodButton.setPrefSize(300,60);
        foodButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openNeF();
            }
        });
        setDarkID(foodButton);
        //energy button
        JFXButton energyButton = new JFXButton("Energy");
        energyButton.setTooltip(new Tooltip("Energy-related actions"));
        setLayoutButton(175, 40, energyButton);
        energyButton.setPrefSize(300,60);
        energyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openNeE();
            }
        });
        setDarkID(energyButton);
        //transport button
        JFXButton transportButton = new JFXButton("Transport");
        transportButton.setTooltip(new Tooltip("Transport-related actions"));
        setLayoutButton(250, 40, transportButton);
        transportButton.setPrefSize(300,60);
        transportButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client client = new Client();
                client.openNeT();
            }
        });
        setDarkID(transportButton);
        Label credits = new Label();
        if (Login.getTheme()) {
            credits.setId("darkTextColor");
        } else {
            credits.setId("credits");
        }
        credits.setText("Sources for carbon footprint data: \n'carbonfootprint.com' "
            + "and 'impact.brighterplanet.com/models/u'");
        credits.setPrefSize(300,60);
        credits.setLayoutX(40);
        credits.setLayoutY(420);
        AnchorPane aaa = createFeedbackPane();
        feedbackPane = aaa;
        anchorPane.getChildren().addAll(aaa, foodButton, energyButton, transportButton, credits);
        return anchorPane;
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
        label.setLayoutX(69);
        label.setLayoutY(10);
        feedbackPane.getChildren().add(label);
    }

    /**
     * Creates a pane to hold feedback.
     */
    public static void createPopupPane() {
        AnchorPane anchorPane = new AnchorPane();
        popup = anchorPane;
        anchorPane.setPrefSize(300,40);
        anchorPane.setLayoutY(50);
        anchorPane.setLayoutX(40);
        popup.setId("popup");

        Label closeButton = new Label("x");
        closeButton.setId("closeButton");
        closeButton.setLayoutX(280);
        closeButton.setLayoutY(0);
        closeButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closePopup();
            }
        });
        Label label = new Label("ACHIEVEMENT UNLOCKED");
        label.setLayoutX(30);
        label.setLayoutY(10);

        anchorPane.getChildren().addAll(closeButton, label);

        anchor.getChildren().add(anchorPane);
    }

    /**
     * closes popup thing.
     */
    public static void closePopup() {
        anchor.getChildren().remove(popup);
    }

    /**
     * helper for what to do on press.
     * @param veganMeal the box
     */
    public static void buttonPress(JFXComboBox veganMeal) {
        String selectedMeal = (String) veganMeal.getValue();
        if (selectedMeal == null || selectedMeal.equals("")) {
            showFeedback("Select or create a vegetarian meal option");
            return;
        }
        try {
            ArrayList<db.Meal> meals =
                ClientMet.cleanerMeals(ClientMet.getMeals(Login.getMail()));
            for (int i = 0; i < meals.size() ; i++) {
                if (meals.get(i).getMeal().equals(selectedMeal)) {
                    JSONObject js = new JSONObject(ClientMet.addVeganMeal(
                        Login.getMail(), (int)meals.get(i).getPrice()));
                    showFeedback("In total you used "
                        + js.getDouble("total meals") + " euros for vegatarian meals");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            showFeedback("An error has occurred");
            e.printStackTrace();
        }
    }

    /**
     * creates pane for "food".
     * @return the pane
     */
    public AnchorPane createPaneFood() {
        AnchorPane anchorPane = new AnchorPane();
        anchor = anchorPane;
        anchorPane.setPrefSize(380,480);
        try {
            ClientMet.cleanerMeals(ClientMet.getMeals(Login.getMail()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JFXComboBox veganMeal = new JFXComboBox();
        veganMeal.setPromptText("Eat a vegetarian meal");
        veganMeal.setLayoutY(76);
        veganMeal.setLayoutX(26);
        veganMeal.setPrefSize(240,60);
        veganMeal.setTooltip(new Tooltip("Choose a vegetarian meal"));
        ArrayList<db.Meal> ms = new ArrayList<>();
        try {
            ms = ClientMet.cleanerMeals(ClientMet.getMeals(Login.getMail()));
            for (int i = 0; i < ms.size(); i++) {
                veganMeal.getItems().add(ms.get(i).getMeal());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Login.getTheme()) {
            veganMeal.setId("darkButton");
        }
        JFXButton veganButton = new JFXButton("Add");
        veganButton.setTooltip(new Tooltip("Add the selected meal to your score"));
        setLayoutButton(76, 296, veganButton);
        veganButton.setPrefSize(60,60);
        veganButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                buttonPress(veganMeal);
            }
        });
        setDarkID(veganButton);
        JFXTextField userMeal = new JFXTextField();
        userMeal.setPromptText("Create your own meal");
        userMeal.setTooltip(new Tooltip("enter the name of your meal"));
        userMeal.setPrefSize(180,60);
        setLayoutField(166, 26, userMeal);
        setDarkField(userMeal);
        JFXTextField userPrice = new JFXTextField();
        userPrice.setPromptText("price (€)");
        userPrice.setTooltip(new Tooltip("enter the price of your meal"));
        userPrice.setPrefSize(60,60);
        setLayoutField(166, 206, userPrice);
        setDarkField(userPrice);
        JFXButton userButton = new JFXButton("Add");
        userButton.setTooltip(new Tooltip("Add your meal to your score"));
        userButton.setPrefSize(60,60);
        setLayoutButton(166, 296, userButton);
        userButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String meal = userMeal.getText();
                String priceS = userPrice.getText();
                if (meal.equals("")) {
                    showFeedback("Meal name can not be left empty");
                    return;
                }
                if (priceS.equals("")) {
                    showFeedback("Price field can not be empty");
                    return;
                }
                double price = Double.parseDouble(priceS);
                try {
                    ClientMet.addMeal(Login.getMail(),meal,price);
                    Client client = new Client();
                    client.openNeF();
                    showFeedback("Added " + meal + " to your meals");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        setDarkID(userButton);
        userPrice.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    userPrice.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        JFXTextField localF = new JFXTextField();
        localF.setPromptText("price (€)");
        setLayoutField(272,296, localF);
        localF.setPrefSize(60, 60);
        localF.setTooltip(new Tooltip("The amount of money you spent on local products"));
        localF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    localF.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(localF);
        JFXButton localProducts = new JFXButton("Buy local products");
        setLayoutButton(272, 26,localProducts );
        localProducts.setPrefSize(240,60);
        localProducts.setTooltip(new Tooltip("Adds local products to your score"));
        localProducts.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (localF.getText() == null || localF.getText().equals("")) {
                    showFeedback("Price field can not be empty");
                    return;
                }
                try {
                    JSONObject js = new JSONObject(ClientMet.addLocal(
                        Login.getMail(), Integer.valueOf(localF.getText())));
                    showFeedback("In total you used "
                        + js.getDouble("total local") + " euros for local shopping");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                } catch (IOException e) {
                    showFeedback("An error has occurred");
                    e.printStackTrace();
                }
            }
        });
        setDarkID(localProducts);
        AnchorPane aaa = createFeedbackPane();
        feedbackPane = aaa;
        JFXButton backButton = createBackButton();
        setDarkID(backButton);
        anchorPane.getChildren().addAll(aaa, veganMeal,
            localProducts, backButton, localF, veganButton, userButton, userMeal,userPrice);
        return anchorPane;
    }

    /**
     * bikes button Helper.
     * @param bikes text
     */
    public static void bikeHelpbutton(JFXTextField bikes) {
        try {
            JSONObject js = new JSONObject(ClientMet.addBike(
                Login.getMail(), Integer.valueOf(bikes.getText())));
            showFeedback("Added " + js.getDouble("Km") + " points for bike ridden");
            if (js.getBoolean("newReward")) {
                createPopupPane();
            }
        } catch (IOException e) {
            showFeedback("An error has occurred");
            e.printStackTrace();
        }
    }

    /**
     * Creates Pane for food.
     * @return the pane
     */
    public AnchorPane createPaneTransport() {
        AnchorPane anchorPane = new AnchorPane();
        anchor = anchorPane;
        anchorPane.setPrefSize(380,480);
        //entry
        JFXTextField bikes = new JFXTextField("KM");
        bikes.setLayoutX(310);
        bikes.setLayoutY(100);
        bikes.setPrefSize(30, 60);
        bikes.setTooltip(new Tooltip("number of km ridden"));
        bikes.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    bikes.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(bikes);

        JFXButton bike = new JFXButton("Bike");
        bike.setLayoutY(100);
        bike.setLayoutX(40);
        bike.setPrefSize(260,60);
        bike.setTooltip(new Tooltip("Adds riding a bike to your score"));
        bike.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                bikeHelpbutton(bikes);
            }
        });
        setDarkID(bike);
        JFXTextField walks = new JFXTextField("KM");
        setLayoutField(175, 310, walks);
        walks.setPrefSize(30,60);
        walks.setTooltip(new Tooltip("number of km walked"));
        walks.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    walks.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(walks);
        JFXButton walk = new JFXButton("Walk");
        setLayoutButton(175, 40, walk);
        walk.setPrefSize(260,60);
        walk.setTooltip(new Tooltip("Adds distance walked to your score"));
        walk.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject js = new JSONObject(ClientMet.addWalk(
                        Login.getMail(), Integer.valueOf(walks.getText())));
                    showFeedback("Added " + js.getString("Km") + " points for walked");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                } catch (IOException e) {
                    showFeedback("An error has occurred");
                    e.printStackTrace();
                }
            }
        });
        setDarkID(walk);
        JFXTextField buss = new JFXTextField("KM");
        setLayoutField(250, 310, buss);
        buss.setPrefSize(30,60);
        buss.setTooltip(new Tooltip("number of km travelled by bus"));
        // force the field to be numeric only
        buss.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    buss.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(buss);
        JFXButton bus = new JFXButton("Take the bus");
        setLayoutButton(250, 40, bus);
        bus.setPrefSize(260,60);
        bus.setTooltip(new Tooltip("Adds km of bus taken to your score"));
        bus.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject js = new JSONObject(ClientMet.addBus(
                        Login.getMail(), Integer.valueOf(buss.getText())));
                    showFeedback("Added " + js.getString("Km") + " points for bus");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                } catch (IOException e) {
                    showFeedback("An error has occurred");
                    e.printStackTrace();
                }
            }
        });
        setDarkID(bus);
        JFXTextField trains = new JFXTextField("KM"); //entry
        setLayoutField(325, 310, trains);
        trains.setPrefSize(30,60);
        trains.setTooltip(new Tooltip("number of km travelled by train"));
        trains.textProperty().addListener(new ChangeListener<String>() {
            @Override // force the field to be numeric only
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    trains.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(trains);
        JFXButton train = new JFXButton("Take the train");
        setLayoutButton(325, 40, train);
        train.setPrefSize(260,60);
        train.setTooltip(new Tooltip("Adds distance travelled by train to your score"));
        train.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                trainHelper(trains);
            }
        });
        setDarkID(train);
        AnchorPane aaa = createFeedbackPane();
        feedbackPane = aaa;
        JFXButton backButton = createBackButton();
        setDarkID(backButton);
        anchorPane.getChildren().addAll(backButton, aaa, buss, walks,
            trains, bikes, bike, bus, train, walk);
        return anchorPane;
    }

    /**
     * helper for chekstyling on click.
     * @param trains text Field
     */
    public static void trainHelper(JFXTextField trains) {
        try {
            JSONObject js = new JSONObject(ClientMet.addTrain(
                Login.getMail(), Integer.valueOf(trains.getText())));
            showFeedback("Added " + js.getString("Km") + " points for train");
            if (js.getBoolean("newReward")) {
                createPopupPane();
            }
        } catch (IOException e) {
            showFeedback("An error has occurred");
            e.printStackTrace();
        }
    }


    /**
     * Creates Pane for energy.
     * @return the pane
     */
    public AnchorPane createPaneEnergy() {
        AnchorPane anchorPane = new AnchorPane();
        anchor = anchorPane;
        anchorPane.setPrefSize(380,480);

        //entry
        JFXTextField temps = new JFXTextField("°C");
        setLayoutField(150, 350, temps);
        temps.setPrefSize(30,60);
        temps.setTooltip(new Tooltip("number of degrees (Celsius) lowered"));
        // force the field to be numeric only
        temps.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    temps.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(temps);

        JFXButton temperature = new JFXButton("Lower the temperature");
        setLayoutButton(150, 40, temperature);
        temperature.setPrefSize(300,60);
        temperature.setTooltip(new Tooltip(
            "Adds points every day depending on how much you lower your temperature."
                + "You only have to press this button once, it updates daily."));
        temperature.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject js = new JSONObject(ClientMet.addHeat(Login.getMail(),
                        Integer.valueOf(temps.getText()),System.currentTimeMillis()));
                    showFeedback("Added "
                        + js.getString("totalPointsHeating") + " points to heating");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                } catch (IOException e) {
                    showFeedback("An error has occurred");
                    e.printStackTrace();
                }
            }
        });
        setDarkID(temperature);
        JFXTextField solars = new JFXTextField("NUM");
        setLayoutField(225, 350, solars );
        solars.setPrefSize(30,60);
        solars.setTooltip(new Tooltip("number of solar panels installed"));
        // force the field to be numeric only
        solars.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    solars.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        setDarkField(solars);
        JFXButton solarPanels = new JFXButton("Install solar panels");
        setLayoutButton(225, 40, solarPanels);
        solarPanels.setPrefSize(300,60);
        solarPanels.setTooltip(new Tooltip(
            "Adds points every day depending on how many solar panels you have installed. "
                + "You only have to press this button once, it updates daily."));
        solarPanels.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    JSONObject js = new JSONObject(ClientMet.addSolar(Login.getMail(),
                        Integer.valueOf(solars.getText()) ,System.currentTimeMillis()));
                    showFeedback("Added "
                        + js.getString("totalPointsSolar") + " points to solar panels");
                    if (js.getBoolean("newReward")) {
                        createPopupPane();
                    }
                } catch (IOException e) {
                    showFeedback("An error has occurred");
                    e.printStackTrace();
                }
            }
        });
        setDarkID(solarPanels);
        JFXButton backButton = createBackButton();
        setDarkID(backButton);
        AnchorPane aaa = createFeedbackPane();
        feedbackPane = aaa;

        anchorPane.getChildren().addAll(aaa, temps, solars, backButton, temperature, solarPanels);
        return anchorPane;
    }

    /**
     * Creates back button.
     * @return the pane
     */

    public JFXButton createBackButton() {
        JFXButton backButton = new JFXButton("Back");
        setLayoutButton(425, 40, backButton);
        backButton.setPrefSize(300,60);
        backButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client cl = new Client();
                cl.openNe();
            }
        });
        return backButton;
    }

    /**
     * shows feedback on achievement.
     * @param text the feedback
     */
    public static void showFeedbackAchivement(String text) {
        Label label = new Label(text);
        label.setLayoutX(10);
        label.setLayoutY(0);
        feedbackPane.getChildren().add(label);
    }

    /**
     * checks if theme changed and if it has change button style to dark theme.
     * @param button the button you want to change
     */
    public static void setDarkID(JFXButton button) {
        if (Login.getTheme()) {
            button.setId("darkButton");
        }
    }

    /**
     * checks if theme changed and if it has change field style to dark theme.
     * @param field the field you want to change
     */
    public static void setDarkField(JFXTextField field) {
        if (Login.getTheme()) {
            field.setId("darkButton");
        }
    }

    /**
     * sets the x and y layout of a button.
     * @param layoutX sets the X layout of a button
     * @param layoutY sets the Y layout of a button
     * @param button the button of which to change it
     */
    public static void setLayoutButton(int layoutY, int layoutX, JFXButton button ) {
        button.setLayoutX(layoutX);
        button.setLayoutY(layoutY);
    }

    /**
     * sets the x and y layout of a field.
     * @param layoutX sets the X layout of a button
     * @param layoutY sets the Y layout of a button
     * @param field the field of which to change it
     */
    public static void setLayoutField(int layoutY, int layoutX, JFXTextField field ) {
        field.setLayoutX(layoutX);
        field.setLayoutY(layoutY);
    }
}
