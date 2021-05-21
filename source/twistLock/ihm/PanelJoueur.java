package twistLock.ihm;

import javax.swing.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Dimension;

import twistLock.controleur.Controleur;

import java.awt.Color;

import javax.imageio.ImageIO;
import java.io.File;

public class PanelJoueur extends JPanel
{

	private static final int TAILLE_LOCK_RESTANT = 10;

	private final Controleur ctrl;
	private final int        numJoueur;
	private String     couleurStr;
	private Color      couleurGraphic;
	private Image      phareImage;
	private Image      feuImage;
	private boolean    gagnant;

	public PanelJoueur(Controleur ctrl, int numJoueur)
	{
		this.ctrl      = ctrl;
		this.numJoueur = numJoueur;

		switch(this.ctrl.getJoueurCouleur(numJoueur))
		{
			case 'R': this.couleurStr     = "Rouge";
			          this.couleurGraphic = Color.RED;   break;
			case 'V': this.couleurStr     = "Vert" ;
			          this.couleurGraphic = Color.GREEN; break;
		}

		try{
			this.phareImage = ImageIO.read(new File("source/twistLock/images/Phare" + this.couleurStr + ".gif"));
			this.feuImage   = ImageIO.read(new File("source/twistLock/images/Feu" + this.couleurStr + this.getActif() + ".gif"));
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		this.setPreferredSize(new Dimension(this.phareImage.getWidth(null) + 50, 100));
	}

	public void paintComponent(Graphics g) 
	{
		//Il faut tout effacer avant de repeindre pour éviter les superpositions
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(this.couleurGraphic);
	    
	    this.dessinerPhare(g);
	    this.dessinerFeu(g);
	    this.ecrireNomScore(g);
	    this.dessinerLockRestants(g);
	}

	public void majTour()
	{
		try{
			this.feuImage = ImageIO.read(new File("source/twistLock/images/Feu" + this.couleurStr + this.getActif() + ".gif"));
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		this.gagnant = this.ctrl.getNumGagnant() == this.numJoueur;

		this.repaint();
	}

	private void dessinerPhare(Graphics g)
	{
		int yCentre = this.getHeight() / 2 - this.phareImage.getHeight(null) / 2;

		g.drawImage(this.phareImage, 0, yCentre, null, null);
	}

	private void dessinerFeu(Graphics g)
	{
		int yPos = this.getHeight() / 2 - this.phareImage.getHeight(null);

		g.drawImage(this.feuImage, 0, yPos, null, null);
	}

	private void ecrireNomScore(Graphics g)
	{
		int yPos = this.getHeight() / 2 + this.phareImage.getHeight(null) / 2 + g.getFontMetrics().getHeight();

		String texte = this.ctrl.getJoueurNom(this.numJoueur) + " " + this.ctrl.getJoueurScore(this.numJoueur);
		
		g.drawString(texte, 0, yPos);
	}

	private void dessinerLockRestants(Graphics g)
	{
		int lockRestant = this.ctrl.getJoueurNbLock(this.numJoueur);
		int yPos = this.getHeight() / 2 + this.phareImage.getHeight(null) / 2 + g.getFontMetrics().getHeight() * 2;

		for(int i = 0; i < lockRestant; i++)
		{
			g.fillOval((i % 10) * TAILLE_LOCK_RESTANT, (i / 10) * TAILLE_LOCK_RESTANT + yPos, TAILLE_LOCK_RESTANT, TAILLE_LOCK_RESTANT);
		}
	}

	private String getActif()
	{
		if(this.ctrl.getNumJoueur() - 1 == this.numJoueur) return "Actif";
		else                                               return "Inactif";
	}
}