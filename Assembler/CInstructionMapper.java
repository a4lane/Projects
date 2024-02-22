package com.edu.miracosta;

import java.util.HashMap;

public class CInstructionMapper {

    private HashMap<String, String> compCodes;
    private HashMap<String, String> destCodes;
    private HashMap<String, String> jumpCodes;


    /**
     * DESCRIPTION:	initializes hashmaps with binary codes for easy lookup
     * PRE:	comp codes = 7 bits (includes a), dest/jump codes = 3 bits
     * POST: all hashmaps have lookups for valid codes
     */
    public CInstructionMapper(){
        jumpCodes = new HashMap<String, String>();
        jumpCodes.put(null, "000");
        jumpCodes.put("JGT", "001");
        jumpCodes.put("JEQ", "010");
        jumpCodes.put("JGE", "011");
        jumpCodes.put("JLT", "100");
        jumpCodes.put("JNE", "101");
        jumpCodes.put("JLE", "110");
        jumpCodes.put("JMP", "111");

        destCodes = new HashMap<String, String>();
        destCodes.put(null, "000");
        destCodes.put("M", "001");
        destCodes.put("D", "010");
        destCodes.put("MD", "011");
        destCodes.put("A", "100");
        destCodes.put("AM", "101");
        destCodes.put("AD", "110");
        destCodes.put("AMD", "111");

        compCodes = new HashMap<String, String>();
        compCodes.put("0", "0101010");
        compCodes.put("1", "0111111");
        compCodes.put("-1", "0111010");
        compCodes.put("D", "0001100");
        compCodes.put("A", "0110000");
        compCodes.put("!D", "0001101");
        compCodes.put("!A", "0110001");
        compCodes.put("-D", "0001111");
        compCodes.put("-A", "0110011");
        compCodes.put("D+1", "0011111");
        compCodes.put("A+1", "0110111");
        compCodes.put("D-1", "0001110");
        compCodes.put("A-1", "0110010");
        compCodes.put("D+A", "0000010");
        compCodes.put("D-A", "0010011");
        compCodes.put("A-D", "0000111");
        compCodes.put("D&A", "0000000");
        compCodes.put("D|A", "0010101");
        compCodes.put("M", "1110000");
        compCodes.put("!M", "1110001");
        compCodes.put("-M", "1110011");
        compCodes.put("M+1", "1110111");
        compCodes.put("M-1", "1110010");
        compCodes.put("D+M", "1000010");
        compCodes.put("D-M", "1010011");
        compCodes.put("M-D", "1000111");
        compCodes.put("D&M", "1000000");
        compCodes.put("D|M", "1010101");

    }

    /**
     * DESCRIPTION:	converts to string of bits (7) for given mnemonic
     * PRE:	hashmaps are built with valid values
     * POST: returns string of bits if valid, else returns null
     */
    public String comp(String mnemonic){
       if (compCodes.containsKey(mnemonic))
           return compCodes.get(mnemonic);
       else
           return null;
    }

    /**
     * DESCRIPTION:	converts to string of bits (3) for given mnemonic
     * PRE:	hashmaps are built with valid values
     * POST: returns string of bits if valid, else returns null
     */
    public String dest(String mnemonic){
        if (destCodes.containsKey(mnemonic))
            return destCodes.get(mnemonic);
        else
            return null;
    }

    /**
     * DESCRIPTION:	converts to string of bits (3) for given mnemonic
     * PRE:	hashmaps are built with valid values
     * POST: returns string of bits if valid, else returns null
     */
    public String jump(String mnemonic){
         if (jumpCodes.containsKey(mnemonic))
             return jumpCodes.get(mnemonic);
         else
             return null;
    }
}
