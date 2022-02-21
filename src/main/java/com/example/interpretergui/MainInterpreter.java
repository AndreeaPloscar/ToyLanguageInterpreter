package com.example.interpretergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MainInterpreter extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            StackPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("list-view.fxml")));
            Scene scene = new Scene(root,800,500);
            stage.setScene(scene);
            stage.setTitle("Intepreter");
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}