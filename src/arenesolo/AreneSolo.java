package arenesolo;
/*
 * Programme minimal pour lancer une partie en solo avec son propre joueur.
 */





import jeu.Plateau;
import jeu.MaitreDuJeuFK;
import gui.FenetreDeJeuFK;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;



import jeu.Joueur;
import jeu.astar.Node;

public class AreneSolo {
	public static final String	PLATEAU_FK_PETIT = 
            "+----------------+\n"+
            "|$$  F-      $$  |\n"+
            "|  @1    F-    $-|\n"+
            "|##          ##  |\n"+
            "|  ##########  ##|\n"+
            "|        @2    @3|\n"+
            "|  @4    F-      |\n"+
            "|              F-|\n"+
            "|  ##  ######    |\n"+
            "+----------------+";
    
    public static void main(String[] args) {
       // Plateau p = new Plateau( 100, PLATEAU_FK_PETIT);
       // Plateau p = new Plateau( 1200, MaitreDuJeuFK.PLATEAU_FK_DEFAUT);
        Plateau p = Plateau.generePlateauAleatoireFK(1000,20,4,20,100);
        MaitreDuJeuFK jeu = new MaitreDuJeuFK(p);
        
        jeu.metJoueurEnPosition(0, new MonJoueur("joueur 1"));
        jeu.metJoueurEnPosition(1, new MonJoueur("joueur 2"));
        jeu.metJoueurEnPosition(2, new MonJoueur("joueur 3"));
        jeu.metJoueurEnPosition(3, new MonJoueur("joueur 4"));
        
        FenetreDeJeuFK f = new FenetreDeJeuFK(jeu, true, true);
        f.log = new java.io.File("/tmp/toto.log");
        
        f.addWindowListener( new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) { }

            @Override
            public void windowClosing(WindowEvent e) { }

            @Override
            public void windowClosed(WindowEvent e) { System.exit(0 );}

            @Override
            public void windowIconified(WindowEvent e) {  }

            @Override
            public void windowDeiconified(WindowEvent e) { }

            @Override
            public void windowActivated(WindowEvent e) { }

            @Override
            public void windowDeactivated(WindowEvent e) { }
        });
        
        /* Code facultatif pour récupérer les clics souris sur le plateau
         * Pour interagir avec votre algo pendant la phase de développement.
         * Par exemple : */
            f.setMouseClickListener( (int x, int y, int bt) -> {
              Joueur j = p.donneJoueur(p.donneJoueurCourant());
              ArrayList<Node> a = p.donneCheminEntre(j.donnePosition(), new Point(x,y));
              f.afficheAstarPath(a);
            });
        
        java.awt.EventQueue.invokeLater(() -> { f.setVisible(true); });
    }
    
}