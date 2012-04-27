package me.cxdur.passwordsafe;

import java.sql.SQLException;
import java.util.HashMap;

import me.cxdur.passwordsafe.commands.GetPassword;
import me.cxdur.passwordsafe.commands.LoginCommand;
import me.cxdur.passwordsafe.commands.SetOtherPassword;
import me.cxdur.passwordsafe.commands.SetPasswordCommand;
import me.cxdur.passwordsafe.listener.PasswordSafeListener;
import me.cxdur.passwordsafe.sql.MysqlDB;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PasswordSafe extends JavaPlugin {	

	/*@Author CXdur
	 * Part of this code belongs to OwnBlocksX, and will be changed later.
	 * OwnBlocks is licensed to DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE, so is my code. 
	 */

	private MysqlDB mysqlDB;
	private String host,databaseName,username,password;

	public HashMap<Player, String> NotLogged = new HashMap<Player, String>();

	public PasswordSafeListener listener = new PasswordSafeListener(this);

	FileConfiguration conf;

	public void onEnable() {
		conf = getConfig();
		getDataFolder().mkdir();
		print("Connecting to MySQL, loading config!");
		addConfigDefaults();
		connectSql();
		createTable();
		registerAll();
	}

	public void addPlayer(String name) {
		if (!mysqlDB.hasPlayer(name)) {
			mysqlDB.addPlayer(name);
		}
		if (!mysqlDB.hasPassword(name)) {
			getServer().getPlayer(name).sendMessage(ChatColor.RED + "You have no password, set one with:");
			getServer().getPlayer(name).sendMessage(ChatColor.RED + "/setpassword <Password>");
		} else {
			NotLogged.put(Bukkit.getPlayer(name), name);
			getServer().getPlayer(name).sendMessage(ChatColor.GREEN + "Please log in with:");
			getServer().getPlayer(name).sendMessage(ChatColor.GREEN + "/login <Password>");
		}
	}

	public void registerAll() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(listener, this);
		getCommand("getpassword").setExecutor(new GetPassword(this));
		getCommand("login").setExecutor(new LoginCommand(this));
		getCommand("setotherpassword").setExecutor(new SetOtherPassword(this));
		getCommand("setpassword").setExecutor(new SetPasswordCommand(this));
	}

	public void createTable() {
		try {
			mysqlDB.loadPlayersTable();
		} catch (Exception e) {
			print("Failed on loading Players table: ");
			e.printStackTrace();
		}
	}

	public void onDisable() {
		print("Closing SQL Connection.");
		try {
			mysqlDB.closeConnection();
		} catch (SQLException e) {
			print("Error on disconnection from MySQL: ");
			e.printStackTrace();
		}
	}

	public synchronized String getPassword(String playerName) {
		return mysqlDB.getPassword(playerName);
	}

	public void setPassword(String playerName, String password) {
		mysqlDB.setPassword(playerName, password);
	}

	public synchronized String getIP(String playerName) {
		return mysqlDB.getIP(playerName);
	}

	public void addConfigDefaults() {
		conf.options().header("PasswordSafe config, please fill this out before enabling the plugin for real: ");
		conf.addDefault("MySQL.Host", "localhost");
		conf.addDefault("MySQL.DatabaseName", "PasswordSafe");
		conf.addDefault("MySQL.Username", "root");
		conf.addDefault("MySQL.Password", "root");
		conf.addDefault("Permission.Node.NeedPW", "passwordsafe.needspassword");
		conf.addDefault("Permission.Node.SetPW", "passwordsafe.setpassword");
		conf.addDefault("Permission.Node.SetPWOthers", "passwordsafe.setpasswordothers");
		conf.addDefault("Permission.Node.Login", "passwordsafe.login");
		conf.addDefault("Permission.Node.GetPassword", "passwordsafe.getpassword");
		conf.addDefault("Permission.OPHasPermission", true);	
		conf.options().copyDefaults(true);	
		saveConfig();
	}

	public void print(String message) {
		System.out.println("[PasswordSafe] " + message);
	}

	private void connectSql() {
		host = conf.getString("MySQL.Host");
		databaseName = conf.getString("MySQL.DatabaseName");
		username = conf.getString("MySQL.Username");
		password = conf.getString("MySQL.Password");
		mysqlDB = new MysqlDB(this, host, databaseName, username, password);
		try {
			mysqlDB.establishConnection();
			print("Successfully connected to the MySQL Database.");
		} catch (Exception e) {
			print("Cant connect to MySQL database with: " + host + ", " + databaseName + ", " + username + ", " + password + ".");
			e.printStackTrace();
		}
	}
}