package com.edu.miracosta;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    private static final String ALL_VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:1234567890";
    private static final String INITIAL_VALID_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz_.$:";

    private HashMap<String, Integer> symbolTable;

    /**
     * DESCRIPTION:	initializes hashmap with predefined symbols
     * PRE:	follows symbols/values from book/appendix
     * POST: all hashmap values have valid address integer
     */
    public SymbolTable(){
        symbolTable = new HashMap<String, Integer>();
        //R0-R15
        symbolTable.put("R0", 0);
        symbolTable.put("R1", 1);
        symbolTable.put("R2", 2);
        symbolTable.put("R3", 3);
        symbolTable.put("R4", 4);
        symbolTable.put("R5", 5);
        symbolTable.put("R6", 6);
        symbolTable.put("R7", 7);
        symbolTable.put("R8", 8);
        symbolTable.put("R9", 9);
        symbolTable.put("R10", 10);
        symbolTable.put("R11", 11);
        symbolTable.put("R12", 12);
        symbolTable.put("R13", 13);
        symbolTable.put("R14", 14);
        symbolTable.put("R15", 15);

        symbolTable.put("SCREEN", 16384);
        symbolTable.put("KBD", 24576);

        symbolTable.put("SP", 0);
        symbolTable.put("LCL", 1);
        symbolTable.put("ARG", 2);
        symbolTable.put("THIS", 3);
        symbolTable.put("THAT", 4);
    }

    /**
     * DESCRIPTION:	adds a new pair of symbol/address to hashmap
     * PRE:	symbol/address pair not in hashmap (check contains() 1st)
     * POST: adds pair, returns true if added, false if illegal name
     */
    public boolean addEntry(String symbol, int address){
        if (!contains(symbol)){
            if (isValidName(symbol)) {
                this.symbolTable.put(symbol, address);
                return true;
            }
        }
        return false;
    }

    /**
     * DESCRIPTION:	returns boolean of whether hashmap has symbol or not
     * PRE:	table has been initialized
     * POST: returns boolean if arg is in table or not
     */
    public boolean contains(String symbol){
        return this.symbolTable.containsKey(symbol);
    }

    /**
     * DESCRIPTION:	returns address in hashmap of given symbol
     * PRE:	symbol is in hashmap (check w/ contains() first)
     * POST: returns address associated with symbol in hashmap
     */
    public int getAddress(String symbol){
        if (contains(symbol))
            return symbolTable.get(symbol);
        else return -1;
    }

    /**
     * DESCRIPTION:	checks validity of identifiers for assembly code symbols
     * PRE:	start with letters or “_.$:” only, numbers allowed after
     * POST: returns true if valid identifier, false otherwise
     */
    public boolean isValidName(String symbol){
        char c = symbol.charAt(0);
        if (INITIAL_VALID_CHARS.indexOf(c) != -1){
            for (int i = 0; i < symbol.length(); i++){
                if (ALL_VALID_CHARS.indexOf(symbol.charAt(i)) == -1)
                    return false;
            }
            return true;
        }
        return false;
    }
}
