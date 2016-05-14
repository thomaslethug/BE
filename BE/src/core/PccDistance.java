package core;

import java.io.PrintStream;

import base.Readarg;

public class PccDistance extends Pcc {
	
    public PccDistance(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }

    @Override
    protected float initCout(float cout,int distance, float vitesse){
    	return cout+distance ;
    }
    
    @Override
    protected void affichageDebut(){
		System.out.println("Run PCC en distance de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	}
    
    @Override
    protected void affichageFin(Sommets origine, Sommets destination,float cout){
   		System.out.println("PCC en distance de " + ": " + origine.getNum() + " vers " +  ": " + destination.getNum()+" vaut "+cout/1000+" km.") ;
	}
}
