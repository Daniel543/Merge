package Merger;

import com.github.javaparser.ASTHelper;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;


public class MergeVisitor extends VoidVisitorAdapter {

    @Override
    public void visit(MethodDeclaration n, Object arg) {
         /* this method will be called for all methods in this
        / CompilationUnit, including inner class methods*/

        Arguments arguments = (Arguments) arg;

        if (n.getName().equals(arguments.getMethodName())) {
            String nameOfNewMethod;

            //INITIALIZE NEW METHOD
            List<Parameter> oldParameters = (List<Parameter>) cloneObject(n.getParameters());
            MethodDeclaration newMethod = new MethodDeclaration();
            nameOfNewMethod = ((ClassOrInterfaceDeclaration) n.getParentNode()).getName() + "_" + n.getName();
            PackageDeclaration destPackage;
            newMethod.setName(nameOfNewMethod);
            newMethod.setData(n.getData());
            newMethod.setArrayCount(n.getArrayCount());
            newMethod.setBody(n.getBody());
            newMethod.setAnnotations(n.getAnnotations());
            newMethod.setComment(n.getComment());
            newMethod.setJavaDoc(n.getJavaDoc());
            newMethod.setModifiers(n.getModifiers());
            newMethod.setParameters(n.getParameters());
            newMethod.setThrows(n.getThrows());
            newMethod.setType(n.getType());
            newMethod.setTypeParameters(n.getTypeParameters());
            newMethod.setModifiers(ModifierSet.addModifier(newMethod.getModifiers(), ModifierSet.STATIC));
            ClassOrInterfaceDeclaration sourceClass = (ClassOrInterfaceDeclaration) n.getParentNode();
            String parameterIdentifier = "this_" + sourceClass.getName();
            Parameter param;
            if (arguments.getTargetPackageDeclaration() != null) {
                param = ASTHelper.createParameter(ASTHelper.createReferenceType(
                        arguments.getTargetPackageDeclaration().getName() + "." + sourceClass.getName(), 0), parameterIdentifier);
            } else {
                param = ASTHelper.createParameter(ASTHelper.createReferenceType(sourceClass.getName(), 0), parameterIdentifier);
            }
            ASTHelper.addParameter(newMethod, param);

            n.setParameters(oldParameters);
            ThisExpressionVisitor thisExpressionVisitor = new ThisExpressionVisitor();
            thisExpressionVisitor.visit(newMethod, parameterIdentifier); // change any "this" expression
            ASTHelper.addMember(arguments.getTargetClass(), newMethod);

            //CHANGE ORIGINAL METHOD's BODY
            String callOfNewMethod;
            if (arguments.getTargetPackageDeclaration() != null) {
                callOfNewMethod = arguments.getTargetPackageDeclaration().getPackageName() + "." + arguments.getTargetClass().getName()
                        + "." + nameOfNewMethod;
            } else {
                callOfNewMethod = arguments.getTargetClass().getName() + "." + nameOfNewMethod;
            }
            BlockStmt block = new BlockStmt();
            ReturnStmt returnStmt = null;
            n.setBody(block);


            MethodCallExpr call = new MethodCallExpr();
            call.setName(callOfNewMethod);

            for (Parameter parameter : n.getParameters()) {
                String identifier = parameter.getId().getName();
                NameExpr expression = new NameExpr(identifier);
                ASTHelper.addArgument(call, expression);
            }
            NameExpr expression = new NameExpr("this");
            ASTHelper.addArgument(call, expression);


            if (!newMethod.getType().equals(ASTHelper.VOID_TYPE)) {
                returnStmt = new ReturnStmt();
                returnStmt.setExpr(call);
                ASTHelper.addStmt(block, returnStmt);
            } else
                ASTHelper.addStmt(block, call);


        } else
            super.visit(n, arg);

    }

    private Object cloneObject(Object toBeCloned) {
        Object result = null;
        try {
            ExceptionListener listener = new ExceptionListener() {
                public void exceptionThrown(Exception e) {
                    //ignore exceptions
                }
            };


            ByteArrayInputStream bais;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            XMLEncoder encoder = new XMLEncoder(baos);
            encoder.setExceptionListener(listener);
            encoder.writeObject(toBeCloned);
            encoder.close();
            bais = new ByteArrayInputStream(baos.toByteArray());
            XMLDecoder decoder = new XMLDecoder(bais);
            decoder.setExceptionListener(listener);
            result = decoder.readObject();

        } catch (Exception e) {
            //ignore exceptions
        }
        return result;
    }

}