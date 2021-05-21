package twistLock.controleur;

import twistLock.TwistLock;
import twistLock.ihm.IHMGui;

import twistLock.metier.Container;
import twistLock.metier.Lock;

import javax.swing.*;

public class Controleur
{

	public static final boolean DEBUG = true;

	private TwistLock metier;
	//private IHMCui    ihm;
	private IHMGui    ihm;

	public Controleur()
	{
		this.metier        = new TwistLock(10, 7);
		//this.ihm           = new IHMCui(this);
		this.ihm           = new IHMGui(this);
	}


	public int getHauteurFenetre() { return this.ihm.getHeight() ;}
	public int getLargeurFenetre() { return this.ihm.getWidth()  ;}


	public int           getNumJoueur()           { return this.metier.getNumJoueur()        ;}
	public char          getJoueurCouleur(int num){ return this.metier.getJoueurCouleur(num) ;}
	public String        getGrille   ()           { return this.metier.toString()            ;}
	public Container[][] getTabContainer()        { return metier.getTabContainer()          ;}
	public Lock[][]      getTabLock()             { return metier.getTabLock()               ;}
	public int           getJoueurScore(int num)  { return metier.getJoueurScore(num)        ;}
	public int           getJoueurNbLock(int num) { return metier.getJoueurNbLock(num)       ;}
	public String        getJoueurNom(int num)    { return metier.getJoueurNom(num)          ;}
	public int           getNumGagnant()          { return metier.getNumGagnant()            ;}

	public void jouerTour(int lig, int col)
	{
		if(! metier.estFini())
		{
			this.metier.jouerTour(lig, col);
			this.ihm.majTour();
		}
	}

	public boolean placePrise(int lig, int col)
	{
		return metier.placePrise(lig, col);
	}

	public boolean estFini()
	{
		return metier.estFini();
	}

	public void AffichageDeFin()
	{
		if(metier.getNumGagnant() == 1)
			JOptionPane.showMessageDialog(ihm, "Fin de partie", "LE JOUEUR 1 GAGNE ! BRAVO !", JOptionPane.PLAIN_MESSAGE);
		else if(metier.getNumGagnant() == 2)
			JOptionPane.showMessageDialog(ihm, "Fin de partie", "LE JOUEUR 2 GAGNE ! BRAVO !", JOptionPane.PLAIN_MESSAGE);
		else if(metier.getNumGagnant() == 0)
			JOptionPane.showMessageDialog(ihm, "Fin de partie", "ÉGALITÉ PERSONNE NE GAGNE !", JOptionPane.PLAIN_MESSAGE);
		else
			JOptionPane.showMessageDialog(ihm, "ERREUR", "Erreur lors de l'affichage du resultat", JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}

	public void calculerScore()
	{
		this.metier.calculerScore();
	}

	public static void main(String[] args)
	{
		new Controleur();
	}
}