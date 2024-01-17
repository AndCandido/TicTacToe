package io.github.andcandido.tictactoe.controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.Objects;

public class GameController {
    private final MainController mainController;

    private Button[][] buttons;

    public GameController(MainController mainController) {
        this.mainController = mainController;
    }

    public void init() {
        initGridButtons();
    }

    protected void initGridButtons() {
        GridPane gridButtons = mainController.getGridButtons();
        gridButtons.getChildren().clear();
        int quantityButtonsLayout = mainController.getQuantityButtonsLayout();

        buttons = new Button[quantityButtonsLayout][quantityButtonsLayout];

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

        button.setText(mainController.isTurnPlayerX() ? "X" : "O");
    }

    private void checkWin() {
        String currentTurnPlayer = mainController.isTurnPlayerX() ? "X" : "O";
        if(!isPlayerWon(currentTurnPlayer)) return;

        mainController.openDialogBox("Jogador '" + currentTurnPlayer + "' venceu!");
        resetGame();
        mainController.plusPlayerWins(currentTurnPlayer);
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

    private void resetGame() {
        clearButtonsText();
        mainController.plusMatches();
    }

    protected void clearButtonsText() {
        for (Button[] buttonsCol : buttons) {
            for (Button button : buttonsCol) {
                button.setText("");
            }
        }
    }

    private void checkTie() {
        if(checkIsTie()) {
            mainController.openDialogBox("O jogo empatou!");
            resetGame();
            mainController.plusTies();
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

    private void changeTurnPlayer() {
        boolean isTurnPlayerX = mainController.isTurnPlayerX();
        mainController.setTurnPlayerX(!isTurnPlayerX);
    }
}
