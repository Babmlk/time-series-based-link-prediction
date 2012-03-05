package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

	public static Connection getConnection(){
		String driverName = "oracle.jdbc.driver.OracleDriver";                        
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		try {
			Class.forName(driverName);
			return DriverManager.getConnection(url,"paulo","admin");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
