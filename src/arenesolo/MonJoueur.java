package arenesolo;
/*
 * Code minimal pour crÃ©er son propre joueur.
 */


import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;

import jeu.Plateau;

public class MonJoueur extends jeu.Joueur {
	
	public void affiche(String msg){
		System.out.println(msg);
	}
    
    public MonJoueur(String nom) { super(nom); }
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu) {

    	HashMap<Integer, ArrayList<Point>> listJoueurs = new HashMap<Integer, ArrayList<Point>>();
    	HashMap<Integer, ArrayList<Point>> listYourt = new HashMap<Integer, ArrayList<Point>>();
    	HashMap<Integer, ArrayList<Point>> listChamps = new HashMap<Integer, ArrayList<Point>>();
    	
    	int taille;  				//taille du plateau lxH
    	int seuilleVigeur;			//seuille de vigeur acceptable selon taille plateau 
    	int vigeurUrgence;			//seuille de vigeur d'urgence selon taille plateau 
    	
    	
    	taille = etatDuJeu.donneGrillePourAstar().length;
    	seuilleVigeur = taille/4;
    	vigeurUrgence = seuilleVigeur/2;
    	
    	listYourt = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_YOURTE);
    	listChamps = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_CHAMP);
    	


       
    	
    	// en cas d'urgence on cherche a gagner des pts vigueur avec
    	// des yourte ou joueurs peut importe
    	if( this.donneVigueur() <= vigeurUrgence) {
          	
        	
        	
        	
    	}
    	// en cas de niveau minimum on cherche des yourte en evitant les joueurs
    	
    	
    	
    	/*
    	 * 
    	 * PARTIE DE OMAR PAS FINI
    	 * 
    	 * 
    	 * 
    	 * */
    	else if (this.donneVigueur() <= seuilleVigeur)
		{
    		listJoueurs = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_JOUEUR);
    		
    		int distance;
    		int distanceLaPlusCourte=taille;// on suppose la distance la plus courte est la taille du jeu
    		
    		Iterator<Entry<Integer, ArrayList<Point>>> iterYourteGeneral;
    		Iterator<Point> iterYourtPoints;
    		Point Hill;
    		
    		Point HillLePlusProche;
    		
    		
	    	iterYourteGeneral  = listYourt.entrySet().iterator();

	    	affiche("Points où il y a des Yourt : ");
	    	

	    	// on recherche notre Yourt le plus proche
	    	while (iterYourteGeneral.hasNext()) {
	    		
	    		iterYourtPoints = iterYourteGeneral.next().getValue().iterator();
	    		
	    		while(iterYourtPoints.hasNext()) {
	    			
	    			Hill = iterYourtPoints.next();
	    			distance = etatDuJeu.donneCheminEntre(Hill, this.donnePosition()).size();
	    			if( distance < distanceLaPlusCourte ) {
	    				distanceLaPlusCourte = distance ;
	    				HillLePlusProche = Hill;
	    				//etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), HillLePlusProche, listJoueurs.getValue());
	    			}
	    		}
    		
	    	}
	    	
	    	
	    	
	    	
		
		}
    	// sinon par default on cherche a gagner des blobs/champs
		else
		{
			
			
    	
	    	HashMap<Integer, ArrayList<Point>> listElements = new HashMap<Integer, ArrayList<Point>>();
	    	listElements = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_TOUT);
	    	
	    	ListIterator<Point> iter = listElements.get(1).listIterator();
	    	
	    	System.out.println("Points où il y a des Yourt : ");
	    	int i = 0;
	    	while (iter.hasNext()|| i < 50) {
	    		
	    		System.out.println(iter.next());
    		
	    	}
    	
    		
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
				
	
    	
        return super.faitUneAction(etatDuJeu);
    }
}