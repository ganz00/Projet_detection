package detection.service;

public class Etat {
	public int id;
	public int val;
	public int tolerance;
	public int proba; // sert lors de la recuperation des etats probable

	public Etat(int id, int val, int tolerance) {
		super();
		this.id = id;
		this.val = val;
		this.tolerance = tolerance;
	}

}
