module com.example.lab4var23 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.lab4var23 to javafx.fxml;
    exports com.example.lab4var23;
}