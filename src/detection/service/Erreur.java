package detection.service;

import java.util.ArrayList;

public class Erreur {
public static final int ERREUR_SIMPLE = 1;
public static final int ERREUR_MOYEN = 2;
public static final int ERREUR_GRAVE = 3;
public static ArrayList<Erreur> erreurSimple;
public static ArrayList<Erreur> erreurMoyen;
public static Erreur erreurGarve;
public int type;
public int heure;
public int mois;
public int ecart;

public static void init(){
	erreurSimple = new ArrayList<Erreur>();
	erreurMoyen = new ArrayList<Erreur>();
}

public Erreur(int ecart,int h,int mois) {
	this.ecart = ecart;
	this.heure = h;
	this.mois = mois;
	if(ecart < 50){
		this.type = ERREUR_SIMPLE;
		erreurSimple.add(this);
	}
	else{
		if(ecart < 100){
		this.type = ERREUR_MOYEN;
		erreurMoyen.add(this);
		}else{
			this.type = ERREUR_GRAVE;
			erreurGarve = this;
		}
	}
}

}
