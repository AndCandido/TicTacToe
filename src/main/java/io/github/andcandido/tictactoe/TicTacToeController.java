package io.github.andcandido.tictactoe;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {

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

    private Button[][] buttons;
    private boolean isTurnPlayerX = true;
    private int matches;
    private int playerXWins;
    private int playerOWins;
    private int ties;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initSpinner();
        initGridButtons(spinnerButtonsLayout.getValue());
    }

    private void initSpinner() {
        spinnerButtonsLayout.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(3, 9, 3)
        );
        spinnerButtonsLayout.valueProperty().addListener((obs, oldValue, newValue) -> {
            initGridButtons(newValue);
        });
    }

    private void initGridButtons(int buttonsLayout) {
        gridButtons.getChildren().clear();

        buttons = new Button[buttonsLayout][buttonsLayout];

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[0].length; col++) {
                buttons[row][col] = new Button();
                setButtonAttributes(buttons[row][col]);

                gridButtons.add(buttons[row][col], col, row);
            }
        }
    }

    private void setButtonAttributes(Button button) {
        button.setFont(new Font("Arial", 40));
        button.setPrefSize(100, 100);
        button.setMinSize(100, 100);

        button.setOnAction(actionEvent -> {
            setTextByTurnPlayer(button);

            checkWin();
            checkTie();

            changeTurnPlayer();
        });
    }

    private void setTextByTurnPlayer(Button button) {
        if(!Objects.equals(button.getText(), "")) return;

        button.setText(isTurnPlayerX ? "X" : "O");
    }

    private void checkWin() {
        String currentTurnPlayer = isTurnPlayerX ? "X" : "O";
        if(!isPlayerWon(currentTurnPlayer)) return;

        openDialogBox("Jogador '" + currentTurnPlayer + "' venceu!");
        resetGame();
        plusPlayerWins(currentTurnPlayer);
    }

    private boolean isPlayerWon(String player) {
        for (int i = 0; i < buttons.length; i++) {
            if(isHorizontalWin(i, player))
                return true;
            if(isVerticalWin(i, player))
                return true;
        }

        return isDiagonalAscendingWin(player) || isDiagonalDescendingWin(player);
    }

    private boolean isHorizontalWin(int row, String player) {
        boolean isWin = true;

        for (int col = 0; col < buttons[0].length; col++) {
            if (!Objects.equals(buttons[row][col].getText(), player))
                return false;
        }

        return  isWin;
    }

    private boolean isVerticalWin(int col, String player) {
        boolean isWin = true;

        for (int row = 0; row < buttons.length; row++) {
            if(!Objects.equals(buttons[row][col].getText(), player))
                return false;
        }

        return isWin;
    }

    private boolean isDiagonalAscendingWin(String player) {
        boolean isWin = true;

        for (int i = 0; i < buttons.length; i++) {
            if(!Objects.equals(buttons[i][i].getText(), player)) {
                return false;
            }
        }

        return isWin;
    }

    private boolean isDiagonalDescendingWin(String player) {
        boolean isWin = true;

        int col = buttons[0].length - 1;
        for (int row = 0; row < buttons.length; row++) {
            if (!Objects.equals(buttons[row][col].getText(), player)) {
                return false;
            }
            col--;
        }

        return isWin;
    }

    private void checkTie() {
        if(checkIsTie()) {
            openDialogBox("O jogo empatou!");
            resetGame();
            plusTies();
        }
    }

    private boolean checkIsTie() {
        boolean isTie = true;

        for (Button[] buttonsCol : buttons) {
            for (Button button : buttonsCol) {
                if(Objects.equals(button.getText(), "")) {
                    return false;
                }
            }
        }

        return isTie;
    }

    private void resetGame() {
        clearButtonsText();
        plusMatches();
    }

    private void clearButtonsText() {
        for (Button[] buttonsCol : buttons) {
            for (Button button : buttonsCol) {
                button.setText("");
            }
        }
    }

    private void plusMatches() {
        matches++;
        labelMatches.setText(String.valueOf(matches));
    }

    private void plusTies() {
        ties++;
        labelTies.setText(String.valueOf(ties));
    }

    private void openDialogBox(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setContentText(message);
        ButtonType buttonOkDone = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonOkDone);
        dialog.showAndWait();
    }

    private void changeTurnPlayer() {
        isTurnPlayerX = !isTurnPlayerX;
    }

    private void plusPlayerWins(String currentTurnPlayer) {
        if(currentTurnPlayer.equals("X")) {
            plusPlayerXWins();
        } else {
            plusPlayerOWins();
        }
    }

    private void plusPlayerXWins() {
        playerXWins++;
        labelPlayerXWins.setText(String.valueOf(playerXWins));
    }

    private void plusPlayerOWins() {
        playerOWins++;
        labelPlayerOWins.setText(String.valueOf(playerOWins));
    }
}