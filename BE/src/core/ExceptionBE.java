package core;
import java.lang.Exception ;

class ExceptionBE extends Exception {
	

	private static final long serialVersionUID = 1L;
	String motif;
	
	public ExceptionBE(String motif){
		super(motif);
		this.motif=motif;
	}
	
	public String toString(){
		return "\nUne exception a été levé : \n\n"+"       "+this.motif+"\n\n";
	}
	
}
