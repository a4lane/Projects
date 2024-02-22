package com.edu.miracosta;

import java.io.FileWriter;
import java.io.IOException;

public class Assembler {



    /**
     * DESCRIPTION:	converts integer from decimal notation to binary notation
     * PRE:	number is valid size for architecture, non-negative
     * POST: returns 16-bit string of binary digits (first char is MSB)
     */
    private static String decimalToBinary(int number){
        if (number == 0) return "0000000000000000";
        int remainder = 0;
        String reverseBinary = "";
        while (number != 0){
            remainder = number%2;
            number = number/2;
            reverseBinary += remainder;
        }
        while (reverseBinary.length() < 16){
            reverseBinary += "0";
        }
        String binary = "";
        for (int i = 15; i >= 0; i--){
            binary += reverseBinary.charAt(i);
        }
        return binary;
    }

    /**
     * DESCRIPTION: runs through assembly file line by line, adding all labels to
     * the symbol table
     * PRE: symbol table is initialized, assembly file exists
     * POST: all labels have been added to the symbol table
     */
    private static void firstPass(String assemblyFileName, SymbolTable symbolTable){
        Parser parser = new Parser(assemblyFileName);
        while (parser.hasMoreCommands()){
            parser.advance();
            char commandType = parser.getCommandType();
            if (commandType == 'L')
                symbolTable.addEntry(parser.getSymbol(), parser.getLineNumber());
        }
    }




    private void handleError(String message){
        System.out.println(message);
    }

    /**
     * DESCRIPTION: parses through each line of the assembly file. For every A command, looks
     * up symbol in the symbol table and if it does not exists adds it; for every C command,
     * translates each part into binary. Prints the binary translation of every A and C command
     * to an output file.
     * PRE: firstPass has already been called
     * POST: every symbol has been added to the table, the code assembly code is successfully
     * translated and written to outpur file
     */
    private static void secondPass(String assemblyFileName, SymbolTable symbolTable, String binaryFileName) throws IOException {
        CInstructionMapper CIM = new CInstructionMapper();
        FileWriter outFile = null;
        try {
            outFile = new FileWriter(binaryFileName);
            Parser parser = new Parser(assemblyFileName);
            int var = 16;
            while (parser.hasMoreCommands()) {
                parser.advance();
                System.out.println(parser.getCleanLine());
                char commandType = parser.getCommandType();
                String binary="";
                switch (commandType) {
                    case 'A':
                    try {
                        int i = Integer.parseInt(parser.getSymbol());
                        binary = (decimalToBinary(i));
                    }
                    catch (NumberFormatException nfe){
                            int address = symbolTable.getAddress(parser.getSymbol());
                            if (address == -1) {
                                symbolTable.addEntry(parser.getSymbol(), var);
                                address = symbolTable.getAddress(parser.getSymbol());
                                var++;
                            }
                            binary = decimalToBinary(address);
                        }
                        outFile.write(binary + "\n");
                        break;

                    case 'C':
                        binary = "111" + CIM.comp(parser.getComp()) + CIM.dest(parser.getDest()) + CIM.jump(parser.getJump());
                        outFile.write(binary + "\n");


                }
            }
        }
        catch (IOException e){
            System.out.println("Error creating file.");
            e.printStackTrace();
        }
        outFile.close();

    }

    public static void main(String args[]) throws IOException {
        SymbolTable symbolTable = new SymbolTable();
        firstPass("./Rect.asm", symbolTable);
        secondPass("./Rect.asm", symbolTable, "./Rect.hack");


    }
}
