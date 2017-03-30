class JavaParser {
    //Reference Code for AST Abstract Syntax Tree
	public ASTNode Test{
		CompilationUnit compilationUnit = JavaParser.parse("class A { }");
		ClassOrInterfaceDeclaration classA = compilationUnit.getClassByName("A");
    	}
	
	private String getDependency(String code){
                //Code for getting class name
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
