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
		Arete areteChemin;
		float temps=0.0f;
		int longueur=0;
		int vitesse=0;
		
		for(int i=0;i<(tailleListe-1);i++){
			//recherche de l'arrète entre le sommet i et i+1
			//try{
			areteChemin=listeSommets.get(i).recupArete(listeSommets.get(i+1));
			//}
			//catch(Exception )
			longueur=areteChemin.getLongueurArete();
			vitesse=areteChemin.getDescript().vitesseMax();
			temps+=(((float)longueur)/((float)vitesse));
		}
		
		return temps;
	}
	

}
