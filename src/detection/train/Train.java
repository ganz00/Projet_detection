package detection.train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import detection.Bd.DetectionDao;
import detection.Bd.TransitionDao;
import detection.service.ModeleMarkov;

public class Train {
	public ArrayList<Etat> etats[];
	public ArrayList<Transition> transition;
	public DetectionDao dao;
	public ModeleMarkov M[];
	public int saison;
	public int T = 100;
	int num = 0;
	@SuppressWarnings("unchecked")
	public Train(int saison)  {
		super();
		this.etats = new ArrayList[2];
		this.transition = new ArrayList<Transition>();
		this.dao = DetectionDao.getsdao();
		this.saison = saison;
		
	}
	public void Init() throws SQLException{
		for(int i = 0 ; i < 2 ; i++) 
			etats[i] = new ArrayList<Etat>();
		
		getEtat(1);
		getEtat(2);
		for(Transition T:transition)
			T.saison = saison;
		//saveTransitions();
		M = new ModeleMarkov[2];
		M[0] = new ModeleMarkov(etats[0], transition,1);
		M[0].setB();
		M[1] = new ModeleMarkov(etats[1], transition,2);
		M[1].setB();
	}

	public void enregistrerEtat(int periode) throws SQLException{
		
		for (Etat etat : etats[periode-1]) {
			dao.enregistrerEtat(etat.val, etat.tolerance,etat.nb, saison,periode);
		}
	}
	
	// GESTION ETATS //
	
	
	public void getEtat(int periode){
		//determiner les etats
		ResultSet resultats = null;
		try {
			resultats = dao.consoParHeureParJour(periode, saison);
			while(resultats.next()){
				int dispo = etatExist(resultats.getInt(3),periode);
				int val = resultats.getInt(3);
				switch (dispo) {
				case -1:
					etats[periode-1].add(new Etat(val, T));
					break;

				default:
					etats[periode-1].get(dispo).nb++;
					break;
				}
			}
			//enregistrerEtat(periode);
			etats[periode-1] = this.getlist(periode);
			resultats.first();
			getTransition(resultats,periode);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public  ArrayList<Etat> getlist(int periode) throws SQLException{
		ResultSet resultats = DetectionDao.getsdao().getEtat(saison,periode);
		ArrayList<Etat> etats = new ArrayList<Etat>();
		while(resultats.next()){
			etats.add(new Etat(resultats.getInt(1), 
					resultats.getInt(4), resultats.getInt(5), resultats.getInt(6)));
		}
		return etats;
	}
	
	
	// Autres methodes
	public  int getId(int val,int periode){
		for(Etat E:etats[periode-1]){
			if(E.val == val)
				return E.id;
		}
		return -1;
	}
	public Etat getEtatById(int id,int periode){
		for(Etat E:etats[periode-1]){
			if(E.id == id)
				return E;
		}
		return null;
		
	}
	public int etatExist(int e,int periode ){
		int i=0;
		for(Etat E:etats[periode-1]){
			if(E.val + E.tolerance >= e && E.val - E.tolerance <=e ){
				return i;
			}
			i++;
		}
		return -1;
	}

	//GESTION TRANSITION //
	
	public void getTransition(ResultSet resultats,int periode){
		Transition T = null;
		Transition.num = 0;
		int pos = 1;
		try {
			do{
			switch (pos) {
			case 1:
				T = new Transition(this.getId(resultats.getInt(3),periode), 0,periode);
				pos++;
				break;

			default:
				T.etatFin = this.getId(resultats.getInt(3),periode);
				resultats.previous();
				pos = 1;
				updateTransition(T,periode);
				break;
			}	
			}while(resultats.next());
			resultats.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	
	public  void saveTransitions() throws SQLException{
		for(Transition t:transition){
			TransitionDao.getsdao().savetransition(t);
		}
	}

	
	public  int transitionExist(int etatDebut, int etatFin, int periode){
		for(Transition t:transition){
			if(t.etatDebut == etatDebut && t.etatFin == etatFin && t.periode == periode)
				return t.id;
		}
		return -1;
	}
	
	public  void updateTransition(Transition T,int periode){
		int statut = transitionExist(T.etatDebut,T.etatFin,T.periode);
		switch (statut) {
		case -1:
			transition.add(new Transition(T.etatDebut, T.etatFin,periode));
			Transition.num ++;
			break;

		default:
			transition.get(statut).nombre++;
			break;
		}
		
	}
	
	public  void importTransition(){
		//determiner les etats
		ResultSet resultats = null;
		transition.clear();
		try {
			resultats = TransitionDao.getsdao().getTransiton(saison);
			num=1;
			while(resultats.next()){
				transition.add(new Transition(resultats.getInt(1),resultats.getInt(2),resultats.getInt(4),resultats.getInt(5),resultats.getInt(6)));
				num++;
			}
			
			resultats.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
