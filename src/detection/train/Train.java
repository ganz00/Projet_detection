package detection.train;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import detection.Bd.SGBD;

public class Train {
	public ArrayList<Etat> etats;
	public int T = 100;
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
			while(resultats.next()){
				int dispo = etatExist(resultats.getInt(3));
				int val = resultats.getInt(3);
				System.out.println(val);
				switch (dispo) {
				case -1:
					etats.add(new Etat(val, T));
					break;

				default:
					etats.get(dispo).nb++;
					break;
				}
			}
			enregistrerEtat();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public int etatExist(int e){
		int i=0;
		for(Etat E:etats){
			if(E.val + E.tolerance >= e && E.val - E.tolerance <=e ){
				return i;
			}
			i++;
		}
		return -1;
	}
	


	public void enregistrerEtat() throws SQLException{
		
		for (Etat etat : etats) {
			sg.enregistrerEtat(etat.val, etat.tolerance,etat.nb);
		}
	}
}
