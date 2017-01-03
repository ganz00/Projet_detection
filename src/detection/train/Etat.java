package detection.train;



public class Etat {
	public int id;
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
	
	public Etat(int id, int val, int tolerance, int nb) {
		super();
		this.id = id;
		this.val = val;
		this.tolerance = tolerance;
		this.nb = nb;
	}

	public boolean Equal(Etat e) {
		if(this.val == e.val)
			return true;
		return false;
	}
	

}
