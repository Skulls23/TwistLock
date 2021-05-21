package twistLock.ihm;

import javax.swing.*;

import twistLock.controleur.Controleur;

public class IHMGui extends JFrame
{

	private PanelPrincipal panelPrinc;
	private PanelJoueur panelJoueur1;
	private PanelJoueur    panelJoueur2;
	private Controleur     ctrl;


	public IHMGui(Controleur ctrl)
	{
		this.ctrl = ctrl;

		this.panelPrinc   = new PanelPrincipal(ctrl);

		this.panelJoueur1 = new PanelJoueur(ctrl, 0);
		this.panelJoueur2 = new PanelJoueur(ctrl, 1);

		this.add(this.panelJoueur1, "West");
		this.add(this.panelPrinc);
		this.add(this.panelJoueur2, "East");

		this.setTitle("Twist Lock");
		this.setSize(1200, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	public void majTour()
	{
		this.panelJoueur1.majTour();
		this.panelJoueur2.majTour();
	}
}