module com.example.bd_iage_fx_2024_dk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.bd_iage_fx_2024_dk to javafx.fxml;
    exports com.example.bd_iage_fx_2024_dk;
    exports com.example.bd_iage_fx_2024_dk.model;
}