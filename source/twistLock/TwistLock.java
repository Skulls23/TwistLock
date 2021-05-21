package twistLock;

import twistLock.controleur.Controleur;

import twistLock.metier.Container;
import twistLock.metier.Lock;
import twistLock.metier.Joueur;

import java.util.Scanner;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TwistLock
{
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	private static   Container[][]  tabContainer;
	
	private Lock[][] tabLock;
	private Joueur[] tabJoueur;
	private int      nbTour;
	
	public TwistLock(int nbCaseLig, int nbCaseCol)
	{
		this.initAll(nbCaseLig, nbCaseCol);
		this.lierLockCont();
		
		this.tabJoueur = new Joueur[2];
		
		this.tabJoueur[0] = new Joueur("| Joueur 1 |", 'R');
		this.tabJoueur[1] = new Joueur("| Joueur 2 |",   'V');
		
		this.nbTour = 0;
		
	}
	
	private void setLock(int lig, int col, char car )
	{
		if ( Controleur.DEBUG ) System.out.println(lig + ":" + col);
		
		if ( lig >= 0 && lig < this.tabLock.length    &&
		     col >= 0 && col < this.tabLock[0].length && this.tabLock[lig][col].getPossede() == ' ' )
		{
			this.tabLock[lig][col].setPossede(car);
			if ( Controleur.DEBUG ) System.out.println("done");
		}
	}
	
	public Container[][] getTabContainer()    { return this.tabContainer ;}
	public Lock[][]      getTabLock()         { return this.tabLock      ;}
	public int           getNumJoueur()       { return this.nbTour%2 + 1 ;}
	public int getJoueurScore(int nJoueur)    { return this.tabJoueur[nJoueur].getScore()  ;}
	public int getJoueurNbLock(int nJoueur)   { return this.tabJoueur[nJoueur].getNbLock() ;}
	public char getJoueurCouleur(int nJoueur) { return this.tabJoueur[nJoueur].getCouleur();}
	public String getJoueurNom(int nJoueur)   { return this.tabJoueur[nJoueur].getNom()    ;}
	public boolean estFini() { return this.tabJoueur[0].aFini() && this.tabJoueur[1].aFini() ;}
	
	public int getNumGagnant()
	{
		if(this.estFini())
		{
			if(this.getJoueurScore(0) > this.getJoueurScore(1)) return 0;
			if(this.getJoueurScore(0) < this.getJoueurScore(1)) return 1;
		}
		
		return -1;
	}

	public void calculerScore()
	{
		for ( int cpt = 0; cpt < this.tabJoueur.length; cpt++)
		{
			int score=0;
			
			for ( int cptLig=0; cptLig < this.tabContainer.length; cptLig++ )
				for ( int cptCol=0; cptCol < this.tabContainer[0].length; cptCol++ )
					if ( this.tabContainer[cptLig][cptCol].getCouleur() == this.tabJoueur[cpt].getCouleur() )
						score += this.tabContainer[cptLig][cptCol].getPts() ;
			
			this.tabJoueur[cpt].setScore(score);
		}
	}
	
	public void jouerTour(int lig, int col)
	{
		this.setLock(lig, col, this.tabJoueur[this.nbTour%2].getCouleur());
		this.tabJoueur[this.nbTour%2].joue();
		this.nbTour++;
	}
	
	public void initAll(int lig, int col)
	{
		this.initContainer(lig, col);
		this.initLock();
	}
	
	private void initContainer(int lig, int col)
	{
		this.tabContainer = new Container[lig][col];
		
		for ( int cptLig = 0; cptLig < this.tabContainer.length; cptLig++ )
			for ( int cptCol = 0; cptCol < this.tabContainer[0].length; cptCol++ )
				this.tabContainer[cptLig][cptCol] = new Container();
	}
	
	private void initLock()
	{
		this.tabLock = new Lock[this.tabContainer.length+1][this.tabContainer[0].length+1];
		
		if ( Controleur.DEBUG ) System.out.println("tabLock = " + (this.tabContainer.length+1) + ":" + (this.tabContainer[0].length+1) );
		
		for ( int cptLig = 0; cptLig < this.tabLock.length; cptLig++ )
			for ( int cptCol = 0; cptCol < this.tabLock[0].length; cptCol++ )
				this.tabLock[cptLig][cptCol] = new Lock();
	}
	
	private void lierLockCont()
	{
		for ( int cptLig = 0; cptLig < this.tabLock.length; cptLig++)
		{
			for ( int cptCol = 0; cptCol < this.tabLock[0].length; cptCol++)
			{
				if ( ( cptLig == 0 || cptLig == this.tabLock.length-1    ) &&
				     ( cptCol == 0 || cptCol == this.tabLock[0].length-1 )    )
				{
					this.lierExtremite(cptLig, cptCol);
				}
				else if ( cptLig == 0 || cptLig == this.tabLock.length-1    ||
				          cptCol == 0 || cptCol == this.tabLock[0].length-1    )
				{
					this.lierCote(cptLig, cptCol);
				}
				else
				{
					this.tabLock[cptLig][cptCol].setTabCont( new Container[] 
					{ this.tabContainer[cptLig-1][cptCol-1],
					  this.tabContainer[cptLig][cptCol-1],
					  this.tabContainer[cptLig-1][cptCol],
					  this.tabContainer[cptLig][cptCol]  } );
				}
			}
		}
		
	}
	
	private void lierExtremite(int lig, int col)
	{
		if ( lig == 0 && col == this.tabLock[0].length-1 ) 
			this.tabLock[lig][col].setTabCont( new Container[] {this.tabContainer[0][this.tabContainer[0].length-1] } );
		
		if ( lig == 0 && col == 0 ) 
			this.tabLock[lig][col].setTabCont( new Container[] {this.tabContainer[0][0] } );
		
		if ( lig == this.tabLock.length-1 && col == this.tabLock[0].length-1 ) 
			this.tabLock[lig][col].setTabCont( new Container[] {this.tabContainer[this.tabContainer.length-1][this.tabContainer[0].length-1] } );
		
		if ( lig == this.tabLock.length-1 && col == 0 ) 
			this.tabLock[lig][col].setTabCont( new Container[] {this.tabContainer[this.tabContainer.length-1][0] } );
	}
	
	private void lierCote(int lig, int col)
	{
		if ( lig == 0 )
			this.tabLock[lig][col].setTabCont( new Container[] 
			{ this.tabContainer[0][col-1], 
			  this.tabContainer[0][col]    } ) ;
		
		if ( lig == this.tabLock.length-1 )
			this.tabLock[lig][col].setTabCont( new Container[] 
			{ this.tabContainer[this.tabContainer.length-1][col-1], 
			  this.tabContainer[this.tabContainer.length-1][col]    } ) ;
		
		if ( col == 0 )
			this.tabLock[lig][col].setTabCont( new Container[] 
			{ this.tabContainer[lig-1][0], 
			  this.tabContainer[lig][0]    } ) ;
		
		if ( col == this.tabLock[0].length-1 )
			this.tabLock[lig][col].setTabCont( new Container[] 
			{ this.tabContainer[lig-1][this.tabContainer[0].length-1], 
			  this.tabContainer[lig][this.tabContainer[0].length-1]    } ) ;
	}
	
	public String toString()
	{
		String s="";
		
		s += "     ";
		for ( int col=0; col < this.tabLock[0].length; col++ )
		{
			s += ANSI_CYAN + ":" + (char)('A'+col) + ":  " + ANSI_RESET;
		}
		s += "\n";
		
		for (int lig=0; lig < this.tabLock.length-1; lig++ )
		{
			s += ANSI_CYAN + ":" + String.format("%2d", lig ) + ": " + ANSI_RESET;
			
			for ( int col=0; col < this.tabLock[0].length; col++ )
			{
				s += this.tabLock[lig][col] + "  ";
			}
			
			if ( lig == 3 )
			{
				s += ANSI_RED + "\t Joueur 1 : " + this.tabJoueur[0].getScore();
				s += "    Nombre de Lock : " + this.tabJoueur[0].getNbLock() + ANSI_RESET;
			}
			if ( lig == 5 )
			{
				s += ANSI_GREEN + "\t Joueur 2 : " + this.tabJoueur[1].getScore();
				s += "    Nombre de Lock : " + this.tabJoueur[1].getNbLock() + ANSI_RESET;
			}
		
			
			s += "\n     ";
			for ( int col=0; col < this.tabContainer[0].length && lig < this.tabContainer.length; col++ )
			{
				s += "   " + this.tabContainer[lig][col];
				
			}
			s += "\n";
		}
		
		s += ANSI_CYAN + ":" + String.format("%2d", this.tabLock.length-1 ) + ": " + ANSI_RESET;
		for ( int col=0; col < this.tabLock[0].length; col++ )
		{
			s += this.tabLock[this.tabLock.length-1][col];
			s += "  ";
		}
		
		return s;
	}
	
}
