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

        for (int col = 0; col < buttons.length; col++) {
            for (int row = 0; row < buttons[0].length; row++) {
                buttons[col][row] = new Button();
                setButtonAttribultes(buttons[col][row]);

                gridButtons.add(buttons[col][row], col, row);
            }
        }
    }

    private void setButtonAttribultes(Button button) {
        button.setFont(new Font("Arial", 40));
        button.setPrefSize(100, 100);

        button.setOnAction(actionEvent -> {
            setTextByTurnPlayer(button);

            if(isPlayerWon("X") || isPlayerWon("O")) {
                resetGame();
                String playerWin = isTurnPlayerX ? "O" : "X";
                openDialogBox("Jogador '" + playerWin + "' venceu!");
            }

            if(checkIsTie()) {
                resetGame();
                openDialogBox("O jogo empatou!");
            }
        });
    }

    private void setTextByTurnPlayer(Button button) {
        if(!Objects.equals(button.getText(), "")) return;

        button.setText(isTurnPlayerX ? "X" : "O");
        isTurnPlayerX = !isTurnPlayerX;
    }

    private boolean isPlayerWon(String player) {
         return isHorizontalWin(0, player)
             || isHorizontalWin(1, player)
             || isHorizontalWin(2, player)
             || isVerticalWin(0, player)
             || isVerticalWin(1, player)
             || isVerticalWin(2, player)
             || isDiagonalWin(player);
    }

    private boolean isHorizontalWin(int row, String player) {
        return Objects.equals(buttons[0][row].getText(), player)
            && Objects.equals(buttons[1][row].getText(), player)
            && Objects.equals(buttons[2][row].getText(), player);
    }

    private boolean isVerticalWin(int col, String player) {
        return Objects.equals(buttons[col][0].getText(), player)
            && Objects.equals(buttons[col][1].getText(), player)
            && Objects.equals(buttons[col][2].getText(), player);

    }

    private boolean isDiagonalWin(String player) {
        return Objects.equals(buttons[0][0].getText(), player)
            && Objects.equals(buttons[1][1].getText(), player)
            && Objects.equals(buttons[2][2].getText(), player)

            || Objects.equals(buttons[0][2].getText(), player)
            && Objects.equals(buttons[1][1].getText(), player)
            && Objects.equals(buttons[2][0].getText(), player);
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