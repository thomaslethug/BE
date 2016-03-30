package core;

import java.util.ArrayList;

public class Chemin {
	
	private int nbSommets;
	private int idCarte;
	private ArrayList<Sommets> listeSommets; //c'est une file, on accède au 1er ou au dernier
	
	public Chemin(int nbSommets, ArrayList<Sommets> listeSommets, int idCarte){
		this.idCarte=idCarte;
		this.listeSommets=listeSommets;
	}
		
	
	public float calculTemps(){
	
		int tailleListe=this.listeSommets.size();
		
		for(int i=0;i<tailleListe;i++){
			//recherche de l'arrète entre le sommet i et i+1
			
		}
	
	}
	

}
