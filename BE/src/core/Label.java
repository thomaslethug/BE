package core;

public class Label {
	private int cout;
	private Sommets pere;
	private Sommets sommet;
	private boolean marque;

	public Label (int cout, Sommets Pere, Sommets sommet,boolean marque){
		this.cout=cout;
		pere=Pere;
		this.sommet=sommet;
		this.marque=marque;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
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
