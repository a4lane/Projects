import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;


public class CodeWriter {

    private PrintWriter outputFile;
    private final String LABEL = "LABEL";
    private int numLabels = 0;
    private final HashMap<String, String> segmentSymbols;


    /**
     * DESCRIPTION: full constructor, creates a file to write to
     * passed in by parameter and prepares to write
     * PRE: the file parameter can be created
     * POST: a new file has been created and is ready to be written to
     */
    public CodeWriter(String fileName){
        try {
            outputFile = new PrintWriter(fileName);
        }
        catch (FileNotFoundException fnfe){
            System.out.println("Error: file could not be created.");
        }
        segmentSymbols = new HashMap<>();
        segmentSymbols.put("local", "LCL");
        segmentSymbols.put("argument", "ARG");
        segmentSymbols.put("this", "THIS");
        segmentSymbols.put("that", "THAT");
    }

    public void setFileName(String fileName){

    }


    /**
     * DESCRIPTION: takes in a String parameter in VM language and
     * write its conversion to assembly to the output file
     * PRE: the command is one of the 9 valid arithmetic commands,
     * and is an arithmetic commandType
     * POST: the translated command is written to the assembly file
     */
    public void writeArithmetic(String command){
        switch (command) {
            case ("add") -> writeAdd();
            case ("sub") -> writeSub();
            case ("neg") -> writeNeg();
            case ("eq") -> writeEq();
            case ("gt") -> writeGT();
            case ("lt") -> writeLT();
            case ("and") -> writeAnd();
            case ("or") -> writeOr();
            case ("not") -> writeNot();
            default -> System.out.println("Not a valid arithmetic command");
        }
    }


    /**
     * DESCRIPTION: writes the translation of push/pop commands to output file
     * PRE: the segment and index parameters are valid values
     * POST: the command is translated and written to the file
     */
    public void writePushPop(CommandType commandType, String segment, int index){
        if (commandType == CommandType.C_PUSH){
            switch (segment) {
                case "constant":
                    writePushConstant(index);
                    break;
                case "static":
                    writePushStatic(index);
                    break;
                case "temp":
                    writePushTemp(index);
                    break;
                case "pointer":
                    writePushPointer(index);
                    break;
                default:
                    writePushOther(segment, index);
                    break;
            }
        }
        else {
            switch (segment) {
                case "static":
                    writePopStatic(index);
                    break;
                case "temp":
                    writePopTemp(index);
                    break;
                case "pointer":
                    writePopPointer(index);
                    break;
                default:
                    writePopOther(segment, index);
                    break;
            }

        }
    }

    public void close(){
        this.outputFile.close();
    }


    public void writeInit(){

        
    }


    public void writeEndLoop(){
        outputFile.write("(END)\n");
        outputFile.write("@END\n");
        outputFile.write("0;JMP\n");
    }


    //PUSH/POP WRITERS

    private void writePushConstant(int index){
        outputFile.write("@" + index + "\n");
        outputFile.write("D=A\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("M=M+1\n");
    }

    private void writePushTemp(int index){
        outputFile.write("@" + (index + 5) + "\n");
        outputFile.write("D=M\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("M=M+1\n");
    }

    private void writePushPointer(int index){
        outputFile.write("@" + (index + 3) + "\n");
        outputFile.write("D=M\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("M=M+1\n");
    }

    private void writePushStatic(int index){
        outputFile.write("@" + (index + 16) + "\n");
        outputFile.write("D=M\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("M=M+1\n");
    }

    private void writePushOther(String segment, int index){
        outputFile.write("@" + index + "\n");
        outputFile.write("D=A\n");
        outputFile.write("@" + segmentSymbols.get(segment) + "\n");
        outputFile.write("A=M+D\n");
        outputFile.write("D=M\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("M=M+1\n");
    }

    private void writePopTemp(int index){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("@" + (index + 5) + "\n");
        outputFile.write("M=D\n");
    }


    private void writePopPointer(int index){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("@" + (index + 3) + "\n");
        outputFile.write("M=D\n");
    }

    private void writePopStatic(int index){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("@" + (index + 16) + "\n");
        outputFile.write("M=D\n");
    }

    private void writePopOther(String segment, int index){
        outputFile.write("@" + index + "\n");
        outputFile.write("D=A\n");
        outputFile.write("@" + segmentSymbols.get(segment) + "\n");
        outputFile.write("A=M\n");
        outputFile.write("D=D+A\n");
        outputFile.write("@" + segmentSymbols.get(segment) + "\n");
        outputFile.write("M=D\n");
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("@" + segmentSymbols.get(segment) + "\n");
        outputFile.write("A=M\n");
        outputFile.write("M=D\n");
        outputFile.write("@" + index + "\n");
        outputFile.write("D=A\n");
        outputFile.write("@" + segmentSymbols.get(segment) + "\n");
        outputFile.write("M=M-D\n");
    }




    //ARITHMETIC WRITERS

    private void writeAdd(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("M=M+D\n");
    }

    private void writeSub(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("M=M-D\n");
    }

    private void writeNeg(){
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=-M\n");
    }

    private void writeEq(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("D=D-M\n");
        String l = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l + "\n");
        outputFile.write("D;JEQ\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=0\n");
        String l2 = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l2 + "\n");
        outputFile.write("0;JMP\n");
        outputFile.write("(" + l + ")\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=-1\n");
        outputFile.write("(" + l2 + ")\n");
    }

    private void writeGT(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("D=D-M\n");
        String l = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l + "\n");
        outputFile.write("D;JLT\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=0\n");
        String l2 = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l2 + "\n");
        outputFile.write("0;JMP\n");
        outputFile.write("(" + l + ")\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=-1\n");
        outputFile.write("(" + l2 + ")\n");
    }

    private void writeLT(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("D=D-M\n");
        String l = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l + "\n");
        outputFile.write("D;JGT\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=0\n");
        String l2 = LABEL + this.numLabels;
        this.numLabels++;
        outputFile.write("@" + l2 + "\n");
        outputFile.write("0;JMP\n");
        outputFile.write("(" + l + ")\n");
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=-1\n");
        outputFile.write("(" + l2 + ")\n");
    }


    private void writeAnd(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("M=D&M\n");

    }

    private void writeOr(){
        outputFile.write("@SP\n");
        outputFile.write("AM=M-1\n");
        outputFile.write("D=M\n");
        outputFile.write("A=A-1\n");
        outputFile.write("M=D|M\n");
    }


    private void writeNot(){
        outputFile.write("@SP\n");
        outputFile.write("A=M-1\n");
        outputFile.write("M=!M\n");
    }





}
