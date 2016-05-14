package Merger;

import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;


public class Arguments {
    private String methodName;
    private ClassOrInterfaceDeclaration targetClass;
    private PackageDeclaration targetPackageDeclaration;

    public PackageDeclaration getTargetPackageDeclaration() {
        return targetPackageDeclaration;
    }

    public void setTargetPackageDeclaration(PackageDeclaration targetPackageDeclaration) {
        this.targetPackageDeclaration = targetPackageDeclaration;
    }

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

    public Arguments(String methodName, ClassOrInterfaceDeclaration targetClass, PackageDeclaration targetPackageDeclaration) {
        this.targetClass = targetClass;
        this.methodName = methodName;
        this.targetPackageDeclaration = targetPackageDeclaration;
    }

    public Arguments() {

    }
}
