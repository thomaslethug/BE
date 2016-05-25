package core ;

import java.io.* ;
import base.Readarg ;
//2392545
//262741
/*
Run PCC de 0: Sommet num 2392545 Nb successeurs : 2 vers 0: Sommet num 262741 Nb successeurs : 2
PCC de :2392545 vers :262741 vaut 408.0556
Temps d'exec : 15822 , MaxElem : 2658 , Nb sommets Explores : 382723 Nb marques : 380070
 * 
 * 
Run PCC de 0: Sommet num 2392545 Nb successeurs : 2 vers 0: Sommet num 262741 Nb successeurs : 2
PCC de :2392545 vers :262741 vaut 462.30945
Temps d'exec : 37367 , MaxElem : 2395 , Nb sommets Explores : 1265486 Nb marques : 1263310
 * 
 */


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

