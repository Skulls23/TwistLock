package twistLock.metier;

public class Lock
{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	
	
	private char        possede;
	private Container[] tabCont;

	
	public Lock()
	{
		this.possede = ' ';
	}
	
	public void setPossede(char car ) 
	{
		
		switch (this.possede)
		{
			case 'R' : 
				switch ( car )
				{
					case ' ' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur(1); break;
					case 'V' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur(2);
				}
				break;
			
			case 'V' :
				switch ( car )
				{
					case ' ' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur(-1); break;
					case 'R' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur(-2);
				}
				break;
				
			case ' ' :
				switch ( car )
				{
					case 'R' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur(-1); break;
					case 'V' : for ( int cpt=0; cpt < this.tabCont.length; cpt++) this.tabCont[cpt].addValeur( 1);
				}
		}
		
		this.possede = car ;
	}
	public void setTabCont(Container[] tab) { this.tabCont = tab ;}
	
	public char        getPossede() { return this.possede ;}
	public Container[] getTabCont() { return this.tabCont ;}
	
	public String toString()
	{
		switch ( this.possede )
		{
			case 'R' : return ANSI_RED + "[" + this.possede + "]" + ANSI_RESET;
			case 'V' : return ANSI_GREEN + "[" + this.possede + "]" + ANSI_RESET;
			default  : return "[" + this.possede + "]";
		}
	}
	
}
