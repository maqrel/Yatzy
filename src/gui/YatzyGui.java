package gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Die;
import models.Rafflecup;
import models.YatzyResultCalculator;


public class YatzyGui extends Application {
    private Rafflecup rafflecup = new Rafflecup();

    // Components for the upperSectionPane
    private final HBox eyesPane = new HBox(10);
    private final HBox holdPane = new HBox(13);
    private final HBox infoActionPane = new HBox(10); // InfoActionPane has some global components
    private final Label numberOfThrowsLeftLabel = new Label(String.valueOf(rafflecup.getNumberOfThrowsLeft()));

    // Components for the bottomSectionPane;
    private final VBox scoreTextsPane = new VBox(16);
    ;
    private final VBox scoreTextFieldsPane = new VBox(7);
    private final VBox chooseScoreButtonsPane = new VBox(7);
    private final GridPane scoreInfoPane = new GridPane(0, 7); //scoreInfoPane has some global components
    private final TextField sumScoreTextField = new TextField();
    private final TextField bonusScoreTextField = new TextField();
    private final TextField totalScoreTextField = new TextField();


    @Override
    public void start(Stage primaryStage) {
        // Create the pane wholeSection which is the window to be displayed.
        primaryStage.setTitle("Yatzy");
        VBox wholeSection = new VBox(10);
        wholeSection.setPadding(new Insets(10, 10, 10, 10));

        // Create its sub panes, which are the upper section and bottom section.
        VBox upperSectionPane = initUpperSectionPane();
        HBox bottomSectionPane = initBottomSectionPane();

        // Add them to the wholeSection.
        wholeSection.getChildren().addAll(upperSectionPane, bottomSectionPane);

        // Create scene, set the scene in the stage and show the stage.
        Scene scene = new Scene(wholeSection);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Methods to create upperSectionPane
    private VBox initUpperSectionPane() {
        // Create pane to hold the upperSection and set its layout
        VBox upperSectionPane = new VBox(7);
        setLayout(upperSectionPane);

        // Put things in the sub panes of the upperSectionPane
        initEyesPane();
        initHoldPane();
        initInfoActionPane();

        // Add the sub panes to the upperSectionPane
        upperSectionPane.getChildren().addAll(eyesPane, holdPane, infoActionPane);

        return upperSectionPane;
    }

    private void setLayout(Pane upperSectionPane) {
        // Setting the layout
        upperSectionPane.setPadding(new Insets(10, 10, 10, 10));
        upperSectionPane.setStyle("-fx-border-color: black");
    }

    public void initEyesPane() {
        // Create and assign 5 label to hold the eyes of the dices. Default value is 0 at the start.
        for (int i = 0; i < 5; i++) {
            Label eyesLabel = new Label(String.valueOf(0));
            eyesLabel.setStyle("-fx-pref-height: 50; -fx-pref-width: 50; -fx-alignment: center; -fx-border-color: gray; -fx-border-radius: 10;");
            eyesPane.getChildren().add(eyesLabel);
        }
    }

    public void initHoldPane() {
        // Create and assign 5 checkboxes which marks whether to hold a die for the next throw.
        for (int index = 0; index < 5; index++) {
            CheckBox holdCheckBox = new CheckBox("Hold");
            holdCheckBox.setDisable(true);
            holdPane.getChildren().add(holdCheckBox);
        }
    }

    private void initInfoActionPane() {
        // Creates and assigns the text label for numberOfThrows
        Label numberOfThrowsLeftTextLabel = new Label("Antal kast tilbage:");

        // Sets layout for global label for the actual number for how many throws are left.
        numberOfThrowsLeftLabel.setStyle("-fx-pref-width: 65; -fx-alignment: center; -fx-translate-x: -9px; -fx-translate-y: 1px;");

        // Creates and assign the button to throw the dices.
        Button throwDiceButton = new Button("Kast terningerne");
        // Add an action event when pressing the button, which is to throw the
        // dices and update states conditioned on the throw
        throwDiceButton.setOnAction(event -> throwDiceAction());

        infoActionPane.getChildren().addAll(numberOfThrowsLeftTextLabel, numberOfThrowsLeftLabel, throwDiceButton);
    }

    private void throwDiceAction() {
        // We throw the dice when numberOfThrowsLeft are bigger or equal to one.
        if (rafflecup.getNumberOfThrowsLeft() >= 1) {
            rafflecup.throwDice(); // throwing the dice.
            updateDices(); // Updating the dice based on the holdCheckbox
            updateNumberOfThrowsLeft(); // Setting the label for number of throws left again

            // When throwing the first dice, number of throws left is 2. This is the time to then
            // find out what dies we want to hold. Therefore, activating the holdCheckBoxes.
            // At the start of the game, they are defaulted to being disabled.
            if (rafflecup.getNumberOfThrowsLeft() == 2) {
                activateHoldCheckBoxes();
            }

            if (rafflecup.getNumberOfThrowsLeft() == 0) {
                updateScoreBoard();
            }
        }
    }

    private void updateNumberOfThrowsLeft() {
        // We set the numberOfThrowsLabel again to update it in the upperSectionPane.
        numberOfThrowsLeftLabel.setText(String.valueOf(rafflecup.getNumberOfThrowsLeft()));
    }

    private void updateDices() {
        Die[] dices = rafflecup.getDice();
        // We only update the dies where the holdCheckbox is not marked
        for (int index = 0; index < dices.length; index++) {
            Die die = dices[index];
            CheckBox checkBox = (CheckBox) holdPane.getChildren().get(index);
            Label label = (Label) eyesPane.getChildren().get(index);
            if (!checkBox.isSelected()) {
                label.setText(String.valueOf(die.getEyes()));
            }
        }
    }

    private void activateHoldCheckBoxes() {
        // Make all checkboxes active
        for (Node checkBox : holdPane.getChildren()) {
            CheckBox holdCheckBox = (CheckBox) checkBox;
            holdCheckBox.setDisable(false);
        }
    }

    private void updateScoreBoard() {
        Die[] dice = makeDice();
        YatzyResultCalculator yatzyResultCalculator = new YatzyResultCalculator(dice);
        updateUpperScoreBoard(yatzyResultCalculator);
        updateLowerScoreBoard(yatzyResultCalculator);
    }

    private Die[] makeDice() {
        Die[] dice = new Die[5];
        for (int index = 0; index < 5; index++) {
            Label numberOfEyesLabel = (Label) eyesPane.getChildren().get(index);
            int numberOfEyes = Integer.parseInt(numberOfEyesLabel.getText());
            dice[index] = new Die(numberOfEyes);
        }
        return dice;
    }

    private void updateUpperScoreBoard(YatzyResultCalculator yatzyResultCalculator) {
        for (int numberOfEyes = 1; numberOfEyes < 7; numberOfEyes++) {
            TextField scoreTextField = (TextField) scoreTextFieldsPane.getChildren().get(numberOfEyes - 1);
            if (!scoreTextField.isDisable()) {
                int score = yatzyResultCalculator.upperSectionScore(numberOfEyes);
                scoreTextField.setText(String.valueOf(score));
            }
        }
    }

    private void updateLowerScoreBoard(YatzyResultCalculator yatzyResultCalculator) {
        //Index 8, da lowerscoreboard starter ved plads 8
        int index = 8;
        while (index < scoreTextFieldsPane.getChildren().size()) {
            TextField score = (TextField) scoreTextFieldsPane.getChildren().get(index);
            if (!score.isDisable()) {
                if (index == 8) {
                    score.setText(String.valueOf(yatzyResultCalculator.onePairScore())); // One pair
                } else if (index == 9) {
                    score.setText(String.valueOf(yatzyResultCalculator.twoPairScore())); // Two pair
                } else if (index == 10) {
                    score.setText(String.valueOf(yatzyResultCalculator.threeOfAKindScore())); // Three of a kind
                } else if (index == 11) {
                    score.setText(String.valueOf(yatzyResultCalculator.fourOfAKindScore())); // Four of a kind
                } else if (index == 12) {
                    score.setText(String.valueOf(yatzyResultCalculator.smallStraightScore())); // Small straight
                } else if (index == 13) {
                    score.setText(String.valueOf(yatzyResultCalculator.largeStraightScore())); // Large straight
                } else if (index == 14) {
                    score.setText(String.valueOf(yatzyResultCalculator.fullHouseScore())); // Full house
                } else if (index == 15) {
                    score.setText(String.valueOf(yatzyResultCalculator.chanceScore())); // Chance
                } else {
                    score.setText(String.valueOf(yatzyResultCalculator.yatzyScore())); // Yatzy
                }
            }
            index++;
        }
    }


    // Methods to create bottomSectionPane
    private HBox initBottomSectionPane() {
        // Create pane to hold the bottomSection and set its layout
        HBox bottomSectionPane = new HBox(10);
        setLayout(bottomSectionPane);

        // Put things in the sub panes of the bottomSectionPane
        initScoreTextsPane();
        initScoreTextFieldsPane();
        initChooseScoreButtonsPane();
        initScoreInformationPane();

        // Add the sub panes to the bottomSectionPane
        bottomSectionPane.getChildren().addAll(scoreTextsPane, scoreTextFieldsPane,
                chooseScoreButtonsPane, scoreInfoPane);

        return bottomSectionPane;
    }

    private void initScoreTextsPane() {
        for (int numberOfEyes = 1; numberOfEyes < 7; numberOfEyes++) {
            Text scoreText = new Text(String.format("%d'ere", numberOfEyes));
            scoreText.setStyle("-fx-translate-y: 3.5px;");
            scoreTextsPane.getChildren().add(scoreText);
        }

        // Empty space for 2 rows.
        for (int i = 0; i < 2; i++) {
            Text emptyText = new Text();
            scoreTextsPane.getChildren().add(emptyText);
        }

        String[] scores = {"Et par", "To par", "3 ens", "4 ens", "Lille straight", "Store straight",
                "Fuldt hus", "Chance", "Yatzy"};

        for (String score : scores) {
            Text scoreText = new Text(score);
            scoreText.setStyle("-fx-translate-y: -16px;");
            scoreTextsPane.getChildren().add(scoreText);
        }
    }

    private void initScoreTextFieldsPane() {
        for (int index = 0; index < 17; index++) {
            // Empty space
            if (index == 6 || index == 7) {
                Text emptyText = new Text();
                scoreTextFieldsPane.getChildren().add(emptyText);
                continue;
            }

            TextField scoreTextFields = new TextField();
            scoreTextFields.setStyle("-fx-pref-width: 45;");
            scoreTextFields.setEditable(false);
            scoreTextFieldsPane.getChildren().add(scoreTextFields);
        }
    }

    private void initChooseScoreButtonsPane() {
        for (int index = 0; index < 18; index++) {
            if (index == 6) {
                Text sumText = new Text("Sum");
                chooseScoreButtonsPane.getChildren().add(sumText);
            } else if (index == 7) {
                Text bonusText = new Text("Bonus");
                chooseScoreButtonsPane.getChildren().add(bonusText);
            } else if (index == 17) {
                Text totalText = new Text("Total");
                chooseScoreButtonsPane.getChildren().add(totalText);
            } else {
                Button chooseScoreButton = new Button("Vælg");
                chooseScoreButton.setOnAction(event -> chooseScoreAction(chooseScoreButton));
                chooseScoreButtonsPane.getChildren().add(chooseScoreButton);
            }
        }
    }

    //Gør således at man kan klikke på "vælg" knappen.
    private void chooseScoreAction(Button chooseScoreButton) {
        boolean noMoreThrowsLeft = rafflecup.getNumberOfThrowsLeft() == 0;
        int index = chooseScoreButtonsPane.getChildren().indexOf(chooseScoreButton);
        Text scoreText = (Text) scoreTextsPane.getChildren().get(index);
        TextField scoreTextField = (TextField) scoreTextFieldsPane.getChildren().get(index);
        //if statement der gør sådan at man ikke kan klikke, hvis intet står der
        if (!scoreTextField.isDisabled() && noMoreThrowsLeft) {
            int score = Integer.parseInt(scoreTextField.getText());
            //Sætter streg igennem for at vise at den er blevet valgt
            scoreText.setStrikethrough(true);
            //Disabler den hvis den er valgt
            scoreTextField.setDisable(true);
            updateSumAndBonus(index, score);
            updateTotal(score);
            resetScoreTextFields();
            resetHoldCheckBoxes();
            createNewRaffleCup();
            updateNumberOfThrowsLeft();
        }
    }

    private void resetHoldCheckBoxes() {
        for (Node checkBox : holdPane.getChildren()) {
            CheckBox holdCheckBox = (CheckBox) checkBox;
            holdCheckBox.setSelected(false);
            holdCheckBox.setDisable(true);
        }
    }

    private void updateSumAndBonus(int index, int value) {
        if (index >= 0 && index <= 5) {
            int sumScore = sumScoreTextField.getText().isEmpty() ? 0 : Integer.parseInt(sumScoreTextField.getText());
            int nextSumScore = sumScore + value;
            if (nextSumScore >= 63) {
                bonusScoreTextField.setText(String.valueOf(50));
                updateTotal(50);
            }
            sumScoreTextField.setText(String.valueOf(nextSumScore));
        }
    }

    private void updateTotal(int value) {
        //?= hvis den er sand, er den ikke lig med nul, og hvis den er falsk er det. Dette gøres, da det er en boolean, da vi skriver .isEmpty(), efter vores textfield
        int totalScore = totalScoreTextField.getText().isEmpty() ? 0 : Integer.parseInt(totalScoreTextField.getText());
       //Her sættes totalscoren sammen med det valgte, selvom man har opnået mindste værdien(63) for at opnå bonussen på 50
        int nextTotalScore = totalScore + value;
        totalScoreTextField.setText(String.valueOf(nextTotalScore));
    }

    private void createNewRaffleCup() {
        rafflecup = new Rafflecup();
    }

    //Tekstelterne resetes, når de ikke bliver valgt
    private void resetScoreTextFields() {
        for (Node textField : scoreTextFieldsPane.getChildren()) {
            //HVis textfield har en instance af en text (nemlig de to text boxes der ligger lige under 6'er og bonusser), så springes den over
            if (textField instanceof Text) {
                continue;
            }
            //If statement hvis de ikke er klikket på, så skal de resterende resettes
            TextField scoreTextField = (TextField) textField;
            if (!scoreTextField.isDisabled()) {
                scoreTextField.clear();
            }
        }
    }

    private void initScoreInformationPane() {
        sumScoreTextField.setStyle("-fx-pref-width: 45; -fx-translate-y: 183px;");
        sumScoreTextField.setEditable(false);
        scoreInfoPane.add(sumScoreTextField, 0, 0);

        bonusScoreTextField.setStyle("-fx-pref-width: 45; -fx-translate-y: 183px;");
        bonusScoreTextField.setEditable(false);
        scoreInfoPane.add(bonusScoreTextField, 0, 1);

        totalScoreTextField.setStyle("-fx-pref-width: 45; -fx-translate-y: 393px;");
        totalScoreTextField.setEditable(false);
        scoreInfoPane.add(totalScoreTextField, 0, 11);
    }
}


