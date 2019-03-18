package arenesolo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import jeu.Plateau;
import jeu.astar.AStar;
import jeu.astar.Node;
import jeu.Joueur.Action;




public class MonJoueurPourTestFonction extends jeu.Joueur {

	/*
	 * 
	 * C'est ma classe de test tu peut ignorer sa ...
	 * 
	 * 
	 * */
	
	
    public MonJoueurPourTestFonction(String nom) { super(nom); }
    
	public void affiche(String msg){
		System.out.println(msg);
	}
	
	
	/*
	public ArrayList<Node> donneCheminAvecObstaclesSupplementairesPersonnel(Point depart, Point cible, ArrayList<Node> obstacles){
		
		ArrayList<Node> obstaclesComplet = new ArrayList();
		
		for(Node n : obstacles) {
			
			obstaclesComplet.add(n);
			
			int x = n.getPosX();
			int y = n.getPosY();
		
			obstaclesComplet.add(new Node(x+1,y));
			obstaclesComplet.add(new Node(x-1,y));
			obstaclesComplet.add(new Node(x,y+1));
			obstaclesComplet.add(new Node(x,y-1));

		}		
		
		return obstaclesComplet;
	}
	*/
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
	
	/* les points de notre map accessible, c'est a dire sans arbres sur notre chemin
	 * ( prend en compte le cas des objets dans des zones infranchissable )
	 * 
	 * @param etatDuJeu
	 * 
	 * @param cherche_object : noter objet Plateau.CHERCHE_CHAMP ....
	 * 
	 * @return une ArrayList des objets atteignable 
	 */
	
    public ArrayList<Node> objetsAccessible(Plateau etatDuJeu,int cherche_object){
    
    	int taille = etatDuJeu.donneGrillePourAstar().length;
    	
    	HashMap<Integer, ArrayList<Point>> list = etatDuJeu.cherche(this.donnePosition(), taille, cherche_object);
		
    	Iterator<Map.Entry<Integer, ArrayList<Point>>> it = list.entrySet().iterator();

    	ArrayList<Node> listeObjetsAtteignable = new ArrayList();
    	
    	while (it.hasNext()) {
    		Map.Entry<Integer, ArrayList<Point>> entry = it.next();
    		
    		Integer i = entry.getKey();
    		ArrayList<Point> al = entry.getValue();
    		
    		Iterator <Point> ital = al.iterator();
    		while (ital.hasNext()) {
    			Point p = ital.next();
    			
    			ArrayList<Node> chemin = etatDuJeu.donneCheminEntre(this.donnePosition(), p);
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
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu) {
    	
    	ArrayList<Node> listeChampAtteignable = objetsAccessible(etatDuJeu,Plateau.CHERCHE_CHAMP);
    	ArrayList<Node> listeYourteAtteignable = objetsAccessible(etatDuJeu,Plateau.CHERCHE_YOURTE);
    	ArrayList<Node> listeJoueurAtteignable = objetsAccessible(etatDuJeu,Plateau.CHERCHE_JOUEUR);
    	
    	System.out.println("champ : ");
    	for(Node n : listeChampAtteignable) {
    		System.out.println(n);
    	}
    	System.out.println("yourth : ");
    	for(Node n : listeYourteAtteignable) {
    		System.out.println(n);
    	}
    	System.out.println("joueur : ");
    	for(Node n : listeJoueurAtteignable) {
    		System.out.println(n);
    	}
    	
    	
    	System.out.println("chemin entre position joueur et les champs :");
    	
    	for(Node n : listeChampAtteignable) {
    		System.out.println("champ : "+n);
    		Point point = new Point(n.getPosX(),n.getPosY());

    		ArrayList<Node> cheminSansContrainte = etatDuJeu.donneCheminEntre(this.donnePosition(),point);
        	System.out.println(cheminSansContrainte);

        	ArrayList<Node> cheminAvecContrainteSupplementaires = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignable);
        	System.out.println(cheminAvecContrainteSupplementaires);
        	
        	int taille = etatDuJeu.donneGrillePourAstar().length;

        	
        	ArrayList<Node> listeJoueurAtteignableComplet = obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
       	
        	System.out.println("**********");
        	
        	System.out.println("avant ajout obstacle : " +listeJoueurAtteignable);
        	System.out.println("apre ajout obstacle : " +listeJoueurAtteignableComplet);
        	
        	System.out.println("**********");
       	
        	
        	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
        	System.out.println(cheminAvecContrainteSupplementaires);    

    	}
    	
    	
    	return Action.DROITE;
    }
	
}
