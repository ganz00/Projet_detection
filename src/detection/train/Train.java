package detection.train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import detection.Bd.SGBD;
import detection.service.Etat;

public class Train {
	public ArrayList<Etat> etats;
	public SGBD sg;
	
	public Train() {
		super();
		this.etats = new ArrayList<Etat>();
		this.sg = new SGBD();
	}
	
	public void GetEtat(){
		//determiner les etats
		int nbCols=0;
		boolean encore = false;
		ResultSet resultats = null;
		try {
			resultats = sg.consoParHeureParJour();
			java.sql.ResultSetMetaData rsmd = resultats.getMetaData();
			nbCols = rsmd.getColumnCount();
			encore = resultats.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	public void enregistrerEtat() throws SQLException{
		
		for (Etat etat : etats) {
			sg.enregistrerEtat(etat.id, etat.val, etat.tolerance);
		}
	}
}
