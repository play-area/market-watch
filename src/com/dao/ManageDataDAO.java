package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageDataDAO {
	
	public static void main(String args[]){  
		try{  
		Class.forName("com.mysql.jdbc.Driver");  
		Connection con=DriverManager.getConnection("jdbc:mysql://50.62.209.77:3306/market-watch-db","mw2018","MarketWatch2018");  

		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select * from watchlist_nifty_fo");  
		while(rs.next()){
			System.out.println(rs.getString(1));  
		}
		con.close();  
		}catch(Exception e){ System.out.println(e);}  
		}  
}
