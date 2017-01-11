package detection.service;

import java.sql.SQLException;
import java.util.ArrayList;

import detection.Bd.DetectionDao;
import detection.train.Etat;
import detection.train.EtatProbable;
import detection.train.Train;
import simulation.Date;
import simulation.Heure;

public class Detection {
	public static int boo = 0;
	public static boolean erreurMoyenH = false;
	public static boolean erreurSimple = false;
	public static int erreurSimpleCount = 0;
	
	
	
	public static double getConso(int heure,int jour,int mois) throws SQLException{
		double consTT =0;
		 consTT=DetectionDao.getconsocour(heure,jour,mois);
		 return consTT;
	}
	public static int getPeriode(Heure h){
		if(h.heure>18 && h.heure<22)
			return 2;
		return 1;
	}
	
	public static void calcul(Heure h,Date date) {

		if (h.heure < 23) {
			h.heure++;
		} else {

			h.heure = 0;
			if (date.jour < 30) {
				boo = 0;
				date.jour++;
			} else {
				boo = 1;
				date.jour = 1;
				if (date.mois < 12) {
					date.mois++;
				} else {
					date.annee++;
				}
			}
		}

	}
	public static int DetermineSaison(int mois) {
		int numero = 0;

		if (mois == 12 || mois <= 3) {
			numero = 2;
		} else if (mois > 3 && mois <= 6) {
			numero = 1;
		} else if (mois > 6 && mois <= 9) {
			numero = 0;
		} else if (mois > 9 && mois <= 12) {
			numero = 3;
		}

		return numero;
	}
	
	public static Etat getEtat(int conso,int p,Train T) {
		int dispo = T.etatExist(conso,p);
		Etat E;
		if(dispo == -1){
			 E = new Etat(conso, 50);
			T.etats[p].add(E);
		}else
			E = T.etats[p].get(dispo);
		return E;
	}
	public static void setErreur(Etat reel,Etat predi,int h,int m){
		if(reel.val > predi.val){
			new Erreur(reel.val-(predi.val + predi.tolerance),h,m);
		}else{
			new Erreur((predi.val - predi.tolerance-reel.val),h,m);
		}
	}
	
	public static int checkEtat(Etat reel,ArrayList<EtatProbable> etats){
			for (EtatProbable etat : etats) {
				if(etat.Idetat == reel.id){
					return 1;
				}
			}
			return -1;
	}

	public static void majErreur(int h,int j,int m,Train T){
		if(Erreur.erreurGarve !=null){
			// enregistrer une erreur
		}
		if(Erreur.erreurMoyen.size() == 1){
			erreurMoyenH = true;
		}
		if(Erreur.erreurMoyen.size() == 2){
			erreurMoyenH = false;
			//enregistrer une erreur
		}
		if(Erreur.erreurSimple.size() ==1){
			erreurSimpleCount = Erreur.erreurSimple.size();
			erreurSimple = true;
		}
		if(h == 24 && Erreur.erreurSimple.size() > 3){
			// creer etat et ajouter
		}
	}
}
