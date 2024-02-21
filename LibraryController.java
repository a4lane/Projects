package edu.miracosta.cs112.library;


import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.layout.VBox;



import java.util.Random;

public class LibraryController{

    public static Random rng = new Random();
    private static Library[] books = {
            new Paperback(rng.nextInt(8999) + 1000, "The Corrections", "Jonathan Franzen", "Fiction", true, null, "TheCorrections.png", 566),
            new Audiobook(rng.nextInt(8999) + 1000, "Gingerbread", "Helen Oyeyemi", "Fiction", false, new User(), "Gingerbread.png", "7:29"),
            new Paperback(rng.nextInt(8999) + 1000, "On Earth We're Briefly Gorgeous", "Ocean Vuong", "Fiction", true, null, "OnEarthWereBrieflyGorgeous.png", 242)


    };

    //LOGIN SCENE
    @FXML
    private Label label;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private Button createAccountButton;


    //HOME SCENE
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button viewAccountButton;







    protected void onCreateAccountButtonClick() {

        String name = firstNameTextField.getText() + " " + lastNameTextField.getText();
        Random rng = new Random();
        User user = new User(rng.nextInt(8999) + 1000, name, 0.0);



    }


    public void setScene2(User user) {


        VBox book1 = new VBox(new ImageView(books[0].getImage()), new Label(books[0].getTitle()));
        VBox book2 = new VBox(new ImageView(books[1].getImage()), new Label(books[1].getTitle()));
        VBox book3 = new VBox(new ImageView(books[2].getImage()), new Label(books[2].getTitle()));
    }

}