package detection;

import java.sql.SQLException;
import java.util.ArrayList;

import detection.service.Detection;
import detection.service.Erreur;
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
		Erreur.init();
		T = new Train[4];
		T[2] = new Train(2);
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
				currentT = T[2];
				newSaison = true;
			}
			int start = (int) Detection.getConso(0, date.jour, date.mois);
			Etat startE = Detection.getEtat(start,p,currentT);
			while(date.mois == mois_cur){
				try{
				p = Detection.getPeriode(h);
				int consommation = (int) Detection.getConso(h.heure, date.jour, date.mois);
				if(consommation!= -1){
				Etat cur = Detection.getEtat(consommation,p,currentT);
				ArrayList<EtatProbable> suivant = T[saison].M[p-1].getnext(startE, p, saison);
				int test = Detection.checkEtat(cur, suivant);
				if(test==-1){
					if(!suivant.isEmpty())
						Detection.setErreur(cur, currentT.getEtatById(suivant.get(1).Idetat, p),h.heure,date.mois);
					else{
						System.out.println(h.toString());
						System.out.println(cur.toString());
						System.out.println("periode "+p);
					}
				}
				startE = cur;
				Detection.majErreur(h.heure, date.jour, date.mois, currentT);
				Detection.calcul(h, date);
				}else{
					continuer = false;
				}
			}catch (Exception e) {
				System.out.println(h.toString());
				System.out.println(startE.toString());
				System.out.println("periode "+p);
				e.printStackTrace();
			}
			
		}
		System.out.println("Fini le "+date.jour+"-"+date.mois+"-"+date.annee+"   à "+h.heure+" heure");
		
	}

}
}
