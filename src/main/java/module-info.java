module com.example.lab3_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab3_1 to javafx.fxml;
    exports com.example.lab3_1;
}