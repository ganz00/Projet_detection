package detection.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import detection.Main;
import detection.train.Train;

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
final Logger logger = Logger.getLogger(Erreur.class.getName());
public Erreur(int ecart,int h,int mois) {
	this.ecart = ecart;
	this.heure = h;
	this.mois = mois;
	try {
		
		InputStream in=Main.class.getResourceAsStream("/META-INF/logging.properties");
		LogManager.getLogManager().readConfiguration(in);
		in.close();
	} catch (Exception e) {
		e.printStackTrace();
	}

	if(ecart < 50){
		this.type = ERREUR_SIMPLE;
		logger.log(Level.INFO, "erreure simple"+h+"/"+mois+"ecart"+ecart);
		erreurSimple.add(this);
	}
	else{
		if(ecart < 100){
		this.type = ERREUR_MOYEN;
		logger.log(Level.WARNING, "erreure moyen"+h+"/"+mois+"ecart"+ecart);
		erreurMoyen.add(this);
		}else{
			this.type = ERREUR_GRAVE;
			logger.log(Level.WARNING, "erreure grave"+h+"/"+mois+"ecart"+ecart);
			erreurGarve = this;
		}
	}
}

}
