package org.aapsasha.desktopapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;


public class ParserApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ParserApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 650, 300);
        stage.setTitle("Math expression parser");
        stage.setScene(scene);

        InputStream iconStream = getClass().getResourceAsStream("icon2.png");
        Image image = new Image(iconStream);
        stage.getIcons().add(image);
        scene.getStylesheets().add(getClass().getResource("demo3.css").toExternalForm());
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}