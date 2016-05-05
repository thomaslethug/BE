package core;

public class Label implements Comparable<Label>{
	private float cout;
	private Sommets pere;
	private Sommets sommet;
	private boolean marque;

	public Label(float cout, Sommets Pere, Sommets sommet,boolean marque){
		this.cout=cout;
		pere=Pere;
		this.sommet=sommet;
		this.marque=marque;
	}
//32 vers 31, 33
//Sommet num 33 Nb successeurs : 2  329.26123
//Sommet num 31 Nb successeurs : 2  177.00674
	
	@Override
	public int compareTo(Label o) {
		if (sommet.getNum()==33 && o.getSommet().getNum()==31){
			System.out.println(sommet+" cout tot "+this.getTotalCout()+" cout "+this.getCout());
			System.out.println(o.getSommet()+" cout tot "+o.getTotalCout()+" cout "+o.getCout()+"\n");
		}

		if(this.getTotalCout() - o.getTotalCout()>0) return 1 ; 
		if(this.getTotalCout() - o.getTotalCout()<0)return -1;
		else{
			if(this.getCout()-o.getCout()>0) return 1;
			if(this.getCout()-o.getCout()<0) return -1;
			else return 0;
		}
	}
	
	public float getCout() {
		return cout;
	}
	
	public float getTotalCout(){
		return cout;
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

	public Sommets getSommet() {
		return sommet;
	}

	public void setSommet(Sommets sommet) {
		this.sommet = sommet;
	}

	public boolean isMarque() {
		return marque;
	}

	public void setMarque(boolean marque) {
		this.marque = marque;
	}
	
}
