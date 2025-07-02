module com.example.drinksproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.j;


    opens com.example.drinksproject to javafx.fxml;
    exports com.example.drinksproject;
}