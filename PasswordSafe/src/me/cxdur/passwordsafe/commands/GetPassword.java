package me.cxdur.passwordsafe.commands;

import me.cxdur.passwordsafe.PasswordSafe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetPassword implements CommandExecutor {

	/*@Author CXdur
	 * Part of this code belongs to OwnBlocksX, and will be changed later.
	 * OwnBlocks is licensed to DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE, so is my code. 
	 */

	private PasswordSafe plugin;

	public GetPassword(PasswordSafe instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd,
			String label, String[] args) {
		Player p = (Player) sender;
		if (p.hasPermission(plugin.getConfig().getString("Permission.Node.GetPassword")) || p.isOp()) {
			if (args.length == 0) {
				p.sendMessage(ChatColor.RED + "Usage: /getpassword <Player>");
				return true;
			} else if (args.length == 1) {
				Player v = Bukkit.getPlayer(args[0]);
				p.sendMessage(ChatColor.RED + "Username: " + v.getName());
				p.sendMessage(ChatColor.RED + "Password: " + plugin.getPassword(v.getName()));
				return true;
			}
		}
		return false;
	}	}