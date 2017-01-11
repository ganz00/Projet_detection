package detection.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import detection.Bd.DetectionDao;
import detection.Bd.TransitionDao;
import detection.train.*;

public class ModeleMarkov {
	public ArrayList<Transition> transition;
	public ArrayList<Transition> B;
	public int nombreEtat;
	public int periode;

	
public ModeleMarkov(ArrayList<Etat> etats,ArrayList<Transition> t, int periode) {
		super();
		this.nombreEtat = etats.size();	
		this.transition = t;
		this.periode = periode;
		B = new ArrayList<Transition>();
	}



public void setB() throws SQLException{
	//contruire la matrice de transition B a l'aide du nombre d'etat et la matrice transition
	TransitionDao Td = TransitionDao.getsdao();
	for(Transition T : transition){
		if(T.periode == periode){
		int total = Td.getNb(T.etatDebut,T.periode,T.saison);
		double p = (double)T.nombre/(double)total;
		Transition t = new Transition(T.etatDebut, T.etatFin, T.periode, T.nombre, p);
		B.add(t);
		T.proba = p;
		Td.saveProba(T.etatDebut,T.etatFin,p,T.saison,T.periode);
		}
	}
	
}
public void majmatrice(){
	//meta jours la matrice en fonction des etats et de la matrice de transition
}

public void editTransition(int id,int etat1,int etat2){
	//rajouter un transition dans la matrice transition en fonction de la saison id
}

public ArrayList<EtatProbable> getnext(Etat E,int p,int s) throws SQLException{
	ResultSet resultats = TransitionDao.getsdao().getNext(E.id, p, s);
	ArrayList<EtatProbable> etats = new ArrayList<EtatProbable>();
	while(resultats.next()){
		etats.add(new EtatProbable(resultats.getInt(1), resultats.getInt(2)));
	}
	return etats;
	
}

}
