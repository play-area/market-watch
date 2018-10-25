package com.trading.constants;

public class DatabaseConstants {
	
	//Database Property Keys
	public static final String MYSQL_REMOTE_IP = "mysql_remote_ip";
	public static final String MYSQL_REMOTE_PORT = "mysql_remote_port";
	public static final String MYSQL_REMOTE_DB_NAME = "mysql_remote_db_name";
	public static final String MYSQL_REMOTE_DB_USER = "mysql_remote_db_user";
	public static final String MYSQL_REMOTE_DB_PASSWORD = "mysql_remote_db_password";
	
	//DB Driver 
	public static final String MYSQL_DB_DRIVER = "com.mysql.jdbc.Driver";
	public static final String MYSQL_DB_DRIVER_TYPE = "jdbc:mysql";
	
	
	//Queries
	public static final String WATCHLIST_QUERY = "SELECT * FROM watchlist_nifty_fo";
	public static final String CANDLES_QUERY_1 = "SELECT * FROM "; 
	public static final String CANDLES_QUERY_2 = " WHERE symbol =?";
	
	
	public static final String WATCHLIST_COLUMN_SYMBOL = "symbol";
	public static final String WATCHLIST_COLUMN_COMPANY_NAME = "company_name";
	public static final String WATCHLIST_COLUMN_SECTOR = "sector";
}
