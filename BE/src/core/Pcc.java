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
		if(ConstantsDebug.doTimeExec) {
			this.zoneOrigine = gr.getZone () ;
			s1 = 119963 ; 
			origine=gr.getSommets(s1);
			this.zoneOrigine = gr.getZone () ;
			s2= 96676 ; 
			destination=gr.getSommets(s2);
		} 
		else {
			this.zoneOrigine = gr.getZone () ;
			s1 = readarg.lireInt ("Numero du sommet d'origine ? ") ;
			origine=gr.getSommets(s1);
			
			// Demander la zone et le sommet destination.
			this.zoneOrigine = gr.getZone () ;
			s2= readarg.lireInt ("Numero du sommet destination ? ");
			destination=gr.getSommets(s2);
		}
	
    } 	
    
     
    public void run() throws ExceptionBE {
    	if(ConstantsDebug.printDebug) affichageDebut();
    	
    	//Création du tas binaire 
    	tas= new BinaryHeap<Label>();
    	
    	//Pour le tracé du PCC
    	int nbSommetsPcc=0;
    	ArrayList<Sommets> listeSommetPcc= new ArrayList<Sommets>();
    	
    	//Tableau contenant tous les labels
    	Label labels[] = new Label[graphe.getTabSommets().length];
    	//initialisation de tous les labels
    	for (int i=0;i<graphe.getTabSommets().length;i++){
    		labels[i]=initLabel(999999999,null,graphe.getSommets(i),false,destination );
    	}
    	
    	//initialisation de la racine 
		Label racine= initLabel(0,origine,origine,false,destination );
    	tas.insert(racine);
    

    	int maxElementTas=1 ;
    	int nbSommetsExplores = 1; 
    	int nbMarques = 0 ; 
    	
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
    	if (!destination.equals(racine.getSommet())) {
    		throw new ExceptionBE("Il n'y a pas de chemin !") ; 
    	}
    	else{
    		
    		//Tracé du pcc
    		Label labelIter=racine;
    		while(!labelIter.getSommet().equals(origine)){

    			listeSommetPcc.add(labelIter.getSommet());
    			nbSommetsPcc++;
    			labelIter=labels[labelIter.getPere().getNum()];

    		}
			listeSommetPcc.add(labelIter.getSommet());
    		Chemin cheminPcc=new Chemin(nbSommetsPcc,listeSommetPcc,graphe.getIdCarte(),graphe.getDessin());
    		cheminPcc.dessinerChemin();

    	}
    	
    	if (ConstantsDebug.printResult) affichageFin( origine, destination,racine.getCout());
    	if (ConstantsDebug.printDebug) perfAlgo(maxElementTas,nbSommetsExplores,nbMarques) ;
    
    }
    
    
    
    public void perfAlgo(int maxElem , int nbSommetsExplores , int nbMarques) {
    	System.out.println("MaxElem : "+maxElem+" , Nb sommets Explores : "+nbSommetsExplores
    			+" Nb marques : "+nbMarques);
    }
    
	protected Label initLabel(float cout, Sommets Pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new Label(cout,Pere,sommet,marque);
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




 


