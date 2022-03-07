module snaaaaaaaaake.snakegame {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens snaaaaaaaaake.snakegame to javafx.fxml;
    exports snaaaaaaaaake.snakegame;
}