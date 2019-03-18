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

    	System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa "+etatDuJeu.donneGrillePourAstar().length);
    	
    	HashMap<Integer, ArrayList<Point>> listJoueurs = new HashMap<Integer, ArrayList<Point>>();
    	HashMap<Integer, ArrayList<Point>> listYourt = new HashMap<Integer, ArrayList<Point>>();
    	HashMap<Integer, ArrayList<Point>> listChamps = new HashMap<Integer, ArrayList<Point>>();
    	
    	int taille;  				//taille du plateau lxH
    	int seuilVigeur;			//seuille de vigeur acceptable selon taille plateau 
    	int seuilVigeurUrgence;			//seuille de vigeur d'urgence selon taille plateau 
    	
    	
    	taille = etatDuJeu.donneGrillePourAstar().length;
    	
    	// dans le cas d'un PLATEAU_FK_PETIT on a une valeur de  etatDuJeu.donneGrillePourAstar().length  ==  20
    	// dans le cas d'un PLATEAU_FK_DEFAUT on a une valeur de  etatDuJeu.donneGrillePourAstar().length  ==  8
    	// je pense c'est plus interessant de mettre sa comme valeur
    	// met ton avis ici
    	
    	
    	seuilVigeur = taille;
    	seuilVigeurUrgence = taille/2;
    	
    	listYourt = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_YOURTE);
    	listChamps = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_CHAMP);
    	


       
    	
    	
    	
    	if( this.donneVigueur() <= seuilVigeurUrgence) {					// en cas d'urgence on cherche a gagner des pts vigueur avec des yourte ou joueurs (shifumi) peut importe
          	
        	
        	
        	
    	}
    	else if (this.donneVigueur() <= seuilVigeur)    																	// en cas de niveau minimum on cherche des yourte en evitant les joueurs
		{
    		affiche("notre joueur a atteint un niveau de seuil de vigueur necessitant de chercher un Yourt");
    		
    		
    		
    		/*
    		 * on recupere nos Yourt ( en haut )
    		 * 
    		 * 
    		 * a chaque tour : 
    		 * 
    		 * on regarde lequel est le plus proche
    		 * 
    		 * 
    		 * on cherche le meuilleur chemin tout en evitant les joueurs
    		 * 
    		 * on recupere le premier point de ce chemin et on y avance !
    		 * 
    		 * */
    		
    		
    		
    		
    		
    		listJoueurs = etatDuJeu.cherche(this.donnePosition(), taille, Plateau.CHERCHE_JOUEUR);
    		
    		int distance;
    		int distanceLaPlusCourte=taille;											// on suppose que la distance la plus courte est la taille du cote du plateau jeu ( le plateau est un carre )
    		
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