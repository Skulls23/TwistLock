package twistLock.metier;

public class Container
{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	private final int PTS;
	
	private int   valeur; // -4 à 4
	
	public Container()
	{
		this.PTS    = (int) (Math.random()*50)+5;
		this.valeur = 0;
	}
	
	public int getPts() { return this.PTS ;}
	
	public char getCouleur()
	{
		if ( valeur <  0 ) return 'R';
		if ( valeur >  0 ) return 'V';
		
		return ' ';
	}
	
	public boolean addValeur(int val) 
	{
		if ( this.valeur + val >= -4 && this.valeur + val <= 4 )
		{
			this.valeur += val ;
			return true;
		}
		
		return false;
	}
	
	public String toString()
	{
		if ( valeur <  0 ) return "" + ANSI_RED + String.format("%02d", this.PTS) + ANSI_RESET;
		if ( valeur >  0 ) return "" + ANSI_GREEN + String.format("%02d", this.PTS) + ANSI_RESET;
		
		return String.format("%02d", this.PTS);
	}
	
}