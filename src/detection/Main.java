package detection;

import java.sql.SQLException;

import detection.train.Train;

public class Main {
	
	

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Train T1 = new Train(3);
		T1.Init();
		System.out.println("TERMINE");
	}

}
