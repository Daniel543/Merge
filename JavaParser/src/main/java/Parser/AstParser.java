package Parser;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Created by Daniel on 4/18/16.
 */
public class AstParser {
    public AstParser() {

    }


    public static CompilationUnit parseFile(String filename) {
        FileInputStream fileInputStream = null;
        CompilationUnit cu = null;

        try {
            fileInputStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            // parse the file
            cu = JavaParser.parse(fileInputStream);
            fileInputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        cu.setData(filename);
        return cu;
    }

    public static String getAst(String filename) {
        ExceptionListener listener = new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                //ignore exceptions
            }
        };

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(baos);
        encoder.setExceptionListener(listener);
        try {
            encoder.writeObject(parseFile(filename));
            encoder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return baos.toString();

    }

    public static String getCode(String ast) {
        ExceptionListener listener = new ExceptionListener() {
            public void exceptionThrown(Exception e) {
                //ignore exceptions
            }
        };
        ByteArrayInputStream bais = new ByteArrayInputStream(ast.getBytes());
        XMLDecoder decoder = new XMLDecoder(bais);
        decoder.setExceptionListener(listener);

        CompilationUnit result = (CompilationUnit) decoder.readObject();
        return result.toString();
    }


}
