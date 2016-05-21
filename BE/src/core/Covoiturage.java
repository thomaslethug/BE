package core ;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;

import base.Readarg;
//h�rite de Pcc pour pouvoir utiliser certaines m�thodes, le PCC lui meme ( Pcc.run())n'est pas utilis�.
public class Covoiturage extends Algo{
	private Sommets pietonOrgn;
	private Sommets voitureOrgn;
	private Sommets dest ; 
	
	
    public 	Covoiturage(Graphe gr, PrintStream sortie, Readarg readarg) {
    	super(gr, sortie, readarg) ;
    	int s1,s2,s3;
    	gr.graphInv() ; 
    	
    	s1 = readarg.lireInt ("point de d�part Pi�ton?") ;
    	pietonOrgn=gr.getSommets(s1);

    	// Demander la zone et le sommet destination.
    	
    	s2= readarg.lireInt ("Point de d�part conducteur ? ");
    	voitureOrgn=gr.getSommets(s2);
    	
    	s3=readarg.lireInt("Point de destination ? ") ; 
    	dest=gr.getSommets(s3) ; 
    }
    //changements: prend en compte mise � jour cout de la route minimum entre les deux d�part, et donc des marques et couts s�par�s
    public float rajoutVoisins(BinaryHeap<LabelPCC> tas, LabelCovoit racine, LabelCovoit[] labels, 
    							LabelPCC[] labels_tasP, LabelPCC[] labels_tasV,LabelPCC[] labels_tasD , float minRoute, Type t,Sommets rdv){
    	
		//Passe par tous les successeurs du sommet derni�rement sortie du tas.  
		for(int i=0;i<racine.getSommet().getNbSuccesseur();i++){
			
			Arete a = racine.getSommet().getArete().get(i) ;   //recup d'une des aretes qui succède à la racine 
			
			Sommets succ=a.getSommetSucc();					   //recup du sommet successeur de cette racine 
			float vitesse=(float)a.getDescript().vitesseMax(); //recup de la vitesse sur cette arete 
			int distance=a.getLongueurArete(); 				   //recup de la distance de cette arete (en metre)  
			float cout_nouv;			
			
			//dans le cas où le cout était plus petit du coté du piéton
			if(Type.pieton==t){
				
				
				//si la vitesse de cette arete est inférieure à 110.0f (c'est à dire que le piéton peut y accéder) 
				if(vitesse<110.0f){
					
					cout_nouv=initCout(racine.getCoutP(),distance,vitesse,true); //calcul du nouveau cout
					
					//si le sommet successeur est marqu� on fait rien car d�ja trait�
					if(labels[succ.getNum()].isMarqueP()==false){
						
						//si le sommet successeur n'est pas dans le tas, on l'ajoute
						if(tas.contains(labels_tasP[succ.getNum()])==false){
							
							labels[succ.getNum()].setCoutP(cout_nouv);
							labels[succ.getNum()].setPereP(racine.getSommet());
							labels[succ.getNum()].setConnexeP(true);
							labels_tasP[succ.getNum()].setCout(cout_nouv);
							labels_tasP[succ.getNum()].setPere(racine.getSommet());
							//System.out.println("Ajout dans PCC pieton");
							//System.out.println("cout pieton"+labels[succ.getNum()].getCoutP()+"cout voiture"+labels[succ.getNum()].getCoutV()+"connexit� V"+labels[succ.getNum()].isConnexeV());
							tas.insert(labels_tasP[succ.getNum()]);		//on ajoutte le label dans le tas de P 
							// mise � jour de la meilleur route � prendre pour les deux covoitureurs
							if(labels[succ.getNum()].isConnexeV() && labels[succ.getNum()].isConnexeD() && 
								minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD()))  {
								
								minRoute=(labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD());
								rdv.setNum(succ.getNum());
							}
							
							
							//DESSIN DES SOMMETS 
							graphe.getDessin().setColor(java.awt.Color.GREEN) ;
							graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
							
						}
						else{   	//si le sommet successeur est dans le tas on update si le nouveau cout est plus faible
							
							if(labels[succ.getNum()].getCoutP()>cout_nouv) {
								
								labels[succ.getNum()].setCoutP(cout_nouv);
								labels[succ.getNum()].setPereP(racine.getSommet());
								//le tas prend en compte ses modifications
								labels_tasP[succ.getNum()].setCout(cout_nouv);
								labels_tasP[succ.getNum()].setPere(racine.getSommet());
								tas.update(labels_tasP[succ.getNum()]);
								
								// System.out.println("Pieton : cout pieton"+labels[succ.getNum()].getCoutP()+"cout voiture"+labels[succ.getNum()].getCoutV());
								//chgt meilleur route si n�cessaire
								if(labels[succ.getNum()].isConnexeV() && labels[succ.getNum()].isConnexeD() && 
										minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD()))  {
										
										minRoute=(labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD());
										rdv.setNum(succ.getNum());
								}
							}
						}
					}
				}
			}
			// pour le conducteur, les labels sont mis a jour diff�remment du pi�ton, d'o� le if (pi�ton) ci dessus
			else if(Type.voiture==t){
				cout_nouv=initCout(racine.getCoutV(),distance,vitesse,false);
				if(labels[succ.getNum()].isMarqueV()==false){
					
					//si le sommet successeur n'est pas dans le tas, on l'ajoute
					if(tas.contains(labels_tasV[succ.getNum()])==false){
						
						labels[succ.getNum()].setCoutV(cout_nouv);
						labels[succ.getNum()].setPereV(racine.getSommet());
						labels[succ.getNum()].setConnexeV(true);
						labels_tasV[succ.getNum()].setCout(cout_nouv);
						labels_tasV[succ.getNum()].setPere(racine.getSommet());
						
						tas.insert(labels_tasV[succ.getNum()]);
						//System.out.println("j'ajoute");
						//System.out.println("cout pieton"+labels[succ.getNum()].getCoutP()+"cout voiture"+labels[succ.getNum()].getCoutV()+"connexit� P "+labels[succ.getNum()].isConnexeP()+"connexit� V "+labels[succ.getNum()].isConnexeV());
						// mise � jour de la meilleur route � prendre pour les deux covoitureurs
						if(labels[succ.getNum()].getCoutP()!=0 && labels[succ.getNum()].getCoutD()!=0 &&  
									succ.getNum()!=pietonOrgn.getNum() && succ.getNum()!=dest.getNum() &&
									minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD())){
					
							minRoute=(labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD());
							rdv.setNum(succ.getNum());
						}
						
						
						//DESSIN DES SOMMETS 
						graphe.getDessin().setColor(java.awt.Color.BLUE) ;
						graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
						
					}
					else{   	//si le sommet successeur est dans le tas on met � jour si le nouveau cout est plus faible
						
						if(labels[succ.getNum()].getCoutV()>cout_nouv) {
							
							labels[succ.getNum()].setCoutV(cout_nouv);
							labels[succ.getNum()].setPereV(racine.getSommet());
							//le tas prend en compte ses modifications
							labels_tasV[succ.getNum()].setCout(cout_nouv);
							labels_tasV[succ.getNum()].setPere(racine.getSommet());
							tas.update(	labels_tasV[succ.getNum()]);
							
							if(labels[succ.getNum()].getCoutP()!=0 && labels[succ.getNum()].getCoutD()!=0 &&  
									succ.getNum()!=pietonOrgn.getNum() && succ.getNum()!=dest.getNum() &&
									minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD())){
					
							minRoute=(labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD());
							rdv.setNum(succ.getNum());
						}
							
						}
					}
				}
			}
			else {
				//DEST 
				cout_nouv=initCout(racine.getCoutD(),distance,vitesse,false);
				if(labels[succ.getNum()].isMarqueD()==false){
					
					//si le sommet successeur n'est pas dans le tas, on l'ajoute
					if(tas.contains(labels_tasD[succ.getNum()])==false){
						
						labels[succ.getNum()].setCoutD(cout_nouv);
						labels[succ.getNum()].setPereD(racine.getSommet());
						labels[succ.getNum()].setConnexeD(true);
						labels_tasD[succ.getNum()].setCout(cout_nouv);
						labels_tasD[succ.getNum()].setPere(racine.getSommet());
						
						tas.insert(labels_tasD[succ.getNum()]);
						if(labels[succ.getNum()].getCoutP()!=0 && labels[succ.getNum()].getCoutV()!=0 &&
										succ.getNum()!=pietonOrgn.getNum() && succ.getNum()!=voitureOrgn.getNum() &&
										minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD())){
						
							minRoute=(labels[succ.getNum()].getCoutD()+labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP());
							rdv.setNum(succ.getNum());
						}
						
						
						//DESSIN DES SOMMETS 
						graphe.getDessin().setColor(java.awt.Color.CYAN) ;
						graphe.getDessin().drawPoint(succ.getLongitudes(), succ.getLatitudes(), 5) ;
						
					}
					else{   	//si le sommet successeur est dans le tas on met � jour si le nouveau cout est plus faible
						
						if(labels[succ.getNum()].getCoutD()>cout_nouv) {
							
							labels[succ.getNum()].setCoutD(cout_nouv);
							labels[succ.getNum()].setPereD(racine.getSommet());
							//le tas prend en compte ses modifications
							labels_tasD[succ.getNum()].setCout(cout_nouv);
							labels_tasD[succ.getNum()].setPere(racine.getSommet());
							tas.update(	labels_tasD[succ.getNum()]);
							
							if(labels[succ.getNum()].getCoutP()!=0 && labels[succ.getNum()].getCoutV()!=0 &&
									succ.getNum()!=pietonOrgn.getNum() && succ.getNum()!=voitureOrgn.getNum() &&
									minRoute> (labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP()+labels[succ.getNum()].getCoutD())){
					
								minRoute=(labels[succ.getNum()].getCoutD()+labels[succ.getNum()].getCoutV()+labels[succ.getNum()].getCoutP());
								rdv.setNum(succ.getNum());
							}
							
						}
					}
				}
			}
		}
    		
		return minRoute;
    }


	@Override	
	public void run(){
		//long t1 = System.currentTimeMillis() ; 
    	//affichageDebut();
    	//initialisation d'un tas suppl�mentaire et du Sommets qui sera retourn�s.
    	// Pb des labels covoit est qu'on ne peux pas les utiliser dans le tas car deux couts diff�rents.
    	// on les utilisent pour combin� les informations des deux parcours sur chaque sommets. 
    	BinaryHeap<LabelPCC> tasP= new BinaryHeap<LabelPCC>();
    	BinaryHeap<LabelPCC> tasV= new BinaryHeap<LabelPCC>();
    	BinaryHeap<LabelPCC> tasD= new BinaryHeap<LabelPCC>();

    	Sommets rdv = new Sommets(0,0f,0f,0);
    	boolean rdvTrouve=false;
    	
    	
    	//Tableau contenant tous les labels covoit, indic� par rapport au num�ro de sommet 
    	LabelCovoit labels[] = new LabelCovoit[graphe.getTabSommets().length];
    	//Ces deux tableau stock les labels utilis� par le tas P ou tasV (indispensable pour la m�thode tas.contain(label))
    	LabelPCC labels_tasP[] = new LabelPCC[graphe.getTabSommets().length];
    	LabelPCC labels_tasV[] = new LabelPCC[graphe.getTabSommets().length];
    	LabelPCC labels_tasD[] = new LabelPCC[graphe.getTabSommetsInv().length];

    	//initialisation de tous les labels
    	for (int i=0;i<graphe.getTabSommets().length;i++){
    		labels[i]=initLabelCovoit(0,null,graphe.getSommets(i),voitureOrgn); 
    		labels_tasP[i]=initLabel(999999999,null,graphe.getSommets(i),false,voitureOrgn );
    		labels_tasV[i]=initLabel(999999999,null,graphe.getSommets(i),false,voitureOrgn );
    		labels_tasD[i]=initLabel(999999999,null,graphe.getSommetsInv(i),false,voitureOrgn) ; 
    	}
    	
   

		//initialisation des departs pieton et voiture
		labels[pietonOrgn.getNum()]= initLabelCovoit(0,pietonOrgn,pietonOrgn,voitureOrgn );
		labels[pietonOrgn.getNum()].setConnexeP(true);//connexe � lui m�me ...
		labels[voitureOrgn.getNum()]= initLabelCovoit(0,voitureOrgn, voitureOrgn,voitureOrgn );
		labels[voitureOrgn.getNum()].setConnexeV(true);//idem
		labels[dest.getNum()]=initLabelCovoit(0,dest,dest,voitureOrgn) ; 
		
		LabelPCC racineV = initLabel(0,voitureOrgn,voitureOrgn,false,voitureOrgn); 
		LabelPCC racineP=initLabel(0,pietonOrgn,pietonOrgn,false,voitureOrgn);
		LabelPCC racineD=initLabel(0,dest,dest,false,voitureOrgn) ; 
		
		
    	tasP.insert(racineP);
    	tasV.insert(racineV);
    	tasD.insert(racineD);
    	
    	//initialisation de valeurs utiles par la suite
    	float coutRoute=9999999;
    	
    	
  
    	
    	// Jusqu'a ce qu'on est notre point de rendez vous.
    	while (rdvTrouve==false && tasP.isEmpty()==false && tasV.isEmpty()==false && tasD.isEmpty()==false){
    		float cP,cV,cD ; 
    		cP=tasP.findMin().getCout() ; 
    		cV=tasV.findMin().getCout() ; 
    		cD=tasD.findMin().getCout() ;
    		
    		if((cV<=cD)&&(cV<=cP)) {
    			racineV=tasV.deleteMin() ;
    			coutRoute=rajoutVoisins(tasV,labels[racineV.getSommet().getNum()],labels,labels_tasP,labels_tasV,labels_tasD,coutRoute,Type.voiture,rdv);		
				labels[racineV.getSommet().getNum()].setMarqueV(true);
    		}
    		else if(cD<=cP) {
    			racineD=tasD.deleteMin() ;
    			coutRoute=rajoutVoisins(tasD,labels[racineD.getSommet().getNum()],labels,labels_tasP,labels_tasV,labels_tasD,coutRoute,Type.dest,rdv);		
				labels[racineD.getSommet().getNum()].setMarqueD(true);
    		}
    		else {
    			racineP=tasP.deleteMin();
  				coutRoute=rajoutVoisins(tasP,labels[racineP.getSommet().getNum()],labels,labels_tasP,labels_tasV,labels_tasD,coutRoute,Type.pieton,rdv);
  				labels[racineP.getSommet().getNum()].setMarqueP(true);
    		}
    		
    		
    		
  
    		if(tasP.isEmpty()==false && tasV.isEmpty()==false && tasD.isEmpty()==false){
	    		if(coutRoute<(tasP.findMin().getCout()+tasV.findMin().getCout()+tasD.findMin().getCout())){
	    			rdvTrouve=true;
	    		}
    		
    		}
    		System.out.println(tasD.size()+" "+tasP.size()+" "+tasV.size());
    	}
	    	
    	
    	
    	
    	
    	
    	if(tasP.isEmpty()==true || tasV.isEmpty()==true || tasD.isEmpty()){
    		System.out.println("les trois ne peuvent pas se rejoindre");
    	}
    	
    	//tracé du trajet voiture
    	LabelPCC labelIter= racineV ;
    	ArrayList<Sommets> listeSommetPccV= new ArrayList<Sommets>();
    	int nbSommetsPccV = 0 ; 
    	while(!(labelIter.getSommet().equals(voitureOrgn))) {
    		listeSommetPccV.add(labelIter.getSommet()) ; 
    		nbSommetsPccV++ ; 
    		labelIter=labels_tasV[labelIter.getPere().getNum()];
    	}
    	listeSommetPccV.add(labelIter.getSommet()) ; 
    	Chemin cheminPcc=new Chemin(nbSommetsPccV,listeSommetPccV,graphe.getIdCarte(),graphe.getDessin());
		cheminPcc.dessinerChemin(Color.RED);
		
		//tracé du trajet pieton
    	LabelPCC labelIterP= racineP ;
    	ArrayList<Sommets> listeSommetPccP= new ArrayList<Sommets>();
    	int nbSommetsPccP = 0 ; 
    	while(!(labelIterP.getSommet().equals(pietonOrgn))) {
    		listeSommetPccP.add(labelIterP.getSommet()) ; 
    		nbSommetsPccP++ ; 
    		labelIterP=labels_tasP[labelIterP.getPere().getNum()];
    	}
    	listeSommetPccP.add(labelIterP.getSommet()) ; 
    	Chemin cheminPccP=new Chemin(nbSommetsPccP,listeSommetPccP,graphe.getIdCarte(),graphe.getDessin());
		cheminPccP.dessinerChemin(Color.GRAY);
		
		//tracé du trajet voiture
    	LabelPCC labelIterD= racineD ;
    	ArrayList<Sommets> listeSommetPccD= new ArrayList<Sommets>();
    	int nbSommetsPccD = 0 ; 
    	while(!(labelIterD.getSommet().equals(dest))) {
    		listeSommetPccD.add(labelIterD.getSommet()) ; 
    		nbSommetsPccD++ ; 
    		labelIterD=labels_tasD[labelIterD.getPere().getNum()];
    	}
    	listeSommetPccD.add(labelIterD.getSommet()) ; 
    	Chemin cheminPccD=new Chemin(nbSommetsPccD,listeSommetPccD,graphe.getIdCarte(),graphe.getDessin());
		cheminPccD.dessinerChemin(Color.MAGENTA);
		
		
    	System.out.println("temps: "+coutRoute+"rdv au sommet: "+ rdv.getNum()+"le pieton marche: "+labels[rdv.getNum()].getCoutP()+"min et la voiture met: "+labels[rdv.getNum()].getCoutV()+"min");
    	
    	//System.out.println("le rendez vous est en:" +rdv.getNum()+"cela prendra "+labels[rdv.getNum()]+"min au pieton"+ "et"+labels[rdv.getNum()]+"min pour la voiture et son conducteur");
    	
	}
	
	protected LabelPCC initLabel(float cout, Sommets Pere, Sommets sommet,boolean marque,Sommets Destination){
    	return new LabelPCC(cout,Pere,sommet,marque);
	}
	
    // on change l'initialisation des labels car nouveau label: LabelCovoit
    // coutV et coutP sont d�j� initialis� � -1
    
    public LabelCovoit initLabelCovoit(float cout, Sommets pere, Sommets sommet, Sommets Destination){
    	return new LabelCovoit(cout,sommet,pere);
    }
	
	public float initCout(float cout,int distance,float vitesse,boolean pieton){
		if(pieton){
			// on a exclut dans les m�thodes qui appelle initCout les vitesses sup�rieures � 110, de cette mani�re le sommet n'est pas ajout�. 
			// sa vitesse est toujours de 4km/h
			vitesse=4;
			return cout + (60.0f*distance)/(1000*vitesse);
		}
		else{
			return cout + (60.0f*distance)/(1000*vitesse);
		}
		
	
	}
	/*
	// Afin de faire des Astar
    private float calculTemps(Sommets sommet){
    	float distance=(float)graphe.distance(sommet.getLongitudes(),sommet.getLatitudes(),voitureOrgn.getLongitudes(),voitureOrgn.getLatitudes()); //mètre
    	float vitesse=(float)130; //km/h
    	return (60.0f*distance)/(1000*vitesse);			
    }*/
}
