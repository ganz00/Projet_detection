package detection;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import detection.service.Detection;
import detection.train.Etat;
import detection.train.EtatProbable;
import detection.train.Train;
import simulation.Date;
import simulation.Heure;

public class Main {


	
	public static Date date = new Date();
	public static Heure h = new Heure();
	public static boolean newSaison = false;
	public static Train T[];
	public static void main(String[] args) throws SQLException {
		try {
			
			InputStream in=Main.class.getResourceAsStream("/META-INF/logging.properties");
			LogManager.getLogManager().readConfiguration(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	final Logger logger = Logger.getLogger(Main.class.getName());
		// TODO Auto-generated method stub
		boolean continuer = true;
		Train currentT;
		int mois_cur = 0;
		date.annee = 2016;
		date.mois = 1;
		date.jour = 1;
		T = new Train[4];
		T[2] = new Train(3);
		logger.log(Level.INFO, "initialisation du train");
		T[2].Init();
		
		logger.log(Level.INFO, "debut de la detection");
		int p=1;
		while(continuer){
			mois_cur = date.mois;
			logger.log(Level.INFO, "detection mois ");
			int saison = Detection.DetermineSaison(date.mois);
			if(newSaison == true){
				T[saison-1] = new Train(saison);
				T[saison-1].Init();
				newSaison = false;
			}
			if(T[saison-1] != null){
				currentT = T[saison-1];
			}else{
				currentT = T[2];
				newSaison = true;
			}
			while(date.mois == mois_cur){
				p = Detection.getPeriode(h);
				int consommation = (int) Detection.getConso(h.heure, date.jour, date.mois);
				if(consommation!= -1){
				Etat cur = Detection.getEtat(consommation,p,currentT);
				ArrayList<EtatProbable> suivant = T[saison-1].M[p].getnext(cur, p, saison);
				int test = Detection.checkEtat(cur, suivant);
				if(test==-1)
					Detection.setErreur(cur, currentT.getEtatById(suivant.get(1).Idetat, p),h.heure,date.mois);
				Detection.majErreur(h.heure, date.jour, date.mois, currentT);
				Detection.calcul(h, date);
				}else{
					continuer = false;
				}
			}
			
		}
		logger.log(Level.INFO,"Fini le "+date.jour+"-"+date.mois+"-"+date.annee+"   à "+h.heure+" heure");
		System.out.println("Fini le "+date.jour+"-"+date.mois+"-"+date.annee+"   à "+h.heure+" heure");
		
	}

}
