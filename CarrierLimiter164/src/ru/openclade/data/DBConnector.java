package ru.openclade.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



import ru.openclade.data.interfaces.IDB;

public class DBConnector implements IDB {
	private String host;
	private String port;
	private String database;
	private String username;
	private String password;
	public DBConnector(String host, String port, String database, String username, String password) {
		this.host = host;
		this.port = port;
		this.database = database;
		this.username = username;
		this.password = password;
	}
	@Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        String connectionUrl = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database; // link of connection
        try { // try catch
            // Here we load the driver�s class file into memory at the runtime
            Class.forName("com.mysql.jdbc.Driver"); // creating object  
            // Establish the connection
            Connection con = DriverManager.getConnection(connectionUrl, this.username, this.password);
            return con; // connection manager
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
