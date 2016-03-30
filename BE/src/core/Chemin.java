package core;

import java.util.ArrayList;
import java.util.Deque;

public class Chemin {
	
	private Sommets PremierSommet;
	private Sommets DernierSommet;
	private int NbSommets;
	private Deque<Sommets> FileSommets; //c'est une file, on accède au 1er ou au dernier
	
	public Chemin(Sommets PremierSommet, Sommets DernierSommet, int NbSommets, ArrayList<Sommets> ListeSommets, String NomCarte){
		this.PremierSommet=PremierSommet;
		this.DernierSommet=DernierSommet;
		this.NbSommets=NbSommets;
		
	}
	

}
