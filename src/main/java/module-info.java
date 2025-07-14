module com.example.drinksproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jdk.jconsole;


    opens com.example.drinksproject to javafx.fxml;
    exports com.example.drinksproject;
}