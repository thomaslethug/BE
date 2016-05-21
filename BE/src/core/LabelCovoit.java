package core;

public class LabelCovoit extends Label {

	private Sommets pereV; 
	private Sommets pereP ; 
	private Sommets pereD ; 
	private boolean connexeP ;
	private boolean connexeD ;
	private boolean connexeV ; 
	private boolean marqueP ;
	private boolean marqueD ;
	private boolean marqueV ; 
	private float coutP  ;
	private float coutV ; 
	private float coutD  ;
	
	public LabelCovoit(float cout,Sommets sommet,Sommets pere) {
		super(sommet);
		this.coutP=cout ; 
		this.coutV=cout ;
		this.coutD=cout ; 
		this.pereP=pere ; 
		this.pereV=pere ;
		this.pereD=pere ; 
		
		//init a False 
		this.marqueP=false ; 
		this.marqueV=false ; 
		this.marqueD=false ; 
		this.connexeP=false ; 
		this.connexeV=false ; 
		this.connexeD=false ; 

	}

	

	public Sommets getPereD() {
		return pereD;
	}



	public void setPereD(Sommets pereD) {
		this.pereD = pereD;
	}



	public boolean isConnexeD() {
		return connexeD;
	}



	public void setConnexeD(boolean connexeD) {
		this.connexeD = connexeD;
	}



	public boolean isMarqueD() {
		return marqueD;
	}



	public void setMarqueD(boolean marqueD) {
		this.marqueD = marqueD;
	}



	public float getCoutD() {
		return coutD;
	}



	public void setCoutD(float coutD) {
		this.coutD = coutD;
	}



	public Sommets getPereV() {
		return pereV;
	}

	public void setPereV(Sommets pereV) {
		this.pereV = pereV;
	}

	public Sommets getPereP() {
		return pereP;
	}

	public void setPereP(Sommets pereP) {
		this.pereP = pereP;
	}

	public boolean isConnexeP() {
		return connexeP;
	}

	public void setConnexeP(boolean connexeP) {
		this.connexeP = connexeP;
	}

	public boolean isConnexeV() {
		return connexeV;
	}

	public void setConnexeV(boolean connexeV) {
		this.connexeV = connexeV;
	}

	public boolean isMarqueP() {
		return marqueP;
	}

	public void setMarqueP(boolean marqueP) {
		this.marqueP = marqueP;
	}

	public boolean isMarqueV() {
		return marqueV;
	}

	public void setMarqueV(boolean marqueV) {
		this.marqueV = marqueV;
	}

	public float getCoutP() {
		return coutP;
	}

	public void setCoutP(float coutP) {
		this.coutP = coutP;
	}

	public float getCoutV() {
		return coutV;
	}

	public void setCoutV(float coutV) {
		this.coutV = coutV;
	}
	
	

}
