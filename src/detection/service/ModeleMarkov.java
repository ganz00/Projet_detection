package detection.service;

import java.util.ArrayList;

import detection.train.Etat;

public class ModeleMarkov {
	public int[][][] Transition;
	
	public int[][][] A;
	public int nombreEtat;
	
public ModeleMarkov(int[][][] matriceTransition, int nombreEtat) {
		super();
		this.Transition = matriceTransition;
		this.nombreEtat = nombreEtat;
	}

public void setB(){
	//contruire la matrice de transition B a l'aide du nombre d'etat et la matrice transition
	
}
public void majmatrice(){
	//meta jours la matrice en fonction des etats et de la matrice de transition
}

public void editTransition(int id,int etat1,int etat2){
	//rajouter un transition dans la matrice transition en fonction de la saison id
}

public ArrayList<Etat> getnext(Etat E){
	//utiliser la matrice pour recuperer les prochain etat probable
	return null;
	
}

}
