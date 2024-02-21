package edu.miracosta.cs112.library;


public class Paperback extends Library{


    /************ INSTANCE VARIABLE ************/
    private int numPages;

    /************ DEFAULT CONSTANTS ************/
    public static final int DEFAULT_NUM_PAGES = 0;


    /************** CONSTRUCTORS ***************/

    public Paperback(){
        super();
        this.setNumPages(DEFAULT_NUM_PAGES);
    }

    public Paperback(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName, int numPages){
        super(bookID, title, author, genre, available, borrower, imageName);
        this.setNumPages(numPages);
    }


    public Paperback(Paperback original){
        if (original == null){
            throw new IllegalArgumentException("ERROR: null object passed to copy constructor");
        }
        this.setAll(original.bookID, original.title, original.author, original.genre, original.available, original.borrower, original.imageName, original.numPages);
    }



    /************ SETTERS **************/

    public void setNumPages(int numPages){
        this.numPages = numPages;
    }


    public void setAll(int bookID, String title, String author, String genre, boolean available, User borrower, String imageName, int numPages){
        super.setAll(bookID, title, author, genre, available, borrower, imageName);
        this.numPages = numPages;
    }



    /************** GETTER *************/

    public int getNumPages(){
        return this.numPages;
    }




    /********************/



    @Override
    public String toString(){
        String output =  "Paperback: ID#" + this.bookID + ": \"" + this.title + "\" by " + this.author + ", " + this.genre + ", " + this.numPages + " pages";
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
        if (!(other instanceof Paperback)){
            return false;
        }
        Paperback that = (Paperback)other;
        return (super.equals(that) && this.numPages == that.numPages);

    }






    @Override
    public boolean checkOutBook(User borrower){
        if (this.available){
            this.borrower = borrower;
            this.borrower.addBook(this);
            this.available = false;
            System.out.println("Status: Success! \n\"" + this.title + "\" has been checked out and is ready to be downloaded to your device.");
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