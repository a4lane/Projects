package com.edu.miracosta;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Parser {

    //constants
    public static final char NO_COMMAND = 'N';
    public static final char A_COMMAND = 'A';
    public static final char C_COMMAND = 'C';
    public static final char L_COMMAND = 'L';

    //file handling
    private Scanner inputFile;
    private int lineNumber;
    private String rawLine;

    //parsed command parts
    private String cleanLine;
    private char commandType;
    private String symbol;
    private String destMnemonic;
    private String compMnemonic;
    private String jumpMnemonic;


    /**
     * DESCRIPTION: opens input file/stream and prepares to parse
     * PRE: provided file is ASM file
     * @param inFileName
     * POST: if file can't be opened, ends program w/ error message
     */
    public Parser(String inFileName){
        try{
            this.inputFile = new Scanner(new FileInputStream(inFileName));
        }
        catch (IOException e){
            System.out.println("Error opening file.");
        }
        this.lineNumber = 0;
    }

    /**
     * DESCRIPTION: returns boolean if more commands left, else closes stream
     * PRE: file stream is open
     * @return true if more commands, else closes stream
     */
    public boolean hasMoreCommands(){
        return this.inputFile.hasNextLine();
    }

    /**
     * DESCRIPTION:	reads next line from file and parses it into instance vars
     * PRE:	file stream is open, called only if hasMoreCommands()
     * POST: current instruction parts put into instance vars
     */
    public void advance(){
        this.rawLine = this.inputFile.nextLine();
        cleanLine();
        parseCommandType();
    }


    /**
     * DESCRIPTION:	cleans raw instruction by removing non-essential parts
     * PRE:	rawLine given (not null)
     * POST: cleanLine set to instruction without comments and whitespace
     */
    private void cleanLine(){
        this.cleanLine = this.rawLine.replaceAll(" ", "").trim().toUpperCase();

        int index = this.cleanLine.indexOf("/");

        if (index != -1)
            this.cleanLine = this.cleanLine.substring(0, index).trim();

    }


    /**
     * DESCRIPTION:	determines command type from cleanLine
     * PRE:	cleanLine is not empty
     * POST: commandType is set to A_COMMAND (A-instruction),
     *    C_COMMAND (C-instruction), L_COMMAND (Label) or
     *    NO_COMMAND (no command)
     */
    private void parseCommandType(){
        if (this.cleanLine.contains("@")) {
            this.commandType = A_COMMAND;
            this.lineNumber++;
        }
        else if (this.cleanLine.contains("=") || this.cleanLine.contains(";")) {
            this.commandType = C_COMMAND;
            this.lineNumber++;
        }
        else if (this.cleanLine.contains("("))
            this.commandType = L_COMMAND;
        else
            this.commandType = NO_COMMAND;
        parse();
    }

    /**
     * DESCRIPTION:	helper method parses line depending on instruction type
     * PRE:	advance() called so cleanLine has value
     * POST: appropriate parts (instance vars) of instruction filled
     */
    private void parse(){
        if (this.commandType == A_COMMAND || this.commandType == L_COMMAND)
            parseSymbol();
        if (this.commandType == C_COMMAND){
            parseDest();
            parseComp();
            parseJump();
        }
    }

    /**
     * DESCRIPTION:	parses symbol for A- or L-commands
     * PRE: advance() called so cleanLine has value,
     * 	call for A- and L-commands only
     * POST: symbol has appropriate value from instruction assigned
     */
    private void parseSymbol(){
        if (this.commandType == A_COMMAND)
            this.symbol = this.cleanLine.substring(1);

        else
            this.symbol = cleanLine.substring(1, this.cleanLine.length()-1);
    }

    /**
     * DESCRIPTION:	helper method parses line to get dest part
     * PRE:	advance() called so cleanLine has value,
     * 	call for C-instructions only
     * POST: destMnemonic set to appropriate value from instruction
     */
    private void parseDest(){
        int index = this.cleanLine.indexOf("=");
        if (index != -1)
            this.destMnemonic = this.cleanLine.substring(0, index);
        else
            this.destMnemonic = null;
    }

    /**
     * DESCRIPTION:	helper method parses line to get comp part
     * PRE:	advance() called so cleanLine has value,
     * 	call for C-instructions only
     * POST: compMnemonic set to appropriate value from instruction
     */
    private void parseComp(){
        int eqIndex = this.cleanLine.indexOf("=");
        int scIndex = this.cleanLine.indexOf(";");
        this.compMnemonic = this.cleanLine;
        if (eqIndex != -1)
            this.compMnemonic = this.compMnemonic.substring(eqIndex + 1);
        if (scIndex != -1)
            this.compMnemonic = this.compMnemonic.substring(0, scIndex);
    }

    /**
     * DESCRIPTION:	helper method parses line to get jump part
     * PRE:	advance() called so cleanLine has value,
     * 	call for C-instructions only
     * POST: jumpMnemonic set to appropriate value from instruction
     */
    private void parseJump(){
        int index = this.cleanLine.indexOf(";");
        if (index != -1)
            this.jumpMnemonic = this.cleanLine.substring(index + 1);
        else
            this.jumpMnemonic = null;
    }


    //getters

    public char getCommandType(){
        return this.commandType;
    }

    public String getSymbol(){
        return this.symbol;
    }

    public String getDest(){
        return this.destMnemonic;
    }

    public String getComp(){
        return this.compMnemonic;
    }

    public String getJump(){
        return this.jumpMnemonic;
    }

    public String getRawLine(){
        return this.rawLine;
    }

    public String getCleanLine(){
        return this.cleanLine;
    }

    public int getLineNumber(){
        return this.lineNumber;
    }
}
