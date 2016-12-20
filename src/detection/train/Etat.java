package detection.train;

public class Etat {
	public int val;
	public int tolerance;
	public int proba; // sert lors de la recuperation des etats probable
	public int nb;

	public Etat(int val, int tolerance) {
		super();
		this.val = val;
		this.tolerance = tolerance;
		this.nb = 1;
	}

}
