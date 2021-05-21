package twistLock.ihm;

import twistLock.controleur.Controleur;

import java.util.Scanner;

public class IHMCui
{

	private final Controleur ctrl;

	public IHMCui(Controleur ctrl)
	{
		this.ctrl = ctrl;
	}

	public void afficher()
	{
		Scanner sc;
		int     lig;
		char    col;

		System.out.println(this.ctrl.getGrille());
			
		System.out.println();
		
		sc = new Scanner(System.in);
		
		System.out.print( "Joueur " + this.ctrl.getNumJoueur() + " choisissez une ligne   : " );
		lig = sc.nextInt(); sc.nextLine();
		System.out.print( "Joueur " + this.ctrl.getNumJoueur() + " choisissez une colonne : " );
		col = sc.nextLine().charAt(0);
		
		if ( Controleur.DEBUG ) System.out.println( lig + ":" + col);
		
		this.ctrl.jouerTour(lig, col - (int)('A'));
		this.ctrl.calculerScore();
		
		System.out.println(this.ctrl.getGrille());
		
		sc.nextLine();
	}
}