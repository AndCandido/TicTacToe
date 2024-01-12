module io.github.andcandido.jogodavelha {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens io.github.andcandido.tictactoe to javafx.fxml;
    exports io.github.andcandido.tictactoe;
}