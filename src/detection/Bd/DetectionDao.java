package detection.Bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import detection.Datasource.DataSourceSingleConnection;
import simulation.Date;
import simulation.Heure;

public class DetectionDao {
	public static DataSourceSingleConnection dataSource;
	public static DetectionDao  dao=null;
	
	
	//constructeur privé
	private  DetectionDao() {
		super();
		dataSource = new DataSourceSingleConnection();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:8889/Simulateur");
		dataSource.setUser("root");
		dataSource.setPassword("root");
		
	}
	//getter dao 
	public static DetectionDao getsdao(){
		if(dao == null){
			dao = new DetectionDao();
			return dao;
		}else
			return dao;
	}
	
// DAO consommation

	public ResultSet consoParHeureParJour(int periode,int saison) throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT consommation.jour,consommation.heure,SUM(consommation.consomation) as conso "
				+ 			"FROM consommation ";
		if(periode == 1){
		requete = requete+" WHERE ((heure >= 0 and heure <= 18) or heure = 22 or heure = 23) and consommation.mois = 1";
				
		}else{
			requete = requete+" WHERE heure > 18 and heure < 22 and consommation.saison ="+saison;	
		}
		requete = requete+" GROUP BY consommation.jour,consommation.heure";
		try {
			resultats = stmt.executeQuery(requete);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	return resultats;	
	}

	
	// DAO etat
	public void enregistrerEtat(int val,int tolerance,int occur,int saison,int periode) throws SQLException {
		Connection con = dataSource.getConnection();
		String query = " insert into Etat (saison,val,tolerance,occurence,periode)"
				        + " values (?, ?, ?,?,?)";

				      // Donn�es � ins�rer dans la base de donn�es
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setInt(1, saison);
				      preparedStmt.setInt(2, val);
				      preparedStmt.setInt(3, tolerance);
				      preparedStmt.setInt(4,occur);
				      preparedStmt.setInt(5,periode);

				      // Ex�cution de la requ�te
				      preparedStmt.execute();
	}

	public ResultSet getEtat(int saison,int periode) throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT * FROM Etat where saison ="+saison+" and periode="+periode;
		try {
			resultats = stmt.executeQuery(requete);
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return resultats;	

	}
	
	
	public static double getconsocour(int heure,int jour,int mois) throws SQLException {
		double resultats = 0;
	 	Connection con = dataSource.getConnection();
	 	java.sql.Statement stmt = con.createStatement();
	 	String requete = "SELECT SUM(consommation.consomation) as consoTotal"+
	 			" FROM consommation  where consommation.heure = "+heure+" and consommation.jour = "+jour+" and consommation.mois = "+mois;
	 	try{
	 	ResultSet res = stmt.executeQuery(requete);
	 	res.next();
	 	resultats = res.getDouble("consoTotal");
	 	}
	 	catch(SQLException e){
	 		System.out.println(e);
	 	}
	 	return resultats ;
	}
	
	public void saveErreur(int heure,int mois, int jour,int eccart) {
		//
	}
	
	
	
	
}
