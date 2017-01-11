package detection.Bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import detection.Datasource.DataSourceSingleConnection;

public class DetectionDao {
	public DataSourceSingleConnection dataSource;
	public static DetectionDao  dao=null;
	
	
	//constructeur privé
	private  DetectionDao() {
		super();
		this.dataSource = new DataSourceSingleConnection();
		this.dataSource.setDriver("com.mysql.jdbc.Driver");
		this.dataSource.setUrl("jdbc:mysql://localhost:8889/Simulateur");
		this.dataSource.setUser("root");
		this.dataSource.setPassword("root");
		
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
public static double consoTotal(Heure h, Date d) throws SQLException
{
	double resultats = 0;
	ResultSet resultats1=null;
	Connection con = dataSource.getConnection();
	java.sql.Statement stmt = con.createStatement();
	String requete = "SELECT SUM(consommation.consommation) as consoTotal"+
			"FROM consommation  where consommation.heure ="+h+"and consommation.jour = "+d.jour+"and consommation ="+d.mois;
	try{
	resultats= stmt.executeQuery(requete).getDouble("consoTotal");
	
	}
	catch(SQLException e){
		System.out.println(e);
	}
	return resultats ;
	
}
	public ResultSet consoParHeureParJour(int periode,int saison) throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT consommation.jour,consommation.heure,SUM(consommation.consomation) as conso "
				+ 			"FROM consommation ";
		if(periode == 1){
		requete = requete+" WHERE ((heure >= 0 and heure <= 18) or heure = 22 or heure = 23) and consommation.saison ="+saison;
				
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
	
	
	public int getconsocour(int heure) {
		return 0;
		//recuperer la consommation courante de la base
			}
	
	
}
