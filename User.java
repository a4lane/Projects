package edu.miracosta.cs112.library;

/**
 * User.java: represents one user of the library
 * Inner Class: PrivateInfo
 *
 */

import java.text.NumberFormat;

public class User {

    NumberFormat currency = NumberFormat.getCurrencyInstance();



    private class PrivateInfo{
        //PrivateInfo user variables
        private String name;
        private double outstandingFees;

        //PrivateInfo full constructor
        public PrivateInfo(String name, double outstandingFees){
            this.name = name;
            this.outstandingFees = outstandingFees;
        }

        //PrivateInfo default constructor
        public PrivateInfo(){
            this(User.DEFAULT_NAME, User.DEFAULT_OUTSTANDING_FEES);

        }

        @Override
        public String toString(){
            return "Name[" + this.name + "], Outstanding Fees[" + currency.format(this.outstandingFees) + "]";
        }


        @Override
        public boolean equals(Object other){
            if (other == null || this.getClass() != other.getClass()){
                return false;
            }
            PrivateInfo that = (PrivateInfo)other;
            return this.name.equals(that.name) && this.outstandingFees == that.outstandingFees;
        }

    } //END of PrivateInfo inner class




    /*********** CONSTANTS ************/
    public static final int DEFAULT_USER_ID = 0000;
    public static final String DEFAULT_NAME = "John Doe";
    public static final double DEFAULT_OUTSTANDING_FEES = 0.0;


    /******** Instance Variables *********/
    private int userID;
    private PrivateInfo pInfo = new PrivateInfo();
    private Library[] checkedOutBooks;
    private int numCheckedOutBooks;



    /***************** Constructors ******************/

    public User(int userID, String name, double outstandingFees){
        this.setAll(userID, name, outstandingFees);
        this.checkedOutBooks = new Library[10];
        this.numCheckedOutBooks = 0;
    }


    public User(){
        this(DEFAULT_USER_ID, DEFAULT_NAME, DEFAULT_OUTSTANDING_FEES);
        this.checkedOutBooks = new Library[10];
    }


    public User(User original){
        if (original == null){
            throw new IllegalArgumentException("Error: null User object passed to copy constructor.");
        }
        this.setAll(original.userID, original.pInfo.name, original.pInfo.outstandingFees, original.checkedOutBooks, original.numCheckedOutBooks);
    }


    /***************** Getters *****************/

    public int getUserID(){
        return this.userID;
    }

    public String getName(){
        return this.pInfo.name;
    }

    public double getOutstandingFees(){
        return this.pInfo.outstandingFees;
    }

    public Library[] getCheckedOutBooks(){
        return this.checkedOutBooks;
    }

    public int getNumCheckedOutBooks(){
        return this.numCheckedOutBooks;
    }


    /************* Setters *********************/
    public void setUserID(int userID){
        this.userID = userID;
    }

    public void setName(String name){
        this.pInfo.name = name;
    }

    public void setOutstandingFees(double outstandingFees){
        this.pInfo.outstandingFees = outstandingFees;
    }

    public void setCheckedOutBooks(Library[] checkedOutBooks){
        this.checkedOutBooks = checkedOutBooks;
    }

    public void setNumCheckedOutBooks(int numCheckedOutBooks){
        this.numCheckedOutBooks = numCheckedOutBooks;
    }

    public void setAll(int userID, String name, double outstandingFees){
        this.setUserID(userID);
        this.setName(name);
        this.setOutstandingFees(outstandingFees);

    }

    public void setAll(int userID, String name, double outstandingFees, Library[] checkedOutBooks, int numCheckedOutBooks){
        this.setUserID(userID);
        this.setName(name);
        this.setOutstandingFees(outstandingFees);
        this.checkedOutBooks = checkedOutBooks;
        this.numCheckedOutBooks = numCheckedOutBooks;
    }


    /**********/

    @Override
    public String toString(){
        String output = "USER #" + this.userID + ": " + this.pInfo + ", Checked Out Books[";
        for (int i = 0; i < this.numCheckedOutBooks; i++){
            output += "(" + (i + 1) + ") \"" + checkedOutBooks[i].getTitle() + "\", by " + checkedOutBooks[i].getAuthor();
            if(i != numCheckedOutBooks - 1){
                output += "; ";
            }
        }
        output += "]";
        return output;
    }

    @Override
    public boolean equals(Object other){
        if (other == null || this.getClass() != other.getClass()){
            return false;
        }
        User that = (User)other;
        return this.userID == that.userID && this.pInfo.equals(that.pInfo);
    }


    public void addBook(Library book){
        if (this.numCheckedOutBooks == 10){
            throw new NullPointerException("Error: You have reached the maximum number of checked out books. Please return one or more books to check this out.");
        }
        this.checkedOutBooks[this.numCheckedOutBooks++] = book;
    }


    public void removeBook(Library book){
        int index = 0;
        for (int i = 0; i < this.numCheckedOutBooks; i++){
            if (this.checkedOutBooks[i].equals(book)){
                index = i;
                break;
            }
        }
        for (int i = index; i < this.checkedOutBooks.length - 1; i++){
            this.checkedOutBooks[i] = this.checkedOutBooks[i + 1];
        }
        this.numCheckedOutBooks--;
    }


}
