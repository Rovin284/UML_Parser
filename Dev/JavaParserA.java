/**
 * Created by rovinpatwal on 4/9/17.
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaParserA {
    HashMap<String, Boolean> map;
    HashMap<String, String> mapClassConn;
    private static ArrayList<CompilationUnit> cuArray;
    public JavaParserA() {
        System.out.println("Rovin");
        //CompilationUnit compilationUnit = JavaParser.parse("class A { }");
        //ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("A");
    }

    public static void main(String[] args) throws Exception {
        JavaParserA JP = new JavaParserA();
        System.out.println("Hello World");
        String inputForYUML = "";
        cuArray = JP.getFileArray("/Volumes/Macintosh HD/SJSU/202/CodeJavaParser/uml-parser-test-4");
        int counter = 1;
        for (CompilationUnit cu : cuArray) {
            inputForYUML += JP.getYUMLCode(cu);
            //System.out.println("Counter "+counter);
            counter++;
        }
        System.out.println(inputForYUML);
    }

    private String getYUMLCode(CompilationUnit cu) {
        String result = "";
        String className = "";
        className = getClassName(cu);
        //classShortName = coi.getName();
        return className;
    }

    //Done
    private ArrayList<CompilationUnit> getFileArray(String inputPath) throws Exception {
        ArrayList<CompilationUnit> fileArray = new ArrayList<CompilationUnit>();
        File folder = new File(inputPath);
        File[] folderFiles = folder.listFiles();
        for (int i = 0; i < folderFiles.length; i++) {
            if (folderFiles[i].isFile() && folderFiles[i].getName().endsWith(".java")) {
                CompilationUnit cu;
                FileInputStream input = new FileInputStream(folderFiles[i]);
                try {
                    cu = JavaParser.parse(input);
                    fileArray.add(cu);
                } finally {
                    input.close();
                }
            } else if(folderFiles[i].isDirectory()) {
                System.out.println("Not a file");
            }
        }
        return fileArray;
    }

    private String getClassName(CompilationUnit cu) {
        String strClassName = "";
        ArrayList<String> makeFieldPublic = new ArrayList<String>();
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

        List<BodyDeclaration> member1s = ((TypeDeclaration) node).getMembers();
        for(int i = 0; i < member1s.size(); i++) {
            String modifier = "";
            if(member1s.get(i) instanceof FieldDeclaration) {
                System.out.println("Field --- "+member1s.get(i));
                int fieldDeclarationModifiers = ((FieldDeclaration) member1s.get(i)).getModifiers();
                boolean proceed = false;
                System.out.println("fieldDeclarationModifiers --- "+fieldDeclarationModifiers);
                if(fieldDeclarationModifiers == ModifierSet.PRIVATE) {
                    modifier = "-";
                } else if (fieldDeclarationModifiers == ModifierSet.PUBLIC){
                    modifier = "+";
                } else if(fieldDeclarationModifiers == ModifierSet.PUBLIC+ModifierSet.STATIC) {
                    modifier = "+";
                }
                System.out.println("modifier "+modifier);
                List<Node> fieldChildNodes = ((FieldDeclaration) member1s.get(i)).getChildrenNodes();
                String nodeType = ((FieldDeclaration) member1s.get(i)).getType().toString();
                System.out.println("nodeType --- "+nodeType);
                for(int k = 0; k < fieldChildNodes.size(); k++){
                    System.out.println("Rovin123 --- "+fieldChildNodes.get(i));
                }
            }
            if(member1s.get(i) instanceof ConstructorDeclaration) {
                System.out.println("Constructor --- " + member1s.get(i));
            }
            if (member1s.get(i) instanceof MethodDeclaration) {
                System.out.println("Method --- " + member1s.get(i));
                //MethodDeclaration md = ((MethodDeclaration) member1s.get(i));
                //System.out.println(md);
                if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0,4) == "public"
                        && !coi.isInterface()) {
                    System.out.println("12345");
                    if (((MethodDeclaration) member1s.get(i)).getName().substring(0,2) == "set"
                            || ((MethodDeclaration) member1s.get(i)).getName().substring(0,2) == "get") {
                        String varName = ((MethodDeclaration) member1s.get(i)).getName().substring(3);
                        makeFieldPublic.add(varName.toLowerCase());
                    }
                }
            }
        }
        return strClassName;
    }
}

