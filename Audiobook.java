package edu.miracosta.cs112.library;


public class Audiobook extends Library{


    /**************** VARIABLES **************/

    private String length;
    public static final String DEFAULT_LENGTH = "0:00";




    /***************** CONSTRUCTORS ***************/

    public Audiobook(){
        super();
        this.setLength(DEFAULT_LENGTH);
    }

    public Audiobook(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName, String length){
        super(bookID, title, author, genre, available, borrower, imageName);
        this.setLength(length);
    }


    public Audiobook(Audiobook original){
        if (original == null){
            throw new IllegalArgumentException("ERROR: null object passed to copy constructor");
        }
        this.setAll(original.bookID, original.title, original.author, original.genre, original.available, original.borrower, original.imageName, original.length);
    }


    /*************** SETTERS ***************/

    public void setLength(String length){
        this.length = length;
    }

    public void setAll(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName, String length){
        super.setAll(bookID, title, author, genre, available, borrower, imageName);
        this.setLength(length);
    }


    /*************** GETTER **************/

    public String getLength(){
        return this.length;
    }



    /*****************************/

    @Override
    public String toString(){
        String output =  "Audiobook: ID#" + this.bookID + ": \"" + this.title + "\" by " + this.author + ", " + this.genre + ", " + this.length;
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
        if (!(other instanceof Audiobook)){
            return false;
        }
        Audiobook that = (Audiobook)other;
        return (super.equals(that) && this.length.equals(that.length));

    }


    @Override
    public boolean checkOutBook(User borrower){
        if (this.available){
            this.borrower = borrower;
            this.borrower.addBook(this);
            this.available = false;
            System.out.println("Status: Success \n\"" + this.title + "\" has been checked out and is ready to be picked up.");
            return true;
        }
        else {
            System.out.println("Status: ERROR \n\"" + this.title + "\" could not be checked out at this time.");
            return false;
        }
    }


    @Override
    public boolean returnBook(){
        if (this.available || this.borrower == null){
            return false;
        }
        this.available = true;
        this.borrower.removeBook(this);
        return true;
    }


}
