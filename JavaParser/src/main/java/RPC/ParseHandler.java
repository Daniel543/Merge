package RPC;

public class ParseHandler {
    public ParseHandler() {

    }


    public String getAst(String filename) {
        return Parser.AstParser.getAst(filename);

    }

    public String getCode(String ast) {

        return Parser.AstParser.getCode(ast);
    }

    public void exit(int code) {
        System.exit(code);
    }

}
