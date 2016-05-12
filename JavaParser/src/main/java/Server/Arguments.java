package Server;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;


public class Arguments {
    private String methodName;
    private ClassOrInterfaceDeclaration targetClass;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ClassOrInterfaceDeclaration getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(ClassOrInterfaceDeclaration targetClass) {
        this.targetClass = targetClass;
    }

    public Arguments(String methodName, ClassOrInterfaceDeclaration targetClass) {
        this.targetClass = targetClass;
        this.methodName = methodName;

    }

    public Arguments() {

    }
}
