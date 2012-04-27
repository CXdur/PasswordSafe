package me.cxdur.passwordsafe.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import me.cxdur.passwordsafe.PasswordSafe;

public class MysqlDB {

	private String username;
	private String password;
	private String host;
	private String databaseName;
	@SuppressWarnings("unused")
	private PasswordSafe plugin;
	private String url;
	private Connection conn;

	public MysqlDB(PasswordSafe instance, String host, String databaseName, String username, String password)	{
		this.host = host;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		plugin = instance;
	}

	public synchronized int addPlayer(String player) {
		int ret = -1;
		try {
			Statement s = conn.createStatement();
			String value = "('"+ player + "', 'nopassword', '" + Bukkit.getPlayer(player).getAddress().getAddress().getHostAddress() + "')";
			ret = s.executeUpdate("INSERT INTO Players (name, password, ip) VALUES " + value);
		} catch (Exception e) {
			e.printStackTrace();
		}        
		return ret;
	}

	public synchronized boolean hasPlayer(String playerName) {
		ResultSet rs = getPlayer(playerName);
		boolean ret = false;
		try {
			while (rs.next())
			{
				if (rs.getString("name").equals(playerName))
					ret = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public synchronized boolean hasPassword(String playerName) {
		ResultSet rs = getPlayer(playerName);
		PreparedStatement ps = null;
		String password = null;
		boolean ret = false;		 
		try {
			ps = conn.prepareStatement("SELECT password FROM players WHERE `name` = '"
					+ playerName + "'");
			rs = ps.executeQuery();
			while (rs.next()) {
				password = rs.getString("password");
				if (!password.equalsIgnoreCase("nopassword")) {
					ret = true;
				}	}
		} catch (SQLException ex) {
			System.out.println("SQL error on hasPassword" + ex);
		}
		return ret;
	} 	

	public synchronized String getPassword(String playerName) {
		ResultSet rs = getPlayer(playerName);
		PreparedStatement ps = null;
		String password = null;	 
		try {
			ps = conn.prepareStatement("SELECT password FROM players WHERE `name` = '"
					+ playerName + "'");
			rs = ps.executeQuery();
			while (rs.next()) {
				password = rs.getString("password");
				return password;
			}	
		} catch (SQLException ex) {
			System.out.println("SQL error on hasPassword" + ex);
		}
		return password;
	} 	

	public synchronized String getIP(String playerName) {
		ResultSet rs = getPlayer(playerName);
		PreparedStatement ps = null;
		String ip = null;	 
		try {
			ps = conn.prepareStatement("SELECT ip FROM players WHERE `name` = '"
					+ playerName + "'");
			rs = ps.executeQuery();
			while (rs.next()) {
				ip = rs.getString("ip");
				return ip;
			}	
		} catch (SQLException ex) {
			System.out.println("SQL error on hasPassword" + ex);
		}
		return ip;
	} 	
	
	public synchronized String setPassword(String playerName, String password) {
		Statement s = null;
		try {
			s = conn.createStatement();
			s.executeUpdate("UPDATE players SET password='"+password+"' WHERE name='" + playerName + "'");
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return password;

	}

	public synchronized ResultSet getPlayer(String playerName) {
		ResultSet rs = null;
		try {
			Statement s = conn.createStatement();
			s.executeQuery("SELECT * FROM players WHERE name='"+playerName+"'");
			rs = s.getResultSet();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public synchronized void loadPlayersTable()throws Exception {
		Statement s = conn.createStatement();
		if (!checkTable("players"))
			s.executeUpdate("CREATE TABLE players (id INT UNSIGNED NOT NULL UNIQUE AUTO_INCREMENT, PRIMARY KEY (id), name varchar(50), password varchar(50), ip varchar(50))");

	}

	private synchronized boolean checkTable(String table)throws Exception {
		Statement s = conn.createStatement();
		s.executeQuery("SHOW TABLES");
		ResultSet rs = s.getResultSet();
		boolean ret = false;
		while (rs.next())
		{
			if (rs.getString(1).equalsIgnoreCase(table))
				ret = true;
		}
		return ret;
	}
	public synchronized void establishConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException
	{
		url = "jdbc:mysql://"+host+"/"+databaseName;
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, username, password);
		//pluginRef.debugMessage("Connection attempt to database -- done");
	}

	public synchronized void closeConnection() throws SQLException
	{
		conn.close();
	}

	public synchronized void resetConnection() throws Exception
	{
		closeConnection();
		establishConnection();
	}
}