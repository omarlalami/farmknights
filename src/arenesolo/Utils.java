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
	
	
	
	
	public Action ActionVigeurCritique(Point actuel, HashMap<Integer, ArrayList<Point>> listYourt, HashMap<Integer, ArrayList<Point>> listJoueurs, int taille, Plateau etatDuJeu)
	{
    	Point HillLePluisProche = null;
		Point Hill;
		int distance=0;
		int distanceLaPlusCourte=taille;
		
		Iterator<Entry<Integer, ArrayList<Point>>> iterYourteGeneral = listYourt.entrySet().iterator();
		
		while (iterYourteGeneral.hasNext()) {
			Iterator<Point> iterYourtPoints = iterYourteGeneral.next().getValue().iterator();
			
			while(iterYourtPoints.hasNext()) {
				
				Hill = iterYourtPoints.next();
				
				if(etatDuJeu.donneCheminAvecObstaclesSupplementaires(Hill, actuel,this.getArbres(etatDuJeu))!=null) {
					
					distance = etatDuJeu.donneCheminAvecObstaclesSupplementaires(Hill,actuel,this.getArbres(etatDuJeu)).size();
					
					if (distance<distanceLaPlusCourte) {
						HillLePluisProche = Hill;
						distanceLaPlusCourte = distance;
	
						System.out.println("j'ai des Yourt");
						}
				}
				
			}
		}
		
		listJoueurs = etatDuJeu.cherche(actuel, taille, Plateau.CHERCHE_JOUEUR);
		System.out.println("lmist joueurs est il vide ? "+listJoueurs.size());
		Iterator<Entry<Integer, ArrayList<Point>>> iterJoueurGeneral = listJoueurs.entrySet().iterator();
		
		
		
		while (iterJoueurGeneral.hasNext()) {
			Iterator<Point> iterJoueurPoints = iterJoueurGeneral.next().getValue().iterator();
			System.out.println("fetch 1 ");
			
			while(iterJoueurPoints.hasNext()) {
				
				System.out.println("fetch 2 ");
				Hill = iterJoueurPoints.next();
				
				if(!Hill.equals(actuel)) {
					
					if(etatDuJeu.donneCheminAvecObstaclesSupplementaires(Hill, actuel,this.getArbres(etatDuJeu))!=null) {
					
						distance = etatDuJeu.donneCheminAvecObstaclesSupplementaires(Hill, actuel,this.getArbres(etatDuJeu)).size();
	    				if ((distance<distanceLaPlusCourte)) {
							System.out.println("j'ai des joueurs");
	    					HillLePluisProche = Hill;
	    					distanceLaPlusCourte = distance;
	    				}
					}
    			}
    		}
		}
		
		//System.out.println("jusque ici tout vas bien");
		
		int x = etatDuJeu.donneCheminAvecObstaclesSupplementaires(actuel, HillLePluisProche, getArbres(etatDuJeu)).get(0).getPosX();
		//System.out.println("x :"+x);
		
		int y = etatDuJeu.donneCheminAvecObstaclesSupplementaires(actuel, HillLePluisProche, getArbres(etatDuJeu)).get(0).getPosY();
		//System.out.println("y : "+y);
		
		System.out.println("chemin destination : "+etatDuJeu.donneCheminAvecObstaclesSupplementaires(actuel, HillLePluisProche, getArbres(etatDuJeu)));
		
		
		Point dest = new Point(x,y);
		System.out.println("j'ai recuperer le point de destination suivant : "+dest);
		System.out.println(dest.x + " " + dest.y);
		return deplacement(actuel,dest);
		
    	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public ArrayList<Node> getNosChamps(Plateau etatDuJeu) {
		ArrayList<Node> listeArbres = new ArrayList<Node>();
		for (int i = 0; i < etatDuJeu.donneGrillePourAstar().length; i++) {
			for (int j = 0; j < etatDuJeu.donneGrillePourAstar().length; j++) {
				if(etatDuJeu.donneContenuCellule(i, j)==Plateau.ENDROIT_CHAMP_J1) 
					listeArbres.add(new Node(i,j));
				
			}
		}
		return null;
	}
	
	
	
	
	
	
	public ArrayList<Node> getArbres (Plateau etatDuJeu) {
		ArrayList<Node> listeArbres = new ArrayList<Node>();
		for (int i = 0; i < etatDuJeu.donneGrillePourAstar().length; i++) {
			for (int j = 0; j < etatDuJeu.donneGrillePourAstar().length; j++) {
				if(etatDuJeu.donneContenuCellule(i, j)==Plateau.ENDROIT_INFRANCHISSABLE)
					listeArbres.add(new Node(i,j));
				
			}
		}
		return null;
	}
	
	
	
	
	
	/* les points de notre map accessible, c'est a dire sans arbres sur notre chemin
	 * ( prend en compte le cas des objets dans des zones infranchissable )
	 * 
	 * @param etatDuJeu
	 * 
	 * @param cherche_object : noter objet Plateau.CHERCHE_CHAMP ....
	 * 
	 * @return une ArrayList des objets atteignable 
	 */
	
    public ArrayList<Node> objetsAccessible(Point pActuel, Plateau etatDuJeu,int cherche_object){
    
    	int taille = etatDuJeu.donneGrillePourAstar().length;
    	
    	HashMap<Integer, ArrayList<Point>> list = etatDuJeu.cherche(pActuel, taille, cherche_object);
		
    	Iterator<Map.Entry<Integer, ArrayList<Point>>> it = list.entrySet().iterator();

    	ArrayList<Node> listeObjetsAtteignable = new ArrayList();
    	
    	while (it.hasNext()) {
    		Map.Entry<Integer, ArrayList<Point>> entry = it.next();
    		
    		Integer i = entry.getKey();
    		ArrayList<Point> al = entry.getValue();
    		
    		Iterator <Point> ital = al.iterator();
    		while (ital.hasNext()) {
    			Point p = ital.next();
    			
    			ArrayList<Node> chemin = etatDuJeu.donneCheminEntre(pActuel, p);
    			if(chemin!=null) {
	    			int x = p.x;
	    			int y = p.y;
	    			//if(this.donnePosition().getX()!=x && this.donnePosition().getY()!=y) // on n'ajoute pas la position actuel comme obstacle
	    				listeObjetsAtteignable.add(new Node(x, y));
    			}
       		}
    	    			
    	}

    	return listeObjetsAtteignable;
    }
    
    
	/*
	 * 
	 * 
	 * cette fonction va les obstacles mais aussi les points adjacent aux obstacles
	 * 
	 */
	public ArrayList<Node> obstaclesSupplementairesPersonnel(Point p, int taille,ArrayList<Node> obstacles){
		
		ArrayList<Node> obstaclesComplet = new ArrayList();
		
		for(Node n : obstacles) {
			
			obstaclesComplet.add(n);
			
			int x = n.getPosX();
			int y = n.getPosY();
			if(p.getX()!=x && p.getY()!=y)	// on ne met pas en place la position courante comme un obstacle
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
