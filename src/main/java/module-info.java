module org.example.trabalho02_po {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.trabalho02_po to javafx.fxml;
    exports org.example.trabalho02_po;
}