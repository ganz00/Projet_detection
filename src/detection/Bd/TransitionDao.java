package detection.Bd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import detection.Datasource.DataSourceSingleConnection;
import detection.train.Transition;

public class TransitionDao {
	public DataSourceSingleConnection dataSource;
	public static TransitionDao  dao=null;
	
	
	//constructeur privé
	private  TransitionDao() {
		super();
		this.dataSource = new DataSourceSingleConnection();
		this.dataSource.setDriver("com.mysql.jdbc.Driver");
		this.dataSource.setUrl("jdbc:mysql://localhost:8889/Simulateur");
		this.dataSource.setUser("root");
		this.dataSource.setPassword("root");
		
	}
	//getter dao 
	public static TransitionDao getsdao(){
		if(dao == null){
			dao = new TransitionDao();
			return dao;
		}else
			return dao;
	}
	
//DAO transition
	public void savetransition(Transition T) throws SQLException{
		Connection con = dataSource.getConnection();
		String query = " insert into transition (debut,fin,saison,periode,nombre)"
				        + " values (?, ?, ?,?,?)";

				      // Donn�es � ins�rer dans la base de donn�es
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setInt(1, T.etatDebut);
				      preparedStmt.setInt (2, T.etatFin);
				      preparedStmt.setInt(3, T.saison);
				      preparedStmt.setInt (4, T.periode);
				      preparedStmt.setInt(5, T.nombre);

				      // Ex�cution de la requ�te
				      preparedStmt.execute();
	}
	
	public ResultSet getTransiton(int saison) throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT * FROM transition where saison ="+saison;
		try {
			resultats = stmt.executeQuery(requete);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return resultats;	

	}
	
	public int getNb(int id,int periode,int saison) throws SQLException {
		ResultSet resultats = null;
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		String requete = "SELECT SUM(nombre) FROM `transition` WHERE debut ="+id+" and saison ="+saison+" and periode ="+periode;
		try {
			resultats = stmt.executeQuery(requete);
			resultats.next(); 
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return resultats.getInt(1);	

	}
	
	public void saveProba(int debut,int fin,double p,int saison,int periode) throws SQLException{
		Connection con = dataSource.getConnection();
		String query = "update transition set proba = ?"
				        + " where debut= ? and fin = ? and saison = ? and periode= ?";

				      // Donn�es � ins�rer dans la base de donn�es
				      PreparedStatement preparedStmt = con.prepareStatement(query);
				      preparedStmt.setDouble(1, p);
				      preparedStmt.setInt (2, debut);
				      preparedStmt.setInt (3, fin);
				      preparedStmt.setInt (4, saison);
				      preparedStmt.setInt (5, periode);
				      // Ex�cution de la requ�te
				      preparedStmt.execute();
	}
	public ResultSet getNext(int id,int periode,int saison) throws SQLException{
		ResultSet resultats = null;
		String query = "SELECT `fin`, `proba`, FROM transition WHERE debut ="+id
				+ " and periode ="+periode+" and saison="+saison+" ORDER BY `transition`.`proba` DESC;";
		Connection con = dataSource.getConnection();
		java.sql.Statement stmt = con.createStatement();
		resultats = stmt.executeQuery(query);
		return resultats;
	}
	
}
