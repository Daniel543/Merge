package Server;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GitHubTest {

    public static void main(String... args) throws Exception {
        /*file1:
        class D {

    public int foo(int e) {
        int a = 20,
        return  100;
    }
    */
        FileInputStream file1 = null;
        try {
            file1 = new FileInputStream("/root/vstup.java");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CompilationUnit cu = getCompilationUnit(file1);
        /*Node method = new Node() {
            @Override
            public <R, A> R accept(GenericVisitor<R, A> genericVisitor, A a) {
                return null;
            }

            @Override
            public <A> void accept(VoidVisitor<A> voidVisitor, A a) {

            }
        };*/

        //List<Node> childs = new ArrayList<Node>();
       /* for (Node node : cu.getTypes()) {
            if (node instanceof ClassOrInterfaceDeclaration){
                System.out.println(((ClassOrInterfaceDeclaration) node).getName());
                if (((ClassOrInterfaceDeclaration) node).getName().equals("Povar")){
                    ((ClassOrInterfaceDeclaration) node).setMembers(null);
                }
            }
        }*/
        ClassOrInterfaceDeclaration newClass = new ClassOrInterfaceDeclaration();
        newClass.setName("SunkaSyr");
        Arguments arguments = new Arguments("setMeno", newClass);
        new MyVisitor().visit(cu, arguments);
        System.out.println(newClass);
        ASTHelper.addTypeDeclaration(cu, newClass);

        // cu.contains(Node method);
        String result = cu.toString();
        // new MyVisitor().visit(cu, null);
        System.out.println(cu);

    }

    public static CompilationUnit getCompilationUnit(InputStream in) {
        try {
            CompilationUnit cu;
            try {
                // parse the file
                cu = JavaParser.parse(in);
                return cu;
            } finally {
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}

