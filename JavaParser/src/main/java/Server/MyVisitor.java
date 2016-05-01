package Server;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.ModifierVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class MyVisitor extends VoidVisitorAdapter {

    @Override
    public void visit(MethodDeclaration n, Object arg) {
        // here you can access the attributes of the method.
        // this method will be called for all methods in this
        // CompilationUnit, including inner class methods
        if (n.getName().equals(arg)) {
            System.out.println("Metoda setName:  " + n.getBody());
            MethodDeclaration newMethod = new MethodDeclaration();


            //Server.GitHubTest.getCompilationUnit();
        } else
            super.visit(n, arg);
    }


}