package Server;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.type.Type;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Daniel on 4/18/16.
 */
public class AstParser {
    public AstParser() {

    }

    FileInputStream in = null;
    CompilationUnit cu = null;
    List<CompilationUnit> compilationUnits = null;
    XMLEncoder encoder = null;
    XMLDecoder decoder = null;
    ByteArrayOutputStream baos;
    ByteArrayInputStream bais = null;
    Vector filenames = null;

    public boolean parseFiles(Vector filenames) {
        this.filenames = filenames;
        compilationUnits = new ArrayList<CompilationUnit>();
        for (Object file : filenames) {
            System.out.println("TOTO MA BYT PRVE" + file);
            String filename = (String) file;
            try {
                this.in = new FileInputStream(filename);
            } catch (FileNotFoundException e) {
                return false;
            }
            try {
                this.cu = JavaParser.parse(in, "UTF-8");
                in.close();
                compilationUnits.add(this.cu);
            } catch (Exception e) {
                return false;
            }
        }
        return true;


    }

    public String getAst(String filename) {         // This method is not needed

        try {
            baos = new ByteArrayOutputStream();
            encoder = new XMLEncoder(baos);
            encoder.writeObject(cu);
            encoder.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // System.out.println(baos);
        return baos.toString();

    }

    public boolean setAst(String ast) {             // This method is not needed
        try {
            bais = new ByteArrayInputStream(ast.getBytes());
            decoder = new XMLDecoder(bais);
            CompilationUnit result = (CompilationUnit) decoder.readObject();
            System.out.println(result);

        } catch (Exception e) {

        }
        return true;
    }

    public boolean moveMethod(Vector args) {
        Arguments arguments = new Arguments();
        String targetClass = (String) args.get(0);
        String methodString = (String) args.get(1);
        methodString = methodString.replace('.', '@');   //there were errors if split was used with "."
        String[] method = methodString.split("@");
        ClassOrInterfaceDeclaration target = (ClassOrInterfaceDeclaration) findClass(targetClass);
        if (target != null) {
            arguments.setTargetClass(target);
        } else {
            ClassOrInterfaceDeclaration newClass = new ClassOrInterfaceDeclaration();
            newClass.setName(targetClass);
            arguments.setTargetClass(newClass);
        }
        arguments.setMethodName(method[1]);
        CompilationUnit whereToLook = (CompilationUnit) findClass(method[0]).getParentNode();
        new MyVisitor().visit(whereToLook, arguments);
        //   System.out.println(newClass);
        if (target == null)
            ASTHelper.addTypeDeclaration(whereToLook, arguments.getTargetClass());

        return true;
    }

    public boolean writeFiles() {
        //TODO
        return true;
    }

    public void exit() {
        System.exit(0);
    }

    private TypeDeclaration findClass(String targetClass) {
        for (CompilationUnit compilationUnit : compilationUnits) {
            for (TypeDeclaration typeDeclaration : compilationUnit.getTypes()) {
                if (typeDeclaration.getName().equals(targetClass)) {
                    return typeDeclaration;
                }
            }

        }
        return null;
    }

}
