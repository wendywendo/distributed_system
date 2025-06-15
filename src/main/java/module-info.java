module com.example.drinksproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.drinksproject to javafx.fxml;
    exports com.example.drinksproject;
}