package fr.lgp.eventhandler;


import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.lgp.commands.LGPCommands;
import fr.lgp.main.main;
import fr.lgp.scoreboards.LGPTeams;
import fr.lgp.utils.GameStates;
import fr.lgp.utils.LGPItems;

public class LGPEventHandler implements Listener{

	public main plugin;
	public LGPTeams LGPT;
	public LGPItems LGPI;
	public LGPCommands LGPC;
	//private static String préfixe = main.préfixe;

	public LGPEventHandler(main plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		LGPI.GiveItemTeam();
	}

	/*@EventHandler(priority = EventPriority.LOWEST)
	public void LoupNoMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(state == GameState.game) {
			if (minutesLeft >= this.getConfig().getInt("PartTime")-1) {
				if (LGPT.getTeamsLoup().hasPlayer(player)) {
					player.teleport(LGPC.SpawnLoup);
					player.sendMessage(préfixe+ChatColor.RED+"Vous pourrez bouger dans "+ChatColor.BLUE+LGPS.secondsLeft+ChatColor.RED+" second(s).");

				}
			}
		}

	}*/

	@EventHandler (priority = EventPriority.LOW)
	public void GiveItem(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if(main.state == GameStates.game) {
			if(LGPT.getTeamsHumain().hasPlayer(player)) {
				if(player.getLocation().getBlockX() == LGPC.SpawnHumain.getBlockX() && player.getLocation().getBlockZ() == LGPC.SpawnHumain.getBlockZ()) {
					LGPI.ItemHumain(player);
				}
			}
			else if(LGPT.getTeamsLoup().hasPlayer(player)) {
				if(player.getLocation().getBlockX() == LGPC.SpawnLoup.getBlockX() && player.getLocation().getBlockZ() == LGPC.SpawnLoup.getBlockZ()) {
					LGPI.ItemLoup(player);
				}
			}
		}
	}

	@EventHandler (priority = EventPriority.HIGH)
	public void PlayerRespawn(PlayerRespawnEvent event) {
		final Player player = event.getPlayer();
		if(main.state == GameStates.game) {
			if(LGPT.getTeamsHumain().hasPlayer(player)) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						player.teleport(LGPC.SpawnHumain);
						LGPI.ItemHumain(player);
					}
				}, 1);				
			}
			else if(LGPT.getTeamsLoup().hasPlayer(player)) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
						player.teleport(LGPC.SpawnLoup);
					}
				}, 1);		

			}

		}
	}

	@EventHandler (priority = EventPriority.HIGH)
	public void invSpectateur(PlayerInteractEvent event) {
		Inventory inv = Bukkit.createInventory(null, 45,"Spectateur");
		ArrayList<String> lore;
		Action a = event.getAction();
		ItemStack is = event.getItem();
		Player pl = event.getPlayer();

		if (a == Action.PHYSICAL || is == null || is.getType() == Material.AIR) 
			return;

		if (is.getType() == Material.COMPASS) {
			for(Player p : Bukkit.getOnlinePlayers()){
				ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName(p.getName());
				lore = new ArrayList<String>();
				lore.add(ChatColor.DARK_PURPLE + "Téléport") ;
				meta.setLore(lore) ;
				item.setItemMeta(meta);
				inv.addItem(item);

			}
			pl.openInventory(inv);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ItemStack clicked = event.getCurrentItem();
		Inventory inventory = event.getInventory();
		if (inventory.getName().equals("Spectateur")) {
			if (clicked.getType() == Material.SKULL_ITEM) {
				event.setCancelled(true);
				player.closeInventory();
				String e = clicked.getItemMeta().getDisplayName();
				Player pl = Bukkit.getPlayer(e);
				Location l = pl.getLocation();
				player.teleport(l);
			}
		}
	}
}
