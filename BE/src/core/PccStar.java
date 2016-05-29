package core ;

import java.io.* ;
import base.Readarg ;


public class PccStar extends Pcc {

    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    }
    
    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg,Sommets s1,Sommets s2) {
    	super(gr,sortie,readarg,s1,s2) ; 
    }


    @Override
	protected LabelAStar initLabel(float cout, Sommets pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new LabelAStar(cout,pere,sommet,marque,calculTemps(sommet));
	}


    private float calculTemps(Sommets sommet){
    	double distance= Graphe.distance(sommet.getLongitudes(),sommet.getLatitudes(),destination.getLongitudes(),destination.getLatitudes()); //mètre
    	double vitesse=130.0; //km/h
    	return (float)((60.0*distance)/(1000.0*vitesse));			
    }
    
    
    @Override
    protected void affichageDebut(){
		System.out.println("Run PCCaStar en temps de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	}
    
    @Override
    protected void affichageFin(Sommets origine, Sommets destination,float cout){
   		System.out.println("PCCaStar en temps de " + ": " + origine.getNum() + " vers " +  ": " + destination.getNum()+" vaut "+cout+" minutes soit "+cout/60+" heures.") ;
	}
}

