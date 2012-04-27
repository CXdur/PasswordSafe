package me.cxdur.passwordsafe.listener;

import me.cxdur.passwordsafe.PasswordSafe;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PasswordSafeListener implements Listener {

	private PasswordSafe plugin;

	public PasswordSafeListener(PasswordSafe instance) {
		plugin = instance;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerMove(final PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			e.setTo(e.getFrom());
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandPreProcessEvent(final PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			if (!e.getMessage().startsWith("/login")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockPlaceEvent(final BlockPlaceEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDropItemEvent(final PlayerDropItemEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerPickupItemEvent(final PlayerPickupItemEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onBlockBreakEvent(final BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerChatEvent(final PlayerChatEvent e) {
		Player p = e.getPlayer();
		if (plugin.NotLogged.containsKey(e.getPlayer())) {
			if (!e.getMessage().startsWith("/login")) {
			e.setCancelled(true);
			p.sendMessage(ChatColor.RED + "You are not logged in, please log in with: /login <Password>.");
		}
	}	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerJoinEvent(final PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.isOp() && (plugin.getConfig().getBoolean("Permission.OPHasPermission") == true)|| (p.hasPermission(plugin.getConfig().getString("Permission.Node")))) {
			plugin.addPlayer(p.getName());			
		}
	}	}