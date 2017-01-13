package detection;

import java.sql.SQLException;
import java.util.ArrayList;

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
		// TODO Auto-generated method stub
		boolean continuer = true;
		Train currentT;
		int mois_cur = 0;
		date.annee = 2016;
		date.mois = 1;
		date.jour = 1;
		h.heure =1;
		T = new Train[4];
		T[2] = new Train(3);
		T[2].Init();
		
		
		int p=1;
		while(continuer){
			mois_cur = date.mois;
			int saison = Detection.DetermineSaison(date.mois);
			if(newSaison == true){
				T[saison] = new Train(saison);
				T[saison].Init();
				newSaison = false;
			}
			if(T[saison] != null){
				currentT = T[saison];
			}else{
			currentT=currentT.consomHiver(currentT, saison);
				newSaison = true;
			}
			int start = (int) Detection.getConso(0, date.jour, date.mois);
			Etat startE = Detection.getEtat(start,p,currentT);
			while(date.mois == mois_cur){
				p = Detection.getPeriode(h);
				int consommation = (int) Detection.getConso(h.heure, date.jour, date.mois);
				if(consommation!= -1){
				Etat cur = Detection.getEtat(consommation,p+1,currentT);
				ArrayList<EtatProbable> suivant = T[saison].M[p].getnext(startE, p+1, saison);
				int test = Detection.checkEtat(cur, suivant);
				if(test==-1){
					Detection.setErreur(cur, currentT.getEtatById(suivant.get(1).Idetat, p-1),h.heure,date.mois);
				}
				startE = cur;
				Detection.majErreur(h.heure, date.jour, date.mois, currentT);
				Detection.calcul(h, date);
				}else{
					continuer = false;
				}
			}
			
		}
		System.out.println("Fini le "+date.jour+"-"+date.mois+"-"+date.annee+"   à "+h.heure+" heure");
		
	}

}
