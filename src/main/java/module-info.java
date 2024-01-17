module io.github.andcandido.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens io.github.andcandido.tictactoe to javafx.fxml, javafx.controls;
    exports io.github.andcandido.tictactoe;
    exports io.github.andcandido.tictactoe.controllers;
    opens io.github.andcandido.tictactoe.controllers to javafx.controls, javafx.fxml;
}