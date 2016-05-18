package core;

public class LabelPCC extends Label implements Comparable<LabelPCC>{
	private float cout;
	private Sommets pere;
	private boolean marque;

	public LabelPCC(float cout,Sommets pere, Sommets sommet , boolean marque) {
		super(sommet);
		this.cout=cout;
		this.pere=pere;
		this.marque=marque;
	}

	@Override
	public int compareTo(LabelPCC o) {

		if(this.getTotalCout() - ((LabelPCC)o).getTotalCout()>0) return 1 ; 
		if(this.getTotalCout() - ((LabelPCC)o).getTotalCout()<0)return -1;
		else{
			if(this.getCoutEstimation()-((LabelPCC)o).getCoutEstimation()>0) return 1;
			if(this.getCoutEstimation()-((LabelPCC)o).getCoutEstimation()<0) return -1;
			else return 0;
		}
	}
	
	public float getCout() {
		return cout;
	}
	
	public float getCoutEstimation(){
		float a=0.0f;
		return a;
	}
	
	public float getTotalCout(){
		return this.getCoutEstimation()+this.getCout();
	}


	public void setCout(float cout) {
		this.cout = cout;
	}

	public Sommets getPere() {
		return pere;
	}

	public void setPere(Sommets pere) {
		this.pere = pere;
	}


	public boolean isMarque() {
		return marque;
	}

	public void setMarque(boolean marque) {
		this.marque = marque;
	}
	
}
