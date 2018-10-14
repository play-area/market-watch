package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.constants.DatabaseConstants;

public class CreateDatabaseConnection {
	
	/**
	 * Function to get the symbols details for a particular watchlist
	 * @param ip IP Address
	 * @param port Port
	 * @param name Database Name
	 * @param user Database User
	 * @param password Database Password
	 * @return Connection
	 */
	public static Connection getMySQLConnection(String ip, int port, String name,String user,String password) {
        Connection con = null;
		try {
			Class.forName(DatabaseConstants.MYSQL_DB_DRIVER);  
			con=DriverManager.getConnection(DatabaseConstants.MYSQL_DB_DRIVER_TYPE+"://"+ip+":"+port+"/"+name,user,password);  
        } catch (Exception ex) {
            System.out.println("Could not establish database connection because = "+ ex); 
        }
        return con;
    }
}
