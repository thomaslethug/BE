package core ;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;

import base.Readarg;

public class Pcc extends Algo {

    // Sommets origine et destination
    protected int zoneOrigine ;
    protected Sommets origine ;
    protected BinaryHeap<LabelPCC> tas;
    protected int zoneDestination ;
    protected Sommets destination ;

    public Pcc(Graphe gr, PrintStream sortie, Readarg readarg) {
		super(gr, sortie, readarg) ;
		int s1,s2;
		this.zoneOrigine = gr.getZone () ;
		s1 = readarg.lireInt ("Numero du sommet d'origine ? ") ;
		origine=gr.getSommets(s1);
		
		// Demander la zone et le sommet destination.
		this.zoneDestination = gr.getZone () ;
		s2= readarg.lireInt ("Numero du sommet destination ? ");
		destination=gr.getSommets(s2);

	
    } 	
    
    public Pcc(Graphe gr, PrintStream sortie,Readarg readarg,Sommets origine, Sommets dest) {
    	super(gr,sortie,readarg) ; 
    	this.zoneOrigine=gr.getZone() ; 
    	this.origine=origine ; 
    	this.destination=dest ; 
    }
    
    
    public void run() throws ExceptionBE  {
    	if(ConstantsDebug.doTimeExec==1) {
    		for (int i =0; i<5 ; i++) {
    			System.gc();
    			long t1 = System.currentTimeMillis() ; 
        		this.launchPCC();
        		long t2 = System.currentTimeMillis() ; 
        		System.out.println("Temps d'execution : "+(t2-t1)+" ms") ;
    		}
    		System.gc();
    		long t1 = System.currentTimeMillis() ; 
    		this.launchPCC(); 
    		long t2 = System.currentTimeMillis() ; 
    		System.out.println("Temps d'execution : "+(t2-t1)+" ms") ;
    	} else {
    		this.launchPCC();
    	}
    
    }
    
    public void launchPCC() throws ExceptionBE{
    	if(ConstantsDebug.printDebug==1) affichageDebut();

    	//Création du tas binaire 
    	tas= new BinaryHeap<LabelPCC>();
    	
    	//Pour le tracé du PCC
    	int nbSommetsPcc=0;
    	ArrayList<Sommets> listeSommetPcc= new ArrayList<Sommets>();
    	
    	long tinit1=System.currentTimeMillis() ; 
    	//Tableau contenant tous les labels
    	LabelPCC labels[] = new LabelPCC[graphe.getTabSommets().length];
    	//initialisation de tous les labels
    	for (int i=0;i<graphe.getTabSommets().length;i++){
    		labels[i]=initLabel(999999999,null,graphe.getSommets(i),false,destination);
    	}
    	long tinit2=System.currentTimeMillis() ; 
    	System.out.println("Temps de l'init : "+(tinit2-tinit1));
    	
    	
    	//initialisation de la racine 
    	LabelPCC racine= initLabel(0,origine,origine,false,destination);
    	tas.insert(racine);
    

    	int maxElementTas=1 ;
    	int nbSommetsExplores = 1; 
    	int nbMarques = 0 ; 
    	long twhile1 = System.currentTimeMillis() ; 
    	//on ne sort pas tant que le tas n'est pas vide ou que le sommet destination n'est pas explor�
    	while(tas.size()!=0 && racine.getSommet()!=destination){

    		racine=tas.findMin();
    		racine.setMarque(true);
        	labels[racine.getSommet().getNum()]=racine;
    		tas.deleteMin();
    		nbMarques++ ;
    		
    		//Pour tous les successeurs du sommet actuel 
    		for(int i=0;i<racine.getSommet().getNbSuccesseur();i++){

    			Sommets succ ;
    			float cout_nouv;
    			float vitesse;
    			int distance;
    			succ=racine.getSommet().getArete().get(i).getSommetSucc();    			
    			distance=racine.getSommet().getArete().get(i).getLongueurArete(); //en metre 
    			vitesse=(float)racine.getSommet().getArete().get(i).getDescript().vitesseMax();
    			cout_nouv=initCout(racine.getCout(),distance,vitesse);
    			
    			//si le sommet successeur est marqué on fait rien car déja traité 
    			if(!labels[succ.getNum()].isMarque()){

    				//si le sommet successeur n'est pas dans le tas
    				if(!tas.contains(labels[succ.getNum()])){

    					labels[succ.getNum()].setCout(cout_nouv);
    					labels[succ.getNum()].setPere(racine.getSommet());
	    				tas.insert(labels[succ.getNum()]);
	    				nbSommetsExplores++ ; 
	    				
	    				if(maxElementTas<tas.size()) maxElementTas=tas.size() ; 
	    				//DESSIN DES SOMMETS 
	    				graphe.getDessin().setColor(java.awt.Color.RED) ;
	    				graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
	    					
    				}
    				
    				else{   	//si le sommet succésseur est dans le tas on update peut être

    					if(labels[succ.getNum()].getCout()>cout_nouv) {
    		
    						labels[succ.getNum()].setCout(cout_nouv);
    						labels[succ.getNum()].setPere(racine.getSommet());
    						//le tas prend en compte ses modifications
    						tas.update(labels[succ.getNum()]);

    					}
    				}
    			}
    		}
    	}
    	long twhile2 = System.currentTimeMillis() ;
    	System.out.println("Temps du while : "+(twhile2-twhile1));
    	if (!destination.equals(racine.getSommet())) {
    		throw new ExceptionBE("Il n'y a pas de chemin !") ; 
    	}
    	else{
    		
    		//Tracé du pcc
    		LabelPCC labelIter=racine;
    		while(!labelIter.getSommet().equals(origine)){

    			listeSommetPcc.add(labelIter.getSommet());
    			nbSommetsPcc++;
    			labelIter=labels[labelIter.getPere().getNum()];

    		}
			listeSommetPcc.add(labelIter.getSommet());
    		Chemin cheminPcc=new Chemin(nbSommetsPcc,listeSommetPcc,graphe.getIdCarte(),graphe.getDessin());
    		cheminPcc.dessinerChemin(Color.BLUE);

    	}
    	
    	if (ConstantsDebug.printResult==1) affichageFin( origine, destination,racine.getCout());
    	if (ConstantsDebug.printDebug==1) perfAlgo(maxElementTas,nbSommetsExplores,nbMarques) ;
    }
    
    public void perfAlgo(int maxElem , int nbSommetsExplores , int nbMarques) {
    	System.out.println("MaxElem : "+maxElem+" , Nb sommets Explores : "+nbSommetsExplores
    			+" Nb marques : "+nbMarques);
    }
    
	protected LabelPCC initLabel(float cout, Sommets Pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new LabelPCC(cout,Pere,sommet,marque);
    }
    
    
    protected float initCout(float cout,int distance, float vitesse){
    	return cout + (60.0f*distance)/(1000*vitesse);
    }
    
   protected void affichageDebut(){
		System.out.println("Run PCC en temps de " + zoneOrigine + ":" + origine + " vers " + zoneDestination + ":" + destination) ;
	}
   
   protected void affichageFin(Sommets origine, Sommets destination,float cout){
   		System.out.println("PCC en temps de " + ": " + origine.getNum() + " vers " +  ": " + destination.getNum()+" vaut "+cout+" minutes soit "+cout/60+" heures.") ;
	}
}




 


