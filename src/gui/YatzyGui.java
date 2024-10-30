package gui;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class YatzyGui extends Application {

    Label test = new Label("Antal Kast Tilbage: 99");
    Button throwdice = new Button("Kast terningerne");
    Label labelTotal = new Label("Total");
    TextField txtTotal = new TextField();
    Label labelSum = new Label("Sum");
    Label labelBonus = new Label("Bonus");
    TextField txtSum = new TextField();
    TextField txtBonus = new TextField();
    int margin = 5;


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Yatzy GUI");
        GridPane pane = new GridPane();
        this.initContent(pane);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void initContent(GridPane pane) {

        //Show grid
        pane.setGridLinesVisible(false);
        pane.setPadding(new Insets(20));

        // Create a top and bottom section of the UI
        GridPane topPane = new GridPane();
        GridPane botPane = new GridPane();

        // Add pane

        pane.add(topPane, 0, 0);
        pane.add(botPane, 0, 1);

        // Create a margin for the two panes

        topPane.setPadding(new Insets(10, 10, 10, 10));

        botPane.setPadding(new Insets(10, 10, 10, 10));


        // Create all the stuff in the top panel

        // Create dice square
        for (int i = 0; i < 5; i++) {

            Rectangle square = new Rectangle(45, 45);
            GridPane.setMargin(square, new Insets(margin));

            square.setFill(Color.LIGHTGRAY);
            square.setStroke(Color.DARKRED.saturate());
            square.setArcWidth(25);
            square.setArcHeight(25);

            topPane.add(square, i, 0); // Add square to the first row of each column

            // Create checkbox and add them under the squares
            CheckBox holdCheckBox = new CheckBox("Hold");
            GridPane.setMargin(holdCheckBox, new Insets(margin));

            topPane.add(holdCheckBox, i, 1);
        }


        // add bottom throw dice
        topPane.add(throwdice, 3, 3,2,1);

        topPane.add(test, 0, 3, 3, 1);


        // Create all the stuff in the bottom panel


        // Create label of single die
        for (int i = 0; i < 6; i++) {
            Label onesLabel = new Label(i + 1 + "'ere");
            GridPane.setMargin(onesLabel, new Insets(margin));
            botPane.add(onesLabel, 0, i);


            TextField onesField = new TextField();
            GridPane.setMargin(onesField, new Insets(margin));

            onesField.setPrefWidth(50);
            botPane.add(onesField, 1, i);
        }

        //Create sum and Bonus fields with margins


        //margins
        GridPane.setMargin(labelSum, new Insets(margin));
        GridPane.setMargin(labelBonus, new Insets(margin));
        GridPane.setMargin(txtBonus, new Insets(margin));
        GridPane.setMargin(txtSum, new Insets(margin));


        botPane.add(labelSum, 5, 6);
        botPane.add(txtSum, 6, 6);
        txtSum.setPrefWidth(50);
        botPane.add(labelBonus, 5, 7);
        botPane.add(txtBonus, 6, 7);
        txtBonus.setPrefWidth(50);


        // Create txtfields for pairs and an array for diffrent names for the labels! smart nok alligevel

        String[] pairName = {"et par", "to par", "3 ens", "4 ens", "lille straight", "Store straight", "Fuldt hus", "Chance", "Yatzy"};
        for (int i = 0; i < 9; i++) {
            Label pairLebel = new Label(pairName[i]);
            GridPane.setMargin(pairLebel, new Insets(margin));

            TextField txtFieldPair = new TextField();
            GridPane.setMargin(txtFieldPair, new Insets(margin));

            txtFieldPair.setPrefWidth(50);
            botPane.add(pairLebel, 0, i + 8);
            botPane.add(txtFieldPair, 1, i + 8);

        }

        botPane.add(txtTotal, 6, 18);
        txtTotal.setPrefWidth(50);

        botPane.add(labelTotal, 5, 18);
    }
}

