package detection.train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import detection.Bd.DetectionDao;


public class Etat {
	public static  ArrayList<Etat> etats;
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
	
	public void setlist() throws SQLException{
		ResultSet resultats = DetectionDao.getsdao().getEtat();
		while(resultats.next()){
			etats.add(new Etat(resultats.getInt(1), 
					resultats.getInt(2), resultats.getInt(3), resultats.getInt(4)));
		}
	}

}
