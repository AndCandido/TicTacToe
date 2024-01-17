package io.github.andcandido.tictactoe.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class MainController {
    private GameController gameController;

    @FXML
    private GridPane gridButtons;
    @FXML
    private Spinner<Integer> spinnerButtonsLayout;
    @FXML
    private Label labelMatches;
    @FXML
    private Label labelPlayerXWins;
    @FXML
    private Label labelPlayerOWins;
    @FXML
    private Label labelTies;
    @FXML
    private Button buttonResetGame;

    private int quantityButtonsLayout = 3;
    private boolean isTurnPlayerX = true;
    private int matches = 0;
    private int playerXWins = 0;
    private int playerOWins = 0;
    private int ties = 0;

    @FXML
    private void initialize() {
        this.gameController = new GameController(this);
        this.gameController.init();
        initSpinnerButtonsLayout();
        initButtonReset();
    }

    private void initSpinnerButtonsLayout() {
        spinnerButtonsLayout.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 9, 3)
        );
        spinnerButtonsLayout.valueProperty().addListener((obs, oldValue, newValue) -> {
            restartGridButtons(newValue);
        });
    }

    private void initButtonReset() {
        buttonResetGame.setOnAction(actionEvent -> {
            gameController.clearButtonsText();
        });
    }

    protected void openDialogBox(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setContentText(message);
        ButtonType buttonOkDone = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonOkDone);
        dialog.showAndWait();
    }

    protected void restartGridButtons(Integer newButtonsLayout) {
        this.quantityButtonsLayout = newButtonsLayout;
        this.gameController.initGridButtons();
    }

    protected void plusMatches() {
        matches++;
        labelMatches.setText(String.valueOf(matches));
    }

    protected void plusPlayerWins(String currentTurnPlayer) {
        if(currentTurnPlayer.equals("X")) {
            plusPlayerXWins();
        } else {
            plusPlayerOWins();
        }
    }

    protected void plusPlayerXWins() {
        playerXWins++;
        labelPlayerXWins.setText(String.valueOf(playerXWins));
    }

    protected void plusPlayerOWins() {
        playerOWins++;
        labelPlayerOWins.setText(String.valueOf(playerOWins));
    }

    protected void plusTies() {
        ties++;
        labelTies.setText(String.valueOf(ties));
    }

    public int getQuantityButtonsLayout() {
        return quantityButtonsLayout;
    }

    public GridPane getGridButtons() {
        return gridButtons;
    }

    public boolean isTurnPlayerX() {
        return isTurnPlayerX;
    }

    public void setTurnPlayerX(boolean turnPlayerX) {
        isTurnPlayerX = turnPlayerX;
    }
}
