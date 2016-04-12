package core ;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import base.Readarg;

public class Pcc extends Algo {

    // Sommets origine et destination
    protected int zoneOrigine ;
    protected Sommets origine ;
    protected BinaryHeap<Label> tas;
    protected int zoneDestination ;
    protected Sommets destination ;

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
	super(gr, sortie, readarg) ;
	int s1,s2;

	this.zoneOrigine = gr.getZone () ;
	s1 = readarg.lireInt ("Numero du sommet d'origine ? ") ;
	origine=gr.getSommets(s1);

	// Demander la zone et le sommet destination.
	this.zoneOrigine = gr.getZone () ;
	s2= readarg.lireInt ("Numero du sommet destination ? ");
	destination=gr.getSommets(s2);
	
    }

    public void run() throws ExceptionBE {

	System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	tas= new BinaryHeap<Label>();
	// liste pour r�cuper les sommets du plus court chemin, dans l'ordre
	//ArrayList<Label> ordre_pcc= new ArrayList<Label>();
	Label racine= new Label(0,origine,origine,false );
	tas.insert(racine);
	List<Label> lesMarques = new ArrayList<Label>() ; 
	//on ne sort pas tant que le tas n'est pas vide ou que le sommet destination n'est pas explor�
	while(tas.size()!=0 && racine.getSommet()!=destination){
		racine=(Label)tas.findMin();
		racine.setMarque(true);
		lesMarques.add(racine) ; 
		//ordre_pcc..
		tas.deleteMin();
		for(int i=0;i<racine.getSommet().getNbSuccesseur();i++){
			
			Sommets succ = new Sommets(-1,0,0,0);
			int cout_nouv;
			Label label_succ;
			succ=racine.getSommet().getArete().get(i).getSommetSucc();
			cout_nouv=racine.getSommet().getArete().get(i).getLongueurArete();
			cout_nouv+=racine.getCout() ; 
			
			// r�cup�ration Label si dans tas sinon null
			label_succ=(Label)tas.checkSommet(succ);
			
			
			
			
			//ajout dans tas si non existant
			if(label_succ==null){
				boolean testMarque = false ; 
				//check les sommets marques 
				for (Label lab : lesMarques) {
					if(lab.getSommet().equals(succ)) testMarque=true ; 
				}
				if (!testMarque) {
					label_succ= new Label(cout_nouv, racine.getSommet(),succ,false);
					tas.insert(label_succ);
				}
			
			}
			//modification cout et pere si passage par le sommet dans racine est plus court
			else {
				if(label_succ.getCout()>cout_nouv) {
					label_succ.setCout(cout_nouv);
					label_succ.setPere(racine.getSommet());
					//le tas prend en compte ses modifications
					tas.update(label_succ);
				}
			}
		}
		}
	if (!destination.equals(racine.getSommet())) 
		throw new ExceptionBE("Il n'y a pas de chemin !") ; 
	System.out.println("PCC de " + ":" + origine.getNum() + " vers " +  ":" + destination.getNum()+" vaut "+racine.getCout()) ;
		
	}
 }


