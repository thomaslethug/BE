package core;


public class UnEntier implements Comparable<UnEntier> {

    // Attribut mutable
    private int zeint ;

    public UnEntier(int x) {
	this.zeint = x ;
    }

    public int get() {
	return this.zeint ;
    }

    public void set(int x) {
	this.zeint = x ;
    }

    public String toString() {
	return "" + this.zeint ;
    }

    public int compareTo(UnEntier other) {
	// Ne pas utiliser a bord d'un avion : ne resiste pas a un overflow.
	return this.zeint - other.zeint ;
    }


    // Voici le test reel.
    public static void main(String[] args) {

	final int max = 40 ;

	// Des entiers que l'on utilise pour tester
	UnEntier[] tab = new UnEntier[max+1] ;
	
	// Initialisation du tableau
	for (int i = 0 ; i < tab.length ; i++) {
	    tab[i] = new UnEntier(i) ;
	}

	// Un tas d'entiers.
	BinaryHeap<UnEntier> tas = new BinaryHeap<UnEntier>() ;

	// On remplit le tas avec nos entiers. D'abord de 49 vers 0 puis de 51 vers 100.
	for (int i = max/2 -1 ; i >= 0 ; i--) {
	    tas.insert(tab[i]) ;
	}

	for (int i = max/2 ; i < tab.length ; i++) {
	    tas.insert(tab[i]) ;
	}

	// On verifie que le tas est bien trie.
	tas.printSorted() ;
	
	//test de update en plein milieu
	tab[3].set(1);
	tab[1].set(2);
	tas.print();
	tas.update(tab[1]) ;
	tas.print();
	//test de update en 0
	tab[0].set(3);
	tas.print();
	tas.update(tab[0]) ;
	tas.print();
	//test de update à la fin
	tab[max-4].set(0);
	tas.print();
	tas.update(tab[max-4]) ;
	tas.print();
	tas.printSorted() ;

	// On change la valeur de tous les elements
	for (int i = 0 ; i < tab.length ; i++) {
	    UnEntier element = tab[i] ;
	    element.set( max - element.get() ) ;

	    // Cette ligne ne compile pas avec le tas que l'on vous fournit au depart, c'est normal.
	    // A vous de completer le tas pour que cela fonctionne.
	   tas.update(element) ;

	}
	// Et on verifie que le tas est toujours trie.
	//tas.printSorted() ;
	//tas.print();
	
	

    }
}
