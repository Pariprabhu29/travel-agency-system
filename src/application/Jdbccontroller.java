package application;

import java.sql.*;
public class Jdbccontroller {

	public Connection databaselink;
	
	public Connection getConnection() {
		
		
		try 
		{
			  Class.forName("com.mysql.cj.jdbc.Driver");
			  databaselink=DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","root");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return databaselink;
	}

}