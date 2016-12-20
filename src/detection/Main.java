package detection;

import java.sql.ResultSet;
import java.sql.SQLException;

import detection.Bd.SGBD;
import detection.train.Train;

public class Main {
	public static Train T;
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		T = new Train();
		T.GetEtat();
		
	}

}
