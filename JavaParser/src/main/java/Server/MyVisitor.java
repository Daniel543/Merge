package Server;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
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
        Arguments arguments = (Arguments) arg;

        if (n.getName().equals(arguments.getMethodName())) {
            System.out.println("Metoda setName:  " + n.getBody());

            //INITIALIZE NEW METHOD
            MethodDeclaration newMethod = new MethodDeclaration();
            String nameOfNewMethod = n.getName() + arguments.getTargetClass().getName();
            newMethod.setName(nameOfNewMethod);
            newMethod.setData(n.getData());
            newMethod.setArrayCount(n.getArrayCount());
            newMethod.setBody(n.getBody());
            newMethod.setAnnotations(n.getAnnotations());
            newMethod.setComment(n.getComment());
            newMethod.setJavaDoc(n.getJavaDoc());
            newMethod.setModifiers(n.getModifiers());
            // newMethod.setNameExpr(n.getNameExpr());
            newMethod.setParameters(n.getParameters());
            newMethod.setThrows(n.getThrows());
            newMethod.setType(n.getType());
            newMethod.setTypeParameters(n.getTypeParameters());
            newMethod.setModifiers(ModifierSet.addModifier(newMethod.getModifiers(), ModifierSet.STATIC));
            ASTHelper.addMember(arguments.getTargetClass(), newMethod);
            //Server.GitHubTest.getCompilationUnit();

            //CHANGE ORIGINAL METHOD's BODY
            BlockStmt block = new BlockStmt();
            n.setBody(block);
            MethodCallExpr call = new MethodCallExpr();
            call.setName(nameOfNewMethod);

            for (Parameter parameter : n.getParameters()) {
                String identifier = parameter.getId().getName();
                NameExpr expression = new NameExpr(identifier);
                ASTHelper.addArgument(call, expression);
            }
            ;
            ASTHelper.addStmt(block, call);




        } else
            super.visit(n, arg);

    }


}