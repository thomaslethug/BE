package core;

import java.io.PrintStream;

import base.Readarg;

public class PccStarDistance extends PccDistance {
	 
	public PccStarDistance(Graphe gr, PrintStream sortie, Readarg readarg) {
		super(gr, sortie, readarg) ;
	}
	
	@Override
	protected LabelPCC initLabel(float cout, Sommets pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new LabelAStar(cout,pere,sommet,marque,(float)graphe.distance(sommet.getLongitudes(),sommet.getLatitudes(),destination.getLongitudes(),destination.getLatitudes()));
    }
	   
	
    @Override
    protected void affichageDebut(){
		System.out.println("Run PCCaStar en distance de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	}
    
    @Override
    protected void affichageFin(Sommets origine, Sommets destination,float cout){
   		System.out.println("PCCaStar en distance de " + ": " + origine.getNum() + " vers " +  ": " + destination.getNum()+" vaut "+cout/1000+" km.") ;
	}
}
