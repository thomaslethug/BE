package core;

import java.awt.Color;
import java.util.ArrayList;

import base.Dessin;

public class Chemin {
	
	private int nbSommets;
	private int idCarte;
	private ArrayList<Sommets> listeSommets; //c'est une file, on accède au 1er ou au dernier
	private Dessin dessin ;
		
	
	
	public Chemin(int nbSommets, ArrayList<Sommets> listeSommets, int idCarte, Dessin dessin){
		this.idCarte=idCarte;
		this.listeSommets=listeSommets;
		this.dessin=dessin ; 
	}
		
	
	
	public float calculTemps(){
	
		int tailleListe=this.listeSommets.size();
		Arete areteChemin;
		float temps=0.0f;
		float longueur=0;
		int vitesse=0;
		
		for(int i=0;i<(tailleListe-1);i++){
			//recherche de l'arrète entre le sommet i et i+1
			try{
				areteChemin=listeSommets.get(i).recupArete(listeSommets.get(i+1));
				longueur=((float) areteChemin.getLongueurArete() )/(1000.0f);
				vitesse=areteChemin.getDescript().vitesseMax();
				temps+=(60.0f*longueur)/((float)vitesse);
			}
			catch(ExceptionBE e){
				System.out.print(e.toString());
			}
		}
		
		return temps;
	}
	
	public void dessinerChemin(Color c) {
		for (int i=0 ; i<(listeSommets.size()-1);i++) {
			dessin.setColor(c) ;
			dessin.setWidth(4);
			dessin.drawLine(listeSommets.get(i).getLongitudes(),listeSommets.get(i).getLatitudes()
					, listeSommets.get(i+1).getLongitudes(), listeSommets.get(i+1).getLatitudes()) ;
		}
	}
	
	

}
