/**
 * Created by rovinpatwal on 4/9/17.
 */

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JavaParserA {
    HashMap<String, Boolean> map;
    HashMap<String, String> mapClassConn;
    ArrayList<String> dependentClass = new ArrayList<String>();
    private static ArrayList<CompilationUnit> cuArray;
    public JavaParserA() {
        System.out.println("Rovin");
        //CompilationUnit compilationUnit = JavaParser.parse("class A { }");
        //ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("A");
    }

    public static void main(String[] args) throws Exception {
        JavaParserA JP = new JavaParserA();
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

    String returnString = "";
    HashMap<String,String> hMapClass = new HashMap<String,String>();
    HashMap<String,String> interfaceMap = new HashMap<String,String>();
    ArrayList<String> getterSetterName = new ArrayList<>();
    private String getClassName(CompilationUnit cu) {

        String strClassName = "";
        String returnDepString = "";
        String returnModString = "";
        String returnClassString = "";
        String additions = "";
        String add2 = "";
        HashMap<String,String> fieldNameMap = new HashMap<String,String>();
        HashMap<String,String> fieldModMap = new HashMap<String,String>();
        boolean depFlag = false;
        ArrayList<String> makeFieldPublic = new ArrayList<String>();
        List<TypeDeclaration> ltd = cu.getTypes();
        Node node = ltd.get(0); // assuming no nested classes

        // Get className
        ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) node;
        if (coi.isInterface()) {
            strClassName = "[" + "<<interface>>;";
            strClassName += coi.getName();
            //interfaceString = interfaceString + "[" + strClassName;
            interfaceMap.put(coi.getName(), strClassName);

        } else {
            //strClassName = "";
            strClassName += coi.getName();
            //if (coi.getExtends() == null) {
            returnClassString = returnClassString + "[" + strClassName + "|";
            //}
        }

        if (coi.getExtends() != null) {
            additions += "] " + "-^ " + coi.getExtends();
            additions += ",";
            System.out.println("Extends ---- " + additions);
        }
        if (coi.getImplements() != null) {
            List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi
                    .getImplements();
            for (ClassOrInterfaceType intface : interfaceList) {
                additions += "[" + coi.getName() + "] " + "-.-^ " + "["
                        + "<<interface>>;" + intface + "]";
                additions += ",";
            }
            System.out.println("adInt ---- " + additions);
        }

        System.out.println("ClassName --- " + strClassName);
        List<BodyDeclaration> member1s = ((TypeDeclaration) node).getMembers();
        for (int i = 0; i < member1s.size(); i++) {
            String modifier = "";
            String nType = "";
            if (member1s.get(i) instanceof MethodDeclaration) {
                String methodNameString = "";
                System.out.println("Method --- " + member1s.get(i));
                String mod = ((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6);

                //MethodDeclaration md = ((MethodDeclaration) member1s.get(i));
                //System.out.println(md);
                if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("public")
                        && !coi.isInterface()) {
                    System.out.println("12345");
                    System.out.println("Get SET " + ((MethodDeclaration) member1s.get(i)).getName().substring(0, 3));
                    System.out.println("Get SET " + ((MethodDeclaration) member1s.get(i)).getName().substring(3));
                    if (((MethodDeclaration) member1s.get(i)).getName().substring(0, 3).equals("set")
                            || ((MethodDeclaration) member1s.get(i)).getName().substring(0, 3).equals("get")) {
                        String varName = ((MethodDeclaration) member1s.get(i)).getName().substring(3);
                        System.out.println("varName " + varName);
                        //makeFieldPublic.add(varName.toLowerCase());
                        getterSetterName.add(varName.toLowerCase());
                        System.out.println("GetterSetterName " + getterSetterName);
                    } else {
                        int fIndex;
                        int lIndex;
                        int kIndex;
                        String intName;
                        String varName;
                        fIndex = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().toString().indexOf(", ");
                        System.out.println("methodNameString ---" + methodNameString);
                        System.out.println("Children node === " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes());
                        if (fIndex > 0) {
                            System.out.println("fIndex  --- " + fIndex);
                            String strRem = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().toString().substring(fIndex + 2);
                            System.out.println("strRem  --- " + strRem);
                            lIndex = strRem.indexOf(" ");
                            kIndex = strRem.indexOf(",");
                            //lIndex = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().;
                            if (lIndex > 0 && kIndex > 0) {
                                intName = strRem.substring(0, lIndex);
                                varName = strRem.substring(lIndex + 1, kIndex);
                                System.out.println("intName === " + intName);
                                System.out.println("varName === " + varName);
                                methodNameString = "+ " + (((MethodDeclaration) member1s.get(i))).getName() + "(" + varName + ":" + intName + ") : " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(0);
                                System.out.println("methodName === " + methodNameString);
                                if (interfaceMap.containsKey(intName)) {
                                    add2 += add2 + methodNameString + "] uses -.->[<<interface>>;" + intName + "]";
                                } else {
                                    add2 += add2 + methodNameString;
                                }
                            } else {
                                String mod1 = "";
                                String add3 = "";
                                String param = "";
                                String paramStr = "";
                                methodNameString = (((MethodDeclaration) member1s.get(i))).getName();
                                if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("public")) {
                                    mod1 = "+";
                                    param = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(1).toString();
                                    System.out.println("Param ---" + param);
                                    System.out.println("Param ---" + param.length());
                                    //Parameter paramCast;
                                    //paramCast = (Parameter) ((MethodDeclaration) member1s.get(i)).getChildrenNodes();
                                    /*if (param.length() <= 3) {
                                        paramStr = "";
                                    } else {
                                        paramStr = param.substring(1, paramStr.length() - 2);  //{STR}
                                    }*/
                                    paramStr = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(1).toString();
                                    add2 = add2 + mod1+" " + methodNameString + "(" + paramStr + ") : " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(0);
                                    System.out.println("add2 =====" + add2);
                                } else if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("private")) {
                                    mod1 = "- ";
                                }
                                System.out.println("methodNameString =====" + methodNameString);
                            }
                        }
                    }
                }
            }
            if (member1s.get(i) instanceof FieldDeclaration) {
                System.out.println("Field --- " + member1s.get(i));
                int fieldDeclarationModifiers = ((FieldDeclaration) member1s.get(i)).getModifiers();
                boolean proceed = false;
                System.out.println("fieldDeclarationModifiers --- " + fieldDeclarationModifiers);

                System.out.println("Type --- " + ((FieldDeclaration) member1s.get(i)).getVariables());
                //returnString = returnString + modifier + " ";
                List<Node> fieldChildNodes = ((FieldDeclaration) member1s.get(i)).getChildrenNodes();
                String nodeType = ((FieldDeclaration) member1s.get(i)).getType().toString();
                System.out.println("nodeType1234 --- " + nodeType);
                if (nodeType.contains("int") || nodeType.contains("String")) {
                    System.out.println("nodeType --- " + nodeType);
                    System.out.println("getterSetterName --- " + getterSetterName);
                    nType = filterName(nodeType);
                    if (fieldDeclarationModifiers == ModifierSet.PRIVATE) {
                        modifier = "-";
                        fieldModMap.put(fieldChildNodes.get(1).toString(), modifier);
                        fieldNameMap.put(fieldChildNodes.get(1).toString(), " : " + nType + ";");
                    } else if (fieldDeclarationModifiers == ModifierSet.PUBLIC) {
                        modifier = "+";
                        fieldModMap.put(fieldChildNodes.get(1).toString(), modifier);
                        fieldNameMap.put(fieldChildNodes.get(1).toString(), " : " + nType + ";");
                    } else if (fieldDeclarationModifiers == ModifierSet.PUBLIC + ModifierSet.STATIC) {
                        modifier = "+";
                        fieldModMap.put(fieldChildNodes.get(1).toString(), modifier);
                        fieldNameMap.put(fieldChildNodes.get(1).toString(), " : " + nType + ";");
                        //returnModString = returnModString + modifier + " " + fieldChildNodes.get(1) + " : " + nType + ";";
                    }
                    System.out.println("modifier " + modifier);
                    //returnModString = returnModString + modifier + " " + fieldChildNodes.get(1) + " : " + nType + ";";
                } else {
                    String collectionClassName = "";
                    System.out.println("Check 1234 " + nodeType);
                    if (nodeType.startsWith("Collection")) {
                        collectionClassName = nodeType.substring(11, nodeType.length() - 1);
                        System.out.println("Key --- " + collectionClassName);
                    } else
                        collectionClassName = nodeType;
                    if (!hMapClass.containsKey(collectionClassName)) {
                        hMapClass.put(collectionClassName, nodeType + "|" + strClassName);
                    }
                }

                //for(int k = 0; k < fieldChildNodes.size(); k++){
                System.out.println("fieldName --- " + fieldChildNodes.get(1));
                //}

            }
            if (member1s.get(i) instanceof ConstructorDeclaration) {
                System.out.println("Constructor --- " + member1s.get(i));
            }

        }

        if (hMapClass.containsKey(strClassName)) {
            depFlag = true;
            System.out.println("hmap : " + hMapClass);
            //if(strClassName )
            String strDependency = "";
            String dependentPart = "";
            String className = "";
            String remName = "";
            int classNameIndex = 0;
            strDependency = hMapClass.get(strClassName);
            classNameIndex = strDependency.indexOf("|");
            System.out.println("Rovin 1234 " + strDependency + "|" + classNameIndex);
            className = strDependency.substring(classNameIndex + 1, strDependency.length());
            remName = strDependency.substring(0, classNameIndex);
            System.out.println("strDependency ------------ " + strDependency);
            System.out.println("strClassName1234 ------------ " + strClassName);
            System.out.println("remName ------------ " + remName);
            if (strClassName != remName) {
                if (strDependency.contains("Collection")) {
                    dependentPart = dependentPart + "-*[" + strClassName + "]";
                } else {
                    dependentPart = dependentPart + "-1[" + strClassName + "]";
                }

                if (returnString.contains(",")) {
                    returnDepString = returnDepString + "[" + className + "]" + dependentPart;
                } else {
                    returnDepString = returnDepString + "]" + dependentPart;
                }
            }
        }
        //System.out.println("returnModString -- " + returnModString);
        //int ind = returnModString.indexOf(";");
        String modifiedRetModStr = "";
        //String smallStr = "";

        for (String key : fieldModMap.keySet()) {
            System.out.println("key: " + key + " value: " + fieldModMap.get(key));
            if (getterSetterName.contains(key)) {
                returnModString = returnModString + "+ " + key + fieldNameMap.get(key);
            } else {
                returnModString = returnModString +fieldModMap.get(key)+" "+ key + fieldNameMap.get(key);
            }
        }

        if(depFlag) {
            returnString = returnString + returnModString +returnDepString + "],";
        } else
        returnString = returnString + returnClassString + returnModString +returnDepString + additions +"|"+add2 + "],";
        System.out.println("Output : "+returnString + "]");

        return strClassName;
    }

    private String filterName (String name){
        name = name.replace("[]","(*)");
        return name;
    }
}

