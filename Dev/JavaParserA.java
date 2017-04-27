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
import java.util.HashSet;

public class JavaParserA {

    private static ArrayList<CompilationUnit> cuArray;
    public JavaParserA() {
        //System.out.println("Rovin");
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
        //System.out.println(inputForYUML);
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
        String classMethodString = "";
        String strClassName = "";
        String classNameString = "";
        String returnDepString = "";
        String returnModString = "";
        String returnClassString = "";
        String returnConsString = "";
        String additions = "";
        String add2 = "";
        HashSet<String> hSetMethodNameB = new HashSet<String>();
        HashSet<String> hSetMethodNameA = new HashSet<String>();
        HashSet<String> hSetMethodNameC = new HashSet<String>();
        HashMap<String,String> fieldNameMap = new HashMap<String,String>();
        HashMap<String,String> fieldModMap = new HashMap<String,String>();
        boolean depFlag = false;
        List<TypeDeclaration> ltd = cu.getTypes();
        Node node = ltd.get(0);

        // Get className
        ClassOrInterfaceDeclaration coi = (ClassOrInterfaceDeclaration) node;
        if (coi.isInterface()) {
            strClassName = "[" + "«interface»;";
            classNameString = "[" + "«interface»;" + coi.getName();
            strClassName += coi.getName();
            //interfaceString = interfaceString + "[" + strClassName;
            interfaceMap.put(coi.getName(), strClassName);

        } else {
            strClassName += coi.getName();
            //if (coi.getExtends() == null) {
            returnClassString = returnClassString + "[" + strClassName + "|";
            classNameString = "[" + strClassName;
            //}
        }


        System.out.println("ClassName --- " + strClassName);
        List<BodyDeclaration> member1s = ((TypeDeclaration) node).getMembers();
        for (int i = 0; i < member1s.size(); i++) {
            String modifier = "";
            String nType = "";
            if (member1s.get(i) instanceof MethodDeclaration) {
                String methodNameString = "";
                System.out.println("Method --- " + ((MethodDeclaration) member1s.get(i)).getName());
                //String mod = ((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6);

                //MethodDeclaration md = ((MethodDeclaration) member1s.get(i));
                //System.out.println(md);
                if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("public")) {
                    //System.out.println("12345");
                    //System.out.println("Get SET " + ((MethodDeclaration) member1s.get(i)).getName().substring(0, 3));
                    //System.out.println("Get SET " + ((MethodDeclaration) member1s.get(i)).getName().substring(3));
                    if (((MethodDeclaration) member1s.get(i)).getName().substring(0, 3).equals("set")
                            || ((MethodDeclaration) member1s.get(i)).getName().substring(0, 3).equals("get")) {
                        String varName = ((MethodDeclaration) member1s.get(i)).getName().substring(3);
                        getterSetterName.add(varName.toLowerCase());
                    } else {
                        int fIndex;
                        int lIndex;
                        int kIndex;
                        String intName = "";
                        String varName = "";
                        fIndex = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().toString().indexOf(", ");
                        //if (fIndex > 0) {
                        //System.out.println("fIndex  --- " + fIndex);
                        String strRem = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().toString().substring(fIndex + 2);
                        System.out.println("strRem  --- " + strRem);
                        lIndex = strRem.indexOf(" ");
                        kIndex = strRem.indexOf(",");
                        if (lIndex > 0 && kIndex > 0) {
                            //intName = strRem.substring(0, lIndex);
                            for(int q = 0; q<(((MethodDeclaration) member1s.get(i))).getParameters().size();q++){
                                varName = (((MethodDeclaration) member1s.get(i))).getParameters().get(q).getId().toString() + " : "+(((MethodDeclaration) member1s.get(i))).getParameters().get(q).getType().toString();
                                intName = (((MethodDeclaration) member1s.get(i))).getParameters().get(q).getType().toString();
                            }
                            methodNameString = "+ " + (((MethodDeclaration) member1s.get(i))).getName() + "(" + varName + ") : " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(0)+";";
                            hSetMethodNameB.add(methodNameString);
                            add2 = add2 + methodNameString;
                            if(hSetMethodNameB.size() > 0) {
                                String hSet = "";
                                for (String s : hSetMethodNameB) {
                                    hSetMethodNameC.add(s);
                                }
                            }
                            System.out.println("intName --- "+intName);
                            if (interfaceMap.containsKey(intName)) {
                                add2 = add2 + "] uses -.->[«interface»;" + intName + "]";
                            }
                        } else {
                            String mod1 = "";
                            String add3 = "";
                            String param = "";
                            String paramStr = "";
                            methodNameString = (((MethodDeclaration) member1s.get(i))).getName();
                            //System.out.println("---------------methodParamName === " + (((MethodDeclaration) member1s.get(i))).getParameters());
                            if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("public")) {
                                mod1 = "+";
                                //param = ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(1).toString();
                                //System.out.println("Param ---" + param);
                                //System.out.println("Param ---" + param.length());
                                for(int q = 0; q<(((MethodDeclaration) member1s.get(i))).getParameters().size();q++){
                                    //System.out.println("--------------------------ID ---"+(((MethodDeclaration) member1s.get(i))).getParameters().get(q).getId());
                                    System.out.println("------------------------- Type 12 ----------------"+(((MethodDeclaration) member1s.get(i))).getParameters().get(q).getType());
                                    paramStr = (((MethodDeclaration) member1s.get(i))).getParameters().get(q).getId().toString() + " : "+(((MethodDeclaration) member1s.get(i))).getParameters().get(q).getType().toString();

                                }

                                hSetMethodNameA.add(mod1+" " + methodNameString + "(" + paramStr + ") : " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(0) + ";");
                                if(hSetMethodNameA.size() > 0) {
                                    String hSet = "";
                                    for (String s : hSetMethodNameA) {
                                        hSetMethodNameC.add(s);
                                    }
                                    add2 = add2 + mod1 + " " + methodNameString + "(" + paramStr + ") : " + ((MethodDeclaration) member1s.get(i)).getChildrenNodes().get(0) + ";";
                                }
                            } else if (((MethodDeclaration) member1s.get(i)).getDeclarationAsString().substring(0, 6).equals("private")) {
                                mod1 = "- ";
                            }
                        }
                        //}
                        for (String key : fieldModMap.keySet()) {
                            System.out.println("key: " + key + " value: " + fieldModMap.get(key));
                            if (getterSetterName.contains(key)) {
                                    int index = returnModString.indexOf(key);
                                    if(index > 0) {
                                        System.out.println(index);
                                        char[] charA = returnModString.toCharArray();
                                        System.out.println(charA[index - 2]);
                                        charA[index - 2] = '+';
                                        String combinedChar = "";
                                        for(int y = 0; y < charA.length;y++){
                                            //System.out.print(charA[y]);
                                            combinedChar = combinedChar + charA[y];
                                        }
                                        returnModString = combinedChar;
                                    }
                            }
                        }

                        String hSet = "";
                        if(hSetMethodNameC.size() > 0) {
                            for (String s : hSetMethodNameC) {
                                hSet = hSet + s;
                            }
                        }
                        classMethodString = classNameString + "|"+ returnModString + "|" + returnConsString +hSet+"],";
                        System.out.println("classMethodString --- "+classMethodString);

                    }
                }
            }
            if (member1s.get(i) instanceof FieldDeclaration) {
                int fieldDeclarationModifiers = ((FieldDeclaration) member1s.get(i)).getModifiers();
                boolean proceed = false;

                List<Node> fieldChildNodes = ((FieldDeclaration) member1s.get(i)).getChildrenNodes();
                String nodeType = ((FieldDeclaration) member1s.get(i)).getType().toString();
                if (nodeType.contains("int") || nodeType.contains("String")) {
                    //System.out.println("getterSetterName --- " + getterSetterName);
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
                    //returnModString = returnModString + modifier + " " + fieldChildNodes.get(1) + " : " + nType + ";";
                } else {
                    String collectionClassName = "";
                    if (nodeType.startsWith("Collection")) {
                        collectionClassName = nodeType.substring(11, nodeType.length() - 1);
                    } else
                        collectionClassName = nodeType;
                    if (!hMapClass.containsKey(collectionClassName)) {
                        hMapClass.put(collectionClassName, nodeType + "|" + strClassName);
                    }
                }

                for (String key : fieldModMap.keySet()) {
                    System.out.println("key: " + key + " value: " + fieldModMap.get(key));
                    if (getterSetterName.contains(key)) {
                        if(! returnModString.contains(key)) {
                            returnModString = returnModString + "+ " + key + fieldNameMap.get(key);
                        }
                    } else {
                        if(! returnModString.contains(key)) {
                            returnModString = returnModString + fieldModMap.get(key) + " " + key + fieldNameMap.get(key);
                        }
                    }
                }

            }
            if (member1s.get(i) instanceof ConstructorDeclaration) {
                //System.out.println("Constructor --- " + member1s.get(i));
                String cType = "";
                String cModifier = "";
                if(((ConstructorDeclaration) member1s.get(i)).getModifiers() == ModifierSet.PRIVATE){
                    cModifier = "-";
                } else if (((ConstructorDeclaration) member1s.get(i)).getModifiers() == ModifierSet.PUBLIC) {
                    cModifier = "+";
                } else if (((ConstructorDeclaration) member1s.get(i)).getModifiers() == ModifierSet.PUBLIC + ModifierSet.STATIC) {
                    cModifier = "+";
                }
                for(int h = 0; h<(((ConstructorDeclaration) member1s.get(i))).getParameters().size();h++){
                    //System.out.println("--------------------------ID ---"+(((MethodDeclaration) member1s.get(i))).getParameters().get(q).getId());
                    System.out.println("Type ======----"+(((ConstructorDeclaration) member1s.get(i))).getParameters().get(h).getType());
                    cType = cType + (((ConstructorDeclaration) member1s.get(i))).getParameters().get(h).getId().toString() + " : "+(((ConstructorDeclaration) member1s.get(i))).getParameters().get(h).getType().toString();
                    //ntName = (((MethodDeclaration) member1s.get(i))).getParameters().get(q).getType().toString();
                }
                returnConsString = returnConsString + cModifier + (((ConstructorDeclaration) member1s.get(i))).getName() + "("  + cType +");";

            }

        }

        if (hMapClass.containsKey(strClassName)) {
            depFlag = true;
            //System.out.println("hmap : " + hMapClass);
            //if(strClassName )
            String strDependency = "";
            String dependentPart = "";
            String className = "";
            String remName = "";
            int classNameIndex = 0;
            strDependency = hMapClass.get(strClassName);
            classNameIndex = strDependency.indexOf("|");
            //System.out.println("Rovin 1234 " + strDependency + "|" + classNameIndex);
            className = strDependency.substring(classNameIndex + 1, strDependency.length());
            remName = strDependency.substring(0, classNameIndex);
            //System.out.println("strDependency ------------ " + strDependency);
            //System.out.println("strClassName1234 ------------ " + strClassName);
            //System.out.println("remName ------------ " + remName);
            if (strClassName != remName) {
                if (strDependency.contains("<"+strClassName+">")) {
                    dependentPart = dependentPart + "-*[" + strClassName + "]";
                } else {
                    dependentPart = dependentPart + "-[" + strClassName + "]";
                }

                if (returnString.contains(",")) {
                    returnDepString = returnDepString + "[" + className + "]" + dependentPart;
                } else {
                    returnDepString = returnDepString + "]" + dependentPart;
                }
            }
            String implText = "";
            if (coi.getImplements() != null) {
                List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi.getImplements();
                for (ClassOrInterfaceType intface : interfaceList) {
                    implText += ",[" + coi.getName() + "] " + "-.-^ " + "["
                            + "«interface»;" + intface + "]";
                    implText += ",";
                }
                //System.out.println("Implements ---- " + additions);
            }
            returnDepString = returnDepString + implText;
        }
        //System.out.println("returnModString -- " + returnModString);
        //int ind = returnModString.indexOf(";");
        String modifiedRetModStr = "";
        //String smallStr = "";



        if (coi.getExtends() != null) {
            additions += "] " + "-^ " + coi.getExtends();
            additions += ",";
            System.out.println("Extends ---- " + additions);
        }


        if(depFlag) {
            returnString = returnString + returnModString +returnDepString + "],";
        } else {
            returnString = returnString + returnClassString + returnModString + returnDepString + "|" + returnConsString + add2;
            if (coi.getImplements() != null) {
                List<ClassOrInterfaceType> interfaceList = (List<ClassOrInterfaceType>) coi
                        .getImplements();
                for (ClassOrInterfaceType intface : interfaceList) {
                    System.out.println("COI Name --- "+coi.getName());
                    System.out.println("COI returnString --- "+returnString);
                    if(returnString.endsWith(",") || returnString.endsWith("|")) {
                        additions += "[" + coi.getName() + "] " + "-.-^ " + "["
                                + "«interface»;" + intface + "]";
                        additions += ",";
                        System.out.println("additions --- " + additions);
                    } else {
                        additions += "] " + "-.-^ " + "["
                                + "«interface»;" + intface + "]";
                        additions += ",";
                        System.out.println("additions --- " + additions);
                    }
                }
                System.out.println("Implements ---- " + additions);
            }
            returnString = returnString + additions + "],";
        }
        returnString = classMethodString + returnString;
        System.out.println("Output : "+returnString + "]");
        System.out.println("additions --- "+additions);
        System.out.println("depFlag --- "+depFlag);
        System.out.println("interfaceMap --- "+interfaceMap);
        return strClassName;
    }

    private String filterName (String name){
        name = name.replace("[]","(*)");
        return name;
    }
}

