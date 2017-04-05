class UmlGenerator {
    	boolean grammarFlag = true;
	String grammar;
	public static Boolean generatePNG(String grammar, String outPath) {
		//code for generator
		this.grammar = grammar;
		String webLink = "http://yuml.me/diagram/plain/class/" + grammar
                    + ".png";
	}
	public static void main(String[] args) {
        	System.out.println("Uml Gnerator!!!");
    	}
}
