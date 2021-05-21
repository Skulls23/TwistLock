package twistLock.metier;

public class Joueur
{
	private String nom;
	private int    score;
	private char   couleur;
	private int    nbLock;
	
	public Joueur(String nom, char couleur)
	{
		this.nom     = nom;
		this.couleur = couleur;
		this.nbLock  = 20;
		this.score   = 0;
	}
	
	public void setScore(int score) { this.score = score ;}
	
	public char   getCouleur() { return this.couleur     ;}
	public int    getNbLock()  { return this.nbLock      ;}
	public int    getScore()   { return this.score       ;}
	public String getNom()     { return this.nom         ;}
	public boolean aFini()     { return this.nbLock <= 0 ;}
	
	public void joue() { this.nbLock-- ;}
	
	public String toString()
	{
		String s="";
		
		s += "" + this.couleur + "  score : " + this.score;
		
		return s;
	}
}
