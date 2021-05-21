package twistLock.ihm;

import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.FontMetrics;

import java.awt.event.*;

import twistLock.controleur.Controleur;
import twistLock.metier.Container;
import twistLock.metier.Lock;

import javax.imageio.ImageIO;
import java.io.File;


public class PanelPrincipal extends JPanel implements MouseListener
{
	private static final int    TAILLE_LOCK     = 10;
	private static final double ECHELLE_POSSEDE =  0.5;

	private Image         conteneurImage;
	private final Lock[][]      locks;
	private final Container[][] containers;
	private final Controleur    ctrl;
	private final int           hauteurGrille;
	private final int           largeurGrille;
	private int           xPosGrille;
	private int           yPosGrille;

	public PanelPrincipal(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.locks      = this.ctrl.getTabLock();
		this.containers = this.ctrl.getTabContainer();

		this.addMouseListener(this);
		
		try{
			this.conteneurImage = ImageIO.read(new File("source/twistLock/images/conteneur.gif"));
		} catch(Exception e)
		{
			e.printStackTrace();
		}

		this.hauteurGrille = conteneurImage.getHeight(null) * this.containers.length;
		this.largeurGrille = conteneurImage.getWidth (null) * this.containers[0].length;
	}

	public void paintComponent(Graphics g) 
	{
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		this.xPosGrille = (this.ctrl.getLargeurFenetre() - this.largeurGrille) / 4;
		this.yPosGrille = (this.ctrl.getHauteurFenetre() - this.hauteurGrille) / 4;
	    this.renduConteneurs(g);
	    this.renduLocks(g);
	}

	public void mouseClicked(MouseEvent e)
	{
		this.jouerLockClique(e.getX(), e.getY());
		this.repaint();
	}

	public void	mouseEntered (MouseEvent e){}
	public void	mouseExited  (MouseEvent e){}
	public void	mousePressed (MouseEvent e){}
	public void	mouseReleased(MouseEvent e){}


	private void renduConteneurs(Graphics g)
	{
		Rectangle rectConteneur;
		int       xPos, yPos;
		String    stringPoints;

		for(int lig = 0; lig < this.containers.length; lig++)
		{
			for(int col = 0; col < this.containers[lig].length; col++)
			{
				xPos = col * conteneurImage.getWidth (null) + this.xPosGrille;
				yPos = lig * conteneurImage.getHeight(null) + this.yPosGrille;

				rectConteneur = new Rectangle(xPos, yPos, conteneurImage.getWidth(null), conteneurImage.getHeight(null));

				stringPoints = "" + this.containers[lig][col].getPts();

				g.drawImage(conteneurImage, xPos, yPos, null, null);
				this.dessinerConteneurPossede(g, this.containers[lig][col].getCouleur(), xPos, yPos);

				g.setColor(Color.BLACK);
				this.dessinerStringCentrer(g, stringPoints, rectConteneur);
			}
		}
	}

	private void renduLocks(Graphics g)
	{

		int xPos, yPos;

		for(int lig = 0; lig < this.locks.length; lig++)
		{
			for(int col = 0; col < this.locks[lig].length; col++)
			{
				if(this.locks[lig][col].getPossede() == 'R')
					g.setColor(Color.RED);
				else if(this.locks[lig][col].getPossede() == 'V')
					g.setColor(Color.GREEN);
				else
					g.setColor(Color.WHITE);

				xPos = col * conteneurImage.getWidth (null) - TAILLE_LOCK / 2 + this.xPosGrille;
				yPos = lig * conteneurImage.getHeight(null) - TAILLE_LOCK / 2 + this.yPosGrille;

				g.fillOval(xPos, yPos, TAILLE_LOCK, TAILLE_LOCK);
			}
		}
	}

	private void jouerLockClique(int sourisX, int sourisY)
	{
		sourisX += TAILLE_LOCK / 2 - this.xPosGrille;
		sourisY += TAILLE_LOCK / 2 - this.yPosGrille;

		int xModuloConteneur = sourisX % conteneurImage.getWidth (null);
		int yModuloConteneur = sourisY % conteneurImage.getHeight(null);

		int xSourisLock      = sourisX / conteneurImage.getWidth (null);
		int ySourisLock      = sourisY / conteneurImage.getHeight(null);

		if(xModuloConteneur <= TAILLE_LOCK && yModuloConteneur <= TAILLE_LOCK)
		{
			this.ctrl.calculerScore();
			if(! ctrl.placePrise(ySourisLock, xSourisLock))
				this.ctrl.jouerTour(ySourisLock, xSourisLock);
		}
	}

	private void dessinerConteneurPossede(Graphics g, char coul, int xPos, int yPos)
	{
		int larg = (int) (this.conteneurImage.getWidth (null) * ECHELLE_POSSEDE);
		int haut = (int) (this.conteneurImage.getHeight(null) * ECHELLE_POSSEDE);

		if(coul == 'V')
			g.setColor(Color.GREEN);
		else if(coul == 'R')
			g.setColor(Color.RED);
		else
			g.setColor(Color.WHITE);


		g.fillOval(xPos + larg / 2, yPos + haut / 2, larg, haut);
	}

	private void dessinerStringCentrer(Graphics g, String texte, Rectangle rect) {
		
		FontMetrics metrics = g.getFontMetrics();

		int x = rect.x + (rect.width - metrics.stringWidth(texte)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		
		g.drawString(texte, x, y);
	}
}
