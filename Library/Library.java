package edu.miracosta.cs112.library;

import javafx.scene.image.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Library{



    /************** INSTANCE VARIABLES *************/
    protected int bookID;
    protected String title;
    protected String author;
    protected String genre;
    protected boolean available;
    protected User borrower;
    protected String imageName;

    /*************** CONSTANTS **************/
    public static final int DEFAULT_BOOK_ID = 0000;
    public static final String DEFAULT_TITLE = "Title";
    public static final String DEFAULT_AUTHOR = "Author";
    public static final String DEFAULT_GENRE = "Genre";
    public static final boolean DEFAULT_AVAILABILITY = false;
    public static final User DEFAULT_BORROWER = new User();


    /*************** CONSTRUCTORS ***************/

    public Library(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName){
        this.setAll(bookID, title, author, genre, available, borrower, imageName);
    }

    public Library(){
        this(DEFAULT_BOOK_ID, DEFAULT_TITLE, DEFAULT_AUTHOR, DEFAULT_GENRE, DEFAULT_AVAILABILITY, DEFAULT_BORROWER, null);
    }

    public Library(Library original){
        if (original == null){
            throw new IllegalArgumentException("ERROR: null object passed to copy constructor.");
        }
        this.setAll(original.bookID, original.title, original.author, original.genre, original.available, original.borrower, original.imageName);
    }


    /***************** GETTERS *******************/

    public int getBookID(){
        return this.bookID;
    }

    public String getTitle(){
        return this.title;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getGenre(){
        return this.genre;
    }

    public boolean getAvailability(){
        return this.available;
    }

    public User getBorrower(){
        return this.borrower;
    }

    public String getImageName(){
        return this.imageName;
    }

    public Image getImage() {
        FileInputStream input = null;
        try {
            input = new FileInputStream("./src/main/resources/" + this.imageName);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            System.err.println("ERROR: could not open file.");
            System.exit(0);
        }
        return new Image(input);
    }



    /**************** SETTERS ****************/

    public void setBookID(int bookID){
        this.bookID = bookID;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setAvailability(boolean available){
        this.available = available;
    }

    public void setBorrower(User borrower){
        this.borrower = borrower;
    }

    public void setImageName(String imageName){
        this.imageName = imageName;
    }

    public void setAll(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName){
        this.setBookID(bookID);
        this.setTitle(title);
        this.setAuthor(author);
        this.setGenre(genre);
        this.setAvailability(available);
        this.setBorrower(borrower);
        this.setImageName(imageName);
    }



    /****************************/

    @Override
    public String toString(){
        String output =  "ID#" + this.bookID + ": " + this.title + " by " + this.author;
        if (!this.available){
            output += "... Not Available - Borrowed by User #" + this.borrower.getUserID();
        }
        else {
            output += "... Available";
        }
        return output;
    }

    @Override
    public boolean equals(Object other){
        if (!(other instanceof Library)){
            return false;
        }
        Library that = (Library)other;
        return (this.bookID == that.bookID && this.author.equals(that.author) && this.title.equals(that.title) && this.genre.equals(that.genre) && this.available == that.available && this.borrower.equals(that.borrower) && this.imageName.equals(that.getImageName()));

    }


    /*********** ABSTRACT METHODS *************/


    public abstract boolean checkOutBook(User borrower);


    public abstract boolean returnBook();




}