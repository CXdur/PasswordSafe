package me.cxdur.passwordsafe.commands;

import me.cxdur.passwordsafe.PasswordSafe;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetOtherPassword implements CommandExecutor {

	private PasswordSafe plugin;
	
	public SetOtherPassword(PasswordSafe instance) {
		plugin = instance;
	}
	
	 public boolean onCommand(CommandSender sender, Command cmd,
             String label, String[] args) {
	   Player p = (Player) sender;
	   Player v = Bukkit.getServer().getPlayer(args[0]);
	   if (p.hasPermission(plugin.getConfig().getString("Permission.Node.SetPWOthers")) || p.isOp()) {
		  if (args.length == 0) { 
			  p.sendMessage(ChatColor.GOLD + "Usage: /setpassword <Playername> <Password>");
			  return true;
		  } else if (args.length == 2) {
			  plugin.setPassword(v.getName(), args[1]);
			  p.sendMessage(ChatColor.RED + "His password is set to: " + ChatColor.GREEN + args[1]);
			  return true;
		  }
		  }
	return false;
	   }
}

