module io.github.andcandido.jogodavelha {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens io.github.andcandido.tictactoe to javafx.fxml, javafx.controls;
    exports io.github.andcandido.tictactoe;
}