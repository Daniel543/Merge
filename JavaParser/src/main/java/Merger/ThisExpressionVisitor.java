package Merger;

import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;


public class ThisExpressionVisitor extends VoidVisitorAdapter {
    @Override
    public void visit(ThisExpr declarator, Object args) // this method changes ThisExpression to NameExpression
    {
        NameExpr nameExpr = new NameExpr();
        nameExpr.setName((String) args);
        FieldAccessExpr fieldAccessExpr;
        fieldAccessExpr = (FieldAccessExpr) declarator.getParentNode();
        fieldAccessExpr.setScope(nameExpr);


    }

}
