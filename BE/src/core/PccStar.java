package core ;

import java.io.* ;
import base.Readarg ;

public class PccStar extends Pcc {

    public PccStar(Graphe gr, PrintStream sortie, Readarg readarg) {
	super(gr, sortie, readarg) ;
    }


    @Override
	protected Label initLabel(float cout, Sommets pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new LabelAStar(cout,pere,sommet,marque,(float)graphe.distance(sommet.getLongitudes(),sommet.getLatitudes(),destination.getLongitudes(),destination.getLatitudes()));
	}


}
