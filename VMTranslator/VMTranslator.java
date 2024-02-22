public class VMTranslator {



    public static void main(String[] args) {
        Parser parser = new Parser("./StaticTest.vm");
        CodeWriter codeWriter = new CodeWriter("./StaticTest.asm");

        while (parser.hasMoreCommands()){
            parser.advance();
            if (parser.getCommandType() == CommandType.C_ARITHMETIC){
                codeWriter.writeArithmetic(parser.getArithmetic());
            }
            else {
                codeWriter.writePushPop(parser.getCommandType(), parser.getSegment(), parser.getIndex());
            }
        }
        codeWriter.writeEndLoop();
        codeWriter.close();
    }
}
