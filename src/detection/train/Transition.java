package detection.train;

import java.util.ArrayList;


public class Transition {
	public static ArrayList<Transition> transition;
	public static int num=0;
	public int id;
	public Etat etatDebut;
	public Etat etatFin;
	public int nombre;
	
	
	public Transition(Etat etatDebut, Etat etatFin) {
		super();
		this.etatDebut = etatDebut;
		this.etatFin = etatFin;
		this.id = num;
		this.nombre = 1;
		num++;
	}

	
	public int transitionExist(Etat etatDebut, Etat etatFin){
		for(Transition t:transition){
			if(t.etatDebut.Equal(etatDebut) && t.etatFin.Equal(etatFin))
				return t.id;
		}
		return -1;
	}
	public void updateTransition(Etat etatDebut, Etat etatFin){
		int statut = transitionExist(etatDebut,etatFin);
		switch (statut) {
		case -1:
			transition.add(new Transition(etatDebut, etatFin));
			break;

		default:
			transition.get(statut).nombre++;
			break;
		}
		
	}
	
	
}
