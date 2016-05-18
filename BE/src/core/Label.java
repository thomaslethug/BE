package core;

public class Label {
	
	private Sommets courant;

	public Label(Sommets sommet){
		this.courant=sommet;
	}

	public Sommets getSommet() {
		return courant;
	}

	public void setSommet(Sommets sommet) {
		this.courant = sommet;
	}
	
	public String toString(){
		return "Label du sommet "+courant.toString();
	}


}
