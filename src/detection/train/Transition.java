package detection.train;

public class Transition {
	public static int num=0;
	public int id;
	public int etatDebut;
	public int etatFin;
	public int nombre;
	public int saison;
	public int periode;
	public double proba;
	
	
	public Transition(int etatDebut, int etatFin,int periode) {
		super();
		this.etatDebut = etatDebut;
		this.etatFin = etatFin;
		this.id = num;
		this.periode = periode;
		this.nombre = 1;
	}
	public Transition(int etatDebut, int etatFin,int periode, int nombre,double proba) {
		super();
		this.etatDebut = etatDebut;
		this.etatFin = etatFin;
		this.nombre = nombre;
		 this.periode = periode;
		this.proba = proba;
		this.id = num;
	}	
	
}
