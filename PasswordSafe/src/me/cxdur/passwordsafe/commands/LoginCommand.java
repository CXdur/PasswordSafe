package me.cxdur.passwordsafe.commands;

import me.cxdur.passwordsafe.PasswordSafe;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class LoginCommand implements CommandExecutor {

	/*@Author CXdur
	 * Part of this code belongs to OwnBlocksX, and will be changed later.
	 * OwnBlocks is licensed to DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE, so is my code. 
	 */

	private PasswordSafe plugin;

	public LoginCommand(PasswordSafe instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player p = (Player) sender;
		if (p.hasPermission(plugin.getConfig().getString("Permission.Node.Login")) || p.isOp()) {
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Usage: Login <Password>");
				return true;
			} else if (args.length == 1) {
				if (args[0].equals(plugin.getPassword(p.getName()))) {
					plugin.NotLogged.remove(p);
					p.sendMessage(ChatColor.GREEN + "You have succesfully logged in.");
					p.sendMessage(ChatColor.GREEN + "You last logged in with IP: " + plugin.getIP(p.getName() + "."));
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "Invalid password.");
					return true;
				}
			}
		}
		return false;	}	}
