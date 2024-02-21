package edu.miracosta.cs112.library;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;



    public class LibraryApplication extends Application {

        @Override
        public void start(Stage primaryStage) throws IOException{
            FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("library-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 500);
            primaryStage.setTitle("City Library");
            primaryStage.setScene(scene);
            primaryStage.show();

        }


        public static void main(String[] args) {
            launch(args);
        }
    }
