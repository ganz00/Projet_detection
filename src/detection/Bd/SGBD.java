package detection.Bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import detection.Datasource.DataSourceSingleConnection;

public class SGBD {
	public DataSourceSingleConnection dataSource;

	public SGBD() {
		super();
		this.dataSource = new DataSourceSingleConnection();
		this.dataSource.setDriver("com.mysql.jdbc.Driver");
		this.dataSource.setUrl("jdbc:mysql://localhost:8889/Simulateur");
		this.dataSource.setUser("root");
		this.dataSource.setPassword("root");
	}

	public ResultSet consoParHeureParJour() throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT consommation.jour,consommation.heure,SUM(consommation.consomation) "
				+ 			"FROM consommation GROUP BY consommation.jour,consommation.heure;";
		try {
			resultats = stmt.executeQuery(requete);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	return resultats;	
	}

	public void enregistrerEtat(int id,int val,int tolerance) throws SQLException {
		Connection con = dataSource.getConnection();
		Statement sta = con.createStatement();
		String query = " insert into Etat (id, val, tolerance)"
				        + " values (?, ?, ?)";

				      // Donn�es � ins�rer dans la base de donn�es
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setInt(1, id);
				      preparedStmt.setInt(2, val);
				      preparedStmt.setInt (3, tolerance);
				      // Ex�cution de la requ�te
				      preparedStmt.execute();
		
	}

	public void getEtat() {
//recuperer les etats de la base
	}
	
	
	public int getconsocour() {
		return 0;
		//recuperer la consommation courante de la base
			}
}
