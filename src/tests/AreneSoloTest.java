package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arenesolo.MonJoueur;
import gui.FenetreDeJeuFK;
import jeu.MaitreDuJeu;
import jeu.MaitreDuJeuFK;
import jeu.MaitreDuJeuListener;
import jeu.Plateau;
import jeu.MaitreDuJeuListener.Symboles;

class AreneSoloTest {

	static Plateau p;
	static MaitreDuJeuFK jeu;
	static FenetreDeJeuFK f ;
	static String PLATEAU_FK_PETIT;
	static MonJoueur personnage1;
	static MonJoueur personnage2;
	static MonJoueur personnage3;
	static MonJoueur personnage4;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		PLATEAU_FK_PETIT = 
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
		
		p = new Plateau( 100, PLATEAU_FK_PETIT);
		jeu = new MaitreDuJeuFK(p);
	
		personnage1 = new MonJoueur("joueur 1");
		personnage2 = new MonJoueur("joueur 2");
		personnage3 = new MonJoueur("joueur 3");
		personnage4 = new MonJoueur("joueur 4");
		
        jeu.metJoueurEnPosition(0, personnage1);
        jeu.metJoueurEnPosition(1, personnage2);
        jeu.metJoueurEnPosition(2, personnage3);
        jeu.metJoueurEnPosition(3, personnage4);

        FenetreDeJeuFK f = new FenetreDeJeuFK(jeu, true, true);
        
        jeu.addEcouteurDuJeu(new MaitreDuJeuListener() {
			
			@Override
			public void unJeuAChange(MaitreDuJeu arg0) {
				
			}
			
			@Override
			public void nouveauMessage(MaitreDuJeu arg0, String arg1) {

				System.out.println(arg1);
			}
			
			@Override
			public void afficheSymbole(MaitreDuJeu arg0, Symboles arg1, Point arg2, int arg3, int arg4) {
				
			}
		});
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testMain() {
		jeu.continueLaPartie(true);
		
	}

}
