package core;

public class LabelAStar extends Label{

	private float coutDestination;

	public LabelAStar(float cout, Sommets Pere, Sommets sommet,boolean marque, float coutDest){
		super(cout,Pere,sommet,marque);
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
