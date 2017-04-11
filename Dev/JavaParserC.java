/**
 * Created by rovinpatwal on 4/9/17.
 */
package umlparser.umlparser;

import java.io.*;
import java.util.*;
import java.lang.*;

import com.github.javaparser.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

public class JavaParserC {
    HashMap<String, Boolean> map;
    HashMap<String, String> mapClassConn;
    private static ArrayList<CompilationUnit> cuArray;
    public JavaParserC() {
        System.out.println("Rovin");
        //CompilationUnit compilationUnit = JavaParser.parse("class A { }");
        //ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("A");
    }

    public static void main(String[] args) throws Exception {
        String yumlCode = "";
        JavaParserC JP = new JavaParserC();
        System.out.println("Hello World");
        cuArray = JP.getCuArray("/Volumes/Macintosh HD/SJSU/202/CodeJavaParser/Test1");
        for (CompilationUnit cu : cuArray)
            yumlCode += JP.parser(cu);
        System.out.println(yumlCode);
    }

    private String parser(CompilationUnit cu) {
        String result = "";
        String className = "";
        className = getClassName(cu);
        //classShortName = coi.getName();
        return className;
    }

    private ArrayList<CompilationUnit> getCuArray(String inPath)
            throws Exception {
        File folder = new File(inPath);
        ArrayList<CompilationUnit> cuArray = new ArrayList<CompilationUnit>();
        for (final File f : folder.listFiles()) {
            if (f.isFile() && f.getName().endsWith(".java")) {
                FileInputStream in = new FileInputStream(f);
                CompilationUnit cu;
                try {
                    cu = JavaParser.parse(in);
                    cuArray.add(cu);
                } finally {
                    in.close();
                }
            }
        }
        return cuArray;
    }

    private String getClassName(CompilationUnit cu){
        String strClassName = "";
        List<TypeDeclaration> ltd = cu.getTypes();
        Node node = ltd.get(0); // assuming no nested classes

        // Get className
        ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) node;
        if (coi.isInterface()) {
            strClassName = "[" + "<<interface>>;";
        } else {
            strClassName = "[";
        }
        strClassName += coi.getName();
        return strClassName;
    }
}

