package twistLock.controleur;

import twistLock.TwistLock;
import twistLock.ihm.IHMGui;

import twistLock.metier.Container;
import twistLock.metier.Lock;

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

		/*while(!this.metier.estFini())
		{
			this.ihm.afficher();
		}*/
	}

	/* A enlever si CUI */
	public int getHauteurFenetre() { return this.ihm.getHeight() ;}
	public int getLargeurFenetre() { return this.ihm.getWidth()  ;}
    /* ---------------- */

	public int           getNumJoueur()           { return this.metier.getNumJoueur()        ;}
	public char          getJoueurCouleur(int num){ return this.metier.getJoueurCouleur(num) ;}
	public String        getGrille   ()           { return this.metier.toString()            ;}
	public Container[][] getTabContainer()        { return metier.getTabContainer()          ;}
	public Lock[][]      getTabLock()             { return metier.getTabLock()               ;}
	public int           getJoueurScore(int num)  { return metier.getJoueurScore(num)        ;}
	public int           getJoueurNbLock(int num) { return metier.getJoueurNbLock(num)       ;}
	public String        getJoueurNom(int num)    { return metier.getJoueurNom(num)          ;}
	public boolean       estFini()                { return metier.estFini()                  ;}
	public int           getNumGagnant()          { return metier.getNumGagnant()            ;}

	public void jouerTour(int lig, int col)
	{
		this.metier.jouerTour(lig, col);
		this.ihm.majTour();
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