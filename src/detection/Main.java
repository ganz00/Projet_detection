package detection;

import java.sql.SQLException;

import detection.service.Detection;
import detection.train.Train;
import simulation.Date;
import simulation.Heure;

public class Main {
	public static Date date = new Date();
	public static Heure h = new Heure();
	public static int boo = 0;

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		boolean continuer = true;
		Train T1 = new Train(3);
		T1.Init();
		int p;
		date.annee = 2016;
		date.mois = 1;
		date.jour = 1;
		while(continuer){
			int consommation = Detection.getConso(h.heure, date.jour, date.mois);
			if(h.heure>18 && h.heure<22){
				p = 2;
			}else
				p=1;
			
		}
		
	}
	public static void calcul() {

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

}
