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
    private Button[][] buttons;

    private boolean isTurnPlayerX = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addButtonsOnGrid();
    }

    private void addButtonsOnGrid() {
        buttons = new Button[3][3];

        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[0].length; col++) {
                buttons[row][col] = new Button();
                setButtonAttribultes(buttons[row][col]);

                gridButtons.add(buttons[row][col], col, row);
            }
        }
    }

    private void setButtonAttribultes(Button button) {
        button.setFont(new Font("Arial", 40));
        button.setPrefSize(100, 100);

        button.setOnAction(actionEvent -> {
            setTextByTurnPlayer(button);

            String currentTurnPlayer = isTurnPlayerX ? "X" : "O";
            if(isPlayerWon(currentTurnPlayer)) {
                openDialogBox("Jogador '" + currentTurnPlayer + "' venceu!");
                resetGame();
            }

            if(checkIsTie()) {
                openDialogBox("O jogo empatou!");
                resetGame();
            }

            changeTurnPlayer();
        });
    }

    private void setTextByTurnPlayer(Button button) {
        if(!Objects.equals(button.getText(), "")) return;

        button.setText(isTurnPlayerX ? "X" : "O");
    }

    private boolean isPlayerWon(String player) {
         return isHorizontalWin(0, player)
             || isHorizontalWin(1, player)
             || isHorizontalWin(2, player)
             || isVerticalWin(0, player)
             || isVerticalWin(1, player)
             || isVerticalWin(2, player)
             || isDiagonalAscendingWin(player)
             || isDiagonalDescendingWin(player);
    }

    private boolean isHorizontalWin(int row, String player) {
        boolean isWin = true;

        for (int col = 0; col < buttons.length; col++) {
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

        int col = 2;
        for (int row = 0; row < buttons.length; row++) {
            if (!Objects.equals(buttons[row][col].getText(), player)) {
                return false;
            }

            col--;
        }

        return isWin;
    }

    private void resetGame() {
        clearButtonsText();
    }

    private void clearButtonsText() {
        for (Button[] buttonsCol : buttons) {
            for (Button button : buttonsCol) {
                button.setText("");
            }
        }
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
}