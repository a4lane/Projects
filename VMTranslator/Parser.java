import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {

    //instance variables to store parts of the commands
    private String currentLine;
    private String segment;
    private int index;
    private Scanner inputFile;
    private String arithmetic;
    private CommandType commandType;
    private String labelName;
    private String functionName;
    private int nVars;
    private int nArgs;


    /**
     * DESCRIPTION: opens the input file and prepares to
     * parse it
     * PRE: the input file exists
     * POST: Scanner is initialized and ready to parse file
     */
    public Parser(String fileName) {
        try {
            this.inputFile = new Scanner(new FileInputStream(fileName));
        }
        catch (FileNotFoundException fnfe){
            System.out.println("Error: file does not exist.");
        }
    }


    /**
     * DESCRIPTION: checks for more commands in the vm file,
     * returns true or false
     * PRE: Constructor has been called and the file exists
     * POST: returns true if there are more lines and false if not
     */
    public boolean hasMoreCommands(){
        return this.inputFile.hasNextLine();
    }


    /**
     * DESCRIPTION: advances the Scanner to the next line of the vm file
     * and reads the current command, then cleans it up and puts its parts in
     * their appropriate member variables
     * PRE: hasMoreCommands() must be true
     * POST: Scanner is on the next line of the input file
     */
    public void advance(){
        if (hasMoreCommands()) {
            this.currentLine = this.inputFile.nextLine();
            int indexComment = this.currentLine.indexOf("/");
            if (indexComment != -1){
                this.currentLine = this.currentLine.substring(0, indexComment);
            }
            this.currentLine = this.currentLine.trim();
            parseCommandType();
        }
    }


    /**
     * DESCRIPTION: parses the command type of the current line of input file,
     * either pus/pop or arithmetic
     * PRE: advance() has been called and current line is not empty
     * POST: correct command type is stored in instance variable
     */
    private void parseCommandType(){
        //PUSH
        if (this.currentLine.contains("push")){
            this.commandType = CommandType.C_PUSH;
            parseSegmentAndIndex();
        }
        //POP
        else if (this.currentLine.contains("pop")){
            this.commandType = CommandType.C_POP;
            parseSegmentAndIndex();
        }
        //LABEL
        else if (this.currentLine.contains("label")){
            this.commandType = CommandType.C_LABEL;
            parseLabelName();
        }
        //GOTO & IF-GOTO
        else if (this.currentLine.contains("goto")){
            if (this.currentLine.contains("if"))
                this.commandType = CommandType.C_IF;
            else
                this.commandType = CommandType.C_GOTO;
            parseLabelName();
        }
        //FUNCTION
        else if (this.currentLine.contains("function")){
            this.commandType = CommandType.C_FUNCTION;
            parseFunctionHeading();
        }
        //CALL
        else if (this.currentLine.contains("call")) {
            this.commandType = CommandType.C_CALL;
            parseFunctionCall();
        }
        //RETURN
        else if (this.currentLine.equals("return")){
            this.commandType = CommandType.C_RETURN;
        }
        //ARITHMETIC
        else {
            this.commandType = CommandType.C_ARITHMETIC;
            parseArithmetic();
        }
    }


    /**
     * DESCRIPTION: helper method to parse the correct memory
     * segment and index of push/pop commands
     * PRE: command type is push/pop
     * POST: the correct parts of the command are stored in their
     * respective instance variables
     */
    private void parseSegmentAndIndex(){
        String[] parts = this.currentLine.split(" ");
        this.segment = parts[1];
        this.index = Integer.parseInt(parts[2]);
    }

    private void parseLabelName(){
        String[] parts = this.currentLine.split(" ");
        this.labelName = parts[1];
    }

    private void parseFunctionHeading(){
        String[] parts = this.currentLine.split(" ");
        this.functionName = parts[1];
        this.nVars = Integer.parseInt(parts[2]);
    }

    private void parseFunctionCall(){
        String[] parts = this.currentLine.split(" ");
        this.functionName = parts[1];
        this.nArgs = Integer.parseInt(parts[2]);
    }


    /**
     * DESCRIPTION: helper method to parse an arithmetic command
     * PRE: command type is arithmetic
     * POST: arithmetic command is stored in its instance variable
     */
    private void parseArithmetic(){
        this.arithmetic = this.currentLine;
    }



    //getters

    public CommandType getCommandType() {
        return this.commandType;
    }

    public String getSegment(){
        return this.segment;
    }

    public int getIndex(){
        return this.index;
    }

    public String getArithmetic(){
        return this.arithmetic;
    }

    public String getLabelName() { return this.labelName; }

    public String getFunctionName() { return this.functionName; }

    public int getNVars(){ return this.nVars; }

    public int getNArgs() { return this.nArgs; }
}
