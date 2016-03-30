package core;

import java.util.Deque;

public class Chemin {
	
	private int nbSommets;
	private int idCarte;
	private Deque<Sommets> fileSommets; //c'est une file, on accède au 1er ou au dernier
	
	public Chemin(int nbSommets, Sommets[] tabSommets, String nomCarte, int idCarte){
		this.idCarte=idCarte;
		for (int i=0;i<nbSommets;i++){
			this.fileSommets.add(tabSommets[i]);
		}		
	}
	
	
	

}
