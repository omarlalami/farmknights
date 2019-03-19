package arenesolo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import jeu.Joueur.Action;
import jeu.Plateau;
import jeu.astar.Node;

public class Utils {
	
	/*
	 * Methode d'affichage demandes qui remplace System.out.println();
	 * 
	 */
	public void affiche(String msg){
		System.out.println(msg);
	}
	
	/* Cette methode nous renvoi les points de notre map accessible, c'est a dire sans arbres sur notre chemin
	 * et sa prend en compte le cas des objets dans des zones infranchissable
	 * 
	 * @param etatDuJeu : l'etat du jeu en cours
	 * 
	 * @param cherche_object : notre objet Plateau.CHERCHE_CHAMP ....
	 * 
	 * @return une ArrayList des objets '@param cherche_object' atteignable 
	 */
    public ArrayList<Node> objetsAccessible(Point pActuel, Plateau etatDuJeu,int cherche_object){
    
    	// notre liste qui contient nos objets @param cherche_object atteignable
    	ArrayList<Node> listeObjetsAtteignable = new ArrayList();
    	
    	int taille = etatDuJeu.donneGrillePourAstar().length;
    	
    	
    	// on recupere une liste qui contient nos objets @param cherche_object
    	
    	HashMap<Integer, ArrayList<Point>> list = etatDuJeu.cherche(pActuel, taille, cherche_object);
    	
    	Iterator<Map.Entry<Integer, ArrayList<Point>>> it = list.entrySet().iterator();
    	
    	
    	
    	// on itere sur notre liste d'objets
    	
    	while (it.hasNext()) {
    		
    		Map.Entry<Integer, ArrayList<Point>> entry = it.next();
    		
    		Integer i = entry.getKey();
    		
    		ArrayList<Point> al = entry.getValue();
    		
    		Iterator <Point> ital = al.iterator();
    		
    		while (ital.hasNext()) {
    			
    			Point destination = ital.next();
    			
    			ArrayList<Node> chemin = etatDuJeu.donneCheminEntre(pActuel, destination);
    			
    			// si un chemin existe entre notre 'position actuel' et la 'destination' alors on ajoute cette destination a notre liste
    			
    			if(chemin!=null) {
    				
	    			int x = destination.x;
	    			int y = destination.y;
	    			
	    			listeObjetsAtteignable.add(new Node(x, y));
    			}
       		}
    	    			
    	}

    	return listeObjetsAtteignable;
    }
    
    
	/*
	 * Cette fonction va retourner les positions des obstacles mais aussi les positions adjacent aux obstacles
	 * 
	 * @param posActuel : le Point a position 
	 * 
	 * @param taille :
	 * 
	 * @return une ArrayList de tous les Points des objets '@param obstacles' a eviter mais aussi les Points des positions adjacentes a ces objets
	 * 
	 */
	public ArrayList<Node> obstaclesSupplementairesPersonnel(Point posActuel, int taille, ArrayList<Node> obstacles){
		
		ArrayList<Node> obstaclesComplet = new ArrayList();
		
		for(Node n : obstacles) {
			
			obstaclesComplet.add(n);
			
			int x = n.getPosX();
			int y = n.getPosY();
			if(posActuel.getX()!=x && posActuel.getY()!=y)	// on ne met pas en place la position courante comme un obstacle
			{
				if (x+1<taille)
					obstaclesComplet.add(new Node(x+1,y));
				if (x-1>=0)
					obstaclesComplet.add(new Node(x-1,y));
				if (y+1<taille)
					obstaclesComplet.add(new Node(x,y+1));
				if (y-1>=0)
					obstaclesComplet.add(new Node(x,y-1));
			}
		}		
		
		return obstaclesComplet;
	}
	
	
	
	
	/*
	 * cette methode retourne l'Action a faire pour permettre de se deplacer d'un point a un autre
	 * 
	 * @param actuel : le point actuel d'un joueur
	 * 
	 * @param dest : le point ou le joueur souhaite aller
	 * 
	 * @return l'Action a effectuer pour se rapprocher de la destination
	 * 
	 * */
	public Action deplacement(Point actuel,Point dest) {
    	
    	System.out.println("je vais vers : "+dest);
    	
    	//recuperation x et y du point courant
		int myx = actuel.x;
    	int myy = actuel.y;
    	
    	//pour un deplacement de case en case on sait que uniquement 
    	//une des deux coordonnées de la destination(x ou y) sera différente du X ou Y de la position courante
    	
    	//ici on test si c'est les X qui sont différents 
    	if (dest.x!=myx) {
    		// si le x de la destination est supérieur on va à droite
    		if (dest.x>myx) return Action.DROITE;
    		//sinon on va à gauche
    		else return Action.GAUCHE;
    	}
    	//ici on test si c'est les Y qui sont différents 
    	if (dest.y!=myy) {
    		// si le x de la destination est supérieur on va en bas
    		if (dest.y>myy) return Action.BAS;
    		//sinon on va à haut
    		else return Action.HAUT;
    	}
    	
    	return null;
    }
	
	
	
}
