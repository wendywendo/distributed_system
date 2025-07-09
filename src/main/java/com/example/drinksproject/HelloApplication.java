package com.example.drinksproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // ✅ Fixed resource loading
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/drinksproject/login.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Lemonade Farm");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
