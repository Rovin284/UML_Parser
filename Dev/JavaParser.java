import java.io.File;
class JavaParser {
    //Reference Code for AST Abstract Syntax Tree
	boolean codeFlag = true;
	final String inputPath;
	final String outputPath;
	public JavaParser(String inputPath, String outputPath){
		this.inputPath = inputPath;
		this.outputPath = inputPath + "\\Output\\" +  outputPath +".png";
	}
	public ASTNode Test{
		CompilationUnit compilationUnit = JavaParser.parse("class A { }");
		ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("A");
    	}
		
	private String getDependency(String code){
                //Code for getting dependency between classes
        }

	private String getMultiplicity(String code){
                //Code for getting multiplicity relationship
        }	
	private String getClassName(String code){
		//Code for getting class name
	}

	private String getMethods(String code){
                //Code for getting Method names
        }	
	private String getAttributes(String code){
                //Code for getting Method names
        }
	private String getInterface(String code){
                //Code for getting Method names
        }	
	public static void main(String[] args) {
        	System.out.println("Java Parser!"); 
    	}
	
}
