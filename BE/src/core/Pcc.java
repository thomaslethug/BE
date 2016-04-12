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

    public void runDistance() throws ExceptionBE {
 
	System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	tas= new BinaryHeap<Label>();
	// liste pour recuperer les sommets du plus court chemin, dans l'ordre
	
	//Chemin 
	ArrayList<Sommets> listeSommetPcc= new ArrayList<Sommets>();
	int nbSommetsPcc=0;
	//ArrayList<Label> ordre_pcc= new ArrayList<Label>();
	Label racine= new Label(0,origine,origine,false );
	tas.insert(racine);
	Label[] lesMarques = new Label[graphe.getTabSommets().length] ; 
	//on ne sort pas tant que le tas n'est pas vide ou que le sommet destination n'est pas explorï¿½
	while(tas.size()!=0 && racine.getSommet()!=destination){
		racine=(Label)tas.findMin();
		racine.setMarque(true);
		lesMarques[racine.getSommet().getNum()]=racine ; 
		//ordre_pcc..
		tas.deleteMin();
		for(int i=0;i<racine.getSommet().getNbSuccesseur();i++){
			
			Sommets succ = new Sommets(-1,0,0,0);
			int cout_nouv;
			Label label_succ;
			succ=racine.getSommet().getArete().get(i).getSommetSucc();
			cout_nouv=racine.getSommet().getArete().get(i).getLongueurArete();
			cout_nouv+=racine.getCout() ; 
			
			// rï¿½cupï¿½ration Label si dans tas sinon null
			label_succ=(Label)tas.checkSommet(succ);
			
			
			
			
			//ajout dans tas si non existant
			if(label_succ==null){
				//check les sommets marques 
				if(lesMarques[succ.getNum()]==null) {
					label_succ= new Label(cout_nouv, racine.getSommet(),succ,false);
					tas.insert(label_succ);
					//DESSIN DES SOMMETS 
					graphe.getDessin().setColor(java.awt.Color.BLUE) ;
					graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
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
	if (!destination.equals(racine.getSommet())) {
		throw new ExceptionBE("Il n'y a pas de chemin !") ; 
	}
	else{
		Label labelIter=racine;
		while(!labelIter.getSommet().equals(origine)){
			listeSommetPcc.add(labelIter.getSommet());
			nbSommetsPcc++;
			labelIter=lesMarques[labelIter.getPere().getNum()];
		}
		Chemin cheminPcc=new Chemin(nbSommetsPcc,listeSommetPcc,graphe.getIdCarte(),graphe.getDessin());
		cheminPcc.dessinerChemin();
		
	} 
	System.out.println("PCC de " + ":" + origine.getNum() + " vers " +  ":" + destination.getNum()+" vaut "+racine.getCout()) ;
		
	}
 
    	
     
    public void run() throws ExceptionBE {

    	System.out.println("Run PCC de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
    	tas= new BinaryHeap<Label>();
    	//Chemin
    	int nbSommetsPcc=0;
    	ArrayList<Sommets> listeSommetPcc= new ArrayList<Sommets>();
    	Label racine= new Label(0,origine,origine,false );
    	tas.insert(racine);
    	Label[] lesMarques = new Label[graphe.getTabSommets().length];
    	//on ne sort pas tant que le tas n'est pas vide ou que le sommet destination n'est pas explorï¿½
    	while(tas.size()!=0 && racine.getSommet()!=destination){
    		racine=(Label)tas.findMin();
    		racine.setMarque(true);
    		lesMarques[racine.getSommet().getNum()]=racine ; 
    		tas.deleteMin();
    		for(int i=0;i<racine.getSommet().getNbSuccesseur();i++){
    			
    			Sommets succ = new Sommets(-1,0,0,0);
    			float cout_nouv;
    			float vitesse;
    			int distance;
    			Label label_succ;
    			succ=racine.getSommet().getArete().get(i).getSommetSucc();
    			
    			distance=racine.getSommet().getArete().get(i).getLongueurArete();
    			vitesse=(float)racine.getSommet().getArete().get(i).getDescript().vitesseMax();
    			
    			cout_nouv=racine.getCout() + (60.0f*distance)/(1000*vitesse);
    			
    			// récupération Label si dans tas sinon null
    			label_succ=(Label)tas.checkSommet(succ);
    			
    			
    			
    			
    			//ajout dans tas si non existant
    			if(label_succ==null){
    				//check les sommets marques 
    				if(lesMarques[succ.getNum()]==null) {
    					label_succ= new Label(cout_nouv, racine.getSommet(),succ,false);
    					tas.insert(label_succ);
    					//DESSIN DES SOMMETS 
    					graphe.getDessin().setColor(java.awt.Color.BLUE) ;
    					graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
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
    	if (!destination.equals(racine.getSommet())) {
    		throw new ExceptionBE("Il n'y a pas de chemin !") ; 
    	}
    	else{
    		Label labelIter=racine;
    		while(!labelIter.getSommet().equals(origine)){
    			listeSommetPcc.add(labelIter.getSommet());
    			nbSommetsPcc++;
    			labelIter=lesMarques[labelIter.getPere().getNum()];
    		}
    		Chemin cheminPcc=new Chemin(nbSommetsPcc,listeSommetPcc,graphe.getIdCarte(),graphe.getDessin());
    		cheminPcc.dessinerChemin();
    		
    	} 
    	System.out.println("PCC de " + ":" + origine.getNum() + " vers " +  ":" + destination.getNum()+" vaut "+racine.getCout()) ;
    		
    	}
    }




 


