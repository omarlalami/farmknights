package arenesolo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import jeu.Plateau;
import jeu.Joueur.Action;




public class MonJoueurPourTestFonction extends jeu.Joueur {

	/*
	 * 
	 * C'est ma classe de test tu peut ignorer sa ...
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
	
	
    public MonJoueurPourTestFonction(String nom) { super(nom); }
	
    // les points de notre map accessible, c'est a dire sans arbres sur notre chemin
    public ArrayList<Point> listeAccessible(){
    
		
	/*	if (    Plateau.joueurPeutAllerIci(p.getX(),p.getY(),true,true    ) )
		{
			
		}
    	*/
    //	Plateau.donneCheminAvecObstaclesSupplementaires(this.donnePosition(),new Point(7, 6),)
    	return null;
    }
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu) {
    	
    	
    	int taille = etatDuJeu.donneGrillePourAstar().length;
    	
    	HashMap<Integer, ArrayList<Point>> listYourt = etatDuJeu.cherche(this.donnePosition(), taille*3, Plateau.CHERCHE_CHAMP);
    	//HashMap<Integer, ArrayList<Point>> listYourt = etatDuJeu.cherche(this.donnePosition(), taille*3, Plateau.CHERCHE_YOURTE);
    	

    	/*if(etatDuJeu.donneContenuCellule(this.donnePosition())==ENDROIT_INFRANCHISSABLE);
    	{
    		
    	}*/
    	Iterator<Map.Entry<Integer, ArrayList<Point>>> it = listYourt.entrySet().iterator();
    	
    	while (it.hasNext()) {
    		Map.Entry<Integer, ArrayList<Point>> entry = it.next();
    		
    		Integer i = entry.getKey();
    		ArrayList<Point> al = entry.getValue();
    		
    		System.out.println("nos i : "+i);
    		
    		// on itere sur chaque arraylist pour voir ce quil ya dedans
    		Iterator <Point> ital = al.iterator();
    		while (ital.hasNext()) {
    			Point p = ital.next();
    			System.out.println(p);
    			
       		}
    		
    		System.out.println("*********************");
    			
    		
    	}
    	
    	return Action.DROITE;
    }
	
}
