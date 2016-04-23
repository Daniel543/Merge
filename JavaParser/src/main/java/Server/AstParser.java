package Server;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created by Daniel on 4/18/16.
 */
public class AstParser {
    public AstParser() {

    }

    FileInputStream in = null;
    CompilationUnit cu = null;
    XMLEncoder encoder = null;
    XMLDecoder decoder = null;
    ByteArrayOutputStream baos;
    ByteArrayInputStream bais = null;

    public boolean parseFile(String filename) {
        try {
            this.in = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            return false;
        }
        try {
            this.cu = JavaParser.parse(in, "UTF-8");
            in.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public String getAst(String filename) {
        parseFile(filename);
        try {
            baos = new ByteArrayOutputStream();
            encoder = new XMLEncoder(baos);
            encoder.writeObject(cu);
            encoder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toString();

    }

    public boolean setAst(String ast) {
        try {
            bais = new ByteArrayInputStream(ast.getBytes());
            decoder = new XMLDecoder(bais);
            CompilationUnit result = (CompilationUnit) decoder.readObject();
            System.out.println(result);

        } catch (Exception e) {

        }
        return true;
    }


}
