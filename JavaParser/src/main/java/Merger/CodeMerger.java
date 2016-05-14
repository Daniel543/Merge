package Merger;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class CodeMerger {


    public static Vector<String> moveMethod(Vector args) {
        Arguments arguments = new Arguments();
        CompilationUnit src = getCompilationUnitFromXml((String) args.get(0)); // src - source AST
        CompilationUnit dst = getCompilationUnitFromXml((String) args.get(1)); // dst - destination AST
        if (src.getData().equals(dst.getData())) {   //checks if compilation units are same
            dst = src;
        } else {
            List<ImportDeclaration> destinationImports = dst.getImports();
            for (ImportDeclaration importDeclaration : src.getImports()) {
                destinationImports.add(importDeclaration);
            }
        }
        String targetClass = (String) args.get(2);
        String methodString = (String) args.get(3);
        methodString = methodString.replace('.', '@');   //there were errors if split was used with "."
        String[] method = methodString.split("@");
        ClassOrInterfaceDeclaration target = (ClassOrInterfaceDeclaration) findClass(targetClass, dst);
        if (target != null) {
            arguments.setTargetClass(target);
        } else {
            ClassOrInterfaceDeclaration newClass = new ClassOrInterfaceDeclaration();
            newClass.setName(targetClass);
            arguments.setTargetClass(newClass);
        }
        arguments.setMethodName(method[1]);
        arguments.setTargetPackageDeclaration(dst.getPackage());
        CompilationUnit whereToLook = (CompilationUnit) findClass(method[0], src).getParentNode();
        new MergeVisitor().visit(whereToLook, arguments);


        if (target == null)
            ASTHelper.addTypeDeclaration(dst, arguments.getTargetClass());
        //
        Vector<String> vector = new Vector<String>();
        ArrayList<String> list = new ArrayList<String>();
        vector.add(compilationUnitToXml(src));
        vector.add(compilationUnitToXml(dst));

        return vector;
    }

    private static TypeDeclaration findClass(String targetClass, CompilationUnit compilationUnit) {

        for (TypeDeclaration typeDeclaration : compilationUnit.getTypes()) {
            if (typeDeclaration.getName().equals(targetClass)) {
                return typeDeclaration;
            }
        }


        return null;
    }

    private static String compilationUnitToXml(CompilationUnit input) {
        ExceptionListener listener = new ExceptionListener() {
            public void exceptionThrown(Exception e) {

            }
        };
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(baos);
        encoder.setExceptionListener(listener);
        encoder.writeObject(input);
        encoder.close();
        return baos.toString();
    }

    private static CompilationUnit getCompilationUnitFromXml(String input) {
        ExceptionListener listener = new ExceptionListener() {
            public void exceptionThrown(Exception e) {

            }
        };

        ByteArrayInputStream bais = new ByteArrayInputStream(input.getBytes());
        XMLDecoder decoder = new XMLDecoder(bais);
        decoder.setExceptionListener(listener);
        CompilationUnit result = (CompilationUnit) decoder.readObject();
        return result;
    }
}
