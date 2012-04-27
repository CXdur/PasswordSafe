package me.cxdur.passwordsafe.commands;

import me.cxdur.passwordsafe.PasswordSafe;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetPasswordCommand implements CommandExecutor {

	private PasswordSafe plugin;
	
	public SetPasswordCommand(PasswordSafe instance) {
		plugin = instance;
	}
	
	 public boolean onCommand(CommandSender sender, Command cmd,
             String label, String[] args) {
	   Player p = (Player) sender;
	   if (p.hasPermission(plugin.getConfig().getString("Permission.Node.SetPW")) || p.isOp()) {
		  if (args.length == 0 || (args.length > 1)) { 
			  p.sendMessage(ChatColor.GOLD + "Usage: /setpassword <Password>");
			  return true;
		  } else if (args.length == 1) {
				  plugin.setPassword(p.getName(), args[0]);
				  p.sendMessage(ChatColor.RED + "You're password is set to: " + ChatColor.GREEN + args[0]);
				  return true;
			  } else {
				  p.sendMessage(ChatColor.RED + "This is already you're password.");
				  return true;
			  }			 
		  }		  
	return false;
	 }
}	