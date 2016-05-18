package core;

public class LabelAStar extends LabelPCC{

	private float coutDestination;

	public LabelAStar(float cout, Sommets pere, Sommets sommet,boolean marque, float coutDest){
		super(cout,pere,sommet,marque);
		this.coutDestination=coutDest;	
	}

	
	public float getCoutDestination() {
		return coutDestination;
	}

	public void setCoutDestination(float coutDestination) {
		this.coutDestination = coutDestination;
	}

	public float getCoutEstimation(){
		return this.coutDestination;
	}
	
	
}
