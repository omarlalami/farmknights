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
import arenesolo.Utils;

import arenesolo.Utils;


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
	

    
    
    @Override
    public Action faitUneAction(Plateau etatDuJeu) {
    	Utils utilitaire = new Utils();
    	
    	ArrayList<Node> listeChampAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_CHAMP);
    	ArrayList<Node> listeYourteAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_YOURTE);
    	ArrayList<Node> listeJoueurAtteignable = utilitaire.objetsAccessible(this.donnePosition(),etatDuJeu,Plateau.CHERCHE_JOUEUR);
    	
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

        	
        	ArrayList<Node> listeJoueurAtteignableComplet = this.obstaclesSupplementairesPersonnel(this.donnePosition(),taille,listeJoueurAtteignable);
       	
        	System.out.println("**********");
        	
        	System.out.println("avant ajout obstacle : " +listeJoueurAtteignable);
        	System.out.println("apre ajout obstacle : " +listeJoueurAtteignableComplet);
        	
        	System.out.println("**********");
       	
        	
        	ArrayList<Node> cheminAvecContrainteSupplementairesFinal = etatDuJeu.donneCheminAvecObstaclesSupplementaires(this.donnePosition(), point, listeJoueurAtteignableComplet);
        	System.out.println(">"+cheminAvecContrainteSupplementaires);    
        	System.out.println(">>"+cheminAvecContrainteSupplementairesFinal);    

    	}
    	
    	
    	return Action.DROITE;
    }
	
}
