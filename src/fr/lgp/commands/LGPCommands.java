package fr.lgp.commands;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import fr.lgp.main.main;
import fr.lgp.scoreboards.LGPScoreboard;
import fr.lgp.scoreboards.LGPTeams;
import fr.lgp.utils.GameStates;

public class LGPCommands implements CommandExecutor {

	public main plugin;
	public LGPScoreboard LGPS;
	public LGPTeams LGPT;
	public Location Spawn;
	public Location SpawnHumain;
	public Location SpawnLoup;
	public static String préfixe = main.préfixe;

	public LGPCommands(main plugin){
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(final CommandSender s, Command c, String l, String[] a) {
		if (!(s instanceof Player)) {
			s.sendMessage(ChatColor.RED+"Vous devez etre un joueur pour executer cette commande.");
			return true;
		}
		Player pl = (Player)s;
		if(!(main.state == GameStates.waiting)) {
			pl.sendMessage(préfixe+ChatColor.RED+"Une partie a été lancée, vous ne pouvez pas effectuer ces commandes. Veuillez reload le serveur pour recommencer une partie.");
			return true;
		}
		else if (c.getName().equalsIgnoreCase("lg")) {
			if (!pl.isOp()) {
				pl.sendMessage(préfixe+ChatColor.RED+"Vous devez être Op.");
				return true;
			}
			if (a.length == 0) {
				pl.sendMessage(préfixe+"Usage : /lg <start|setup|help>");
				return true;
			}
			else if (a[0].equalsIgnoreCase("start")) {
				if (Spawn == null || SpawnHumain == null || SpawnLoup == null) {
					pl.sendMessage(préfixe+ChatColor.RED+"Setup non configuré !  Impossible de lancer la partie.");
					return true;
				}
				if (main.LGPT.nmbHumain() == 0 || main.LGPT.nmbLoup() == 0) {
					pl.sendMessage(préfixe+ChatColor.RED+"Il faut au moins 1 joueur dans chaque teams.");
					return true;
				}
				else {

					Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin , new BukkitRunnable() {
						@Override
						public void run() {
							if (main.timeBeforeTheParty != -1) {
								if (main.timeBeforeTheParty != 0) {	

									if (main.timeBeforeTheParty == 1) {
										Bukkit.getServer().broadcastMessage(préfixe + ChatColor.GOLD+"Lancement de la partie dans "+ChatColor.LIGHT_PURPLE+ main.timeBeforeTheParty +ChatColor.GOLD+" seconde.");
										main.timeBeforeTheParty--;
									}

									else if (main.timeBeforeTheParty == 60 || main.timeBeforeTheParty == 30 || main.timeBeforeTheParty <= 10) {
										Bukkit.getServer().broadcastMessage(préfixe + ChatColor.GOLD+"Lancement de la partie dans "+ChatColor.LIGHT_PURPLE+ main.timeBeforeTheParty +ChatColor.GOLD+" secondes.");
										main.timeBeforeTheParty--;
									}
								}		
								else {
									Bukkit.getServer().broadcastMessage(préfixe +ChatColor.GREEN+"GO");
									main.timeBeforeTheParty--;
									start();
								}

							}
						}	
					},0L, 20L);

				}

				return true;

			}

			else if (a[0].equalsIgnoreCase("setup")) {
				Block block = pl.getTargetBlock(null, 5);

				if (a[0] != null) {
					pl.sendMessage(préfixe+"Usage : /lg setup <spawn|spawnHumain|spawnLoup>");
					return true;
				}
				else if (a[1].equalsIgnoreCase("spawn")) {
					int x = block.getX();
					int y = block.getY();
					int z = block.getZ();
					Location location = new Location(pl.getWorld(), x, y+1, z);
					Spawn = location;
					block.setType(Material.BEACON);
					pl.sendMessage(préfixe+"Spawn : x:"+x+" y:"+y+" z:"+z);
				}
				else if (a[1].equalsIgnoreCase("spawnHumain")) {
					int x = block.getX();
					int y = block.getY();
					int z = block.getZ();
					Location location  = new Location(pl.getWorld(), x, y+1, z);
					SpawnHumain = location;
					block.setType(Material.STAINED_CLAY);
					block.setData((byte) 4);
					pl.sendMessage(préfixe+"Spawn humain : x:"+x+" y:"+y+" z:"+z);
				}
				else if (a[1].equalsIgnoreCase("spawnLoup")) {
					int x = block.getX();
					int y = block.getY();
					int z = block.getZ();
					Location location = new Location(pl.getWorld(), x, y+1, z);
					SpawnLoup = location;
					block.setType(Material.STAINED_CLAY);
					block.setData((byte) 11);
					pl.sendMessage(préfixe+"Spawn loup : x:"+x+" y:"+y+" z:"+z);
				}
				return true;
			}
			else if (a[0].equalsIgnoreCase("help")) {
				pl.sendMessage("");
				pl.sendMessage("");
				pl.sendMessage(ChatColor.BOLD+""+ChatColor.GREEN+"=========="+ChatColor.GOLD+" Loup Garou "+ChatColor.BOLD+""+ChatColor.GREEN+"==========");
				pl.sendMessage(ChatColor.GRAY+"     - "+ChatColor.DARK_PURPLE+"Commandes :");
				pl.sendMessage(ChatColor.YELLOW+"/lg start"+ChatColor.GRAY+" : Démarre la partie.");
				pl.sendMessage(ChatColor.YELLOW+"/lg setup <spawn||spawnHumain||spawnLoup>"+ChatColor.GRAY+" : configure le setup.");
				pl.sendMessage("");
				pl.sendMessage(ChatColor.GRAY+"     - "+ChatColor.DARK_PURPLE+"Règles :");
				pl.sendMessage(ChatColor.AQUA+"Le but de chacun est d'envoyer une "
						+ "potion qui sera donnée sur l'adversaire pour qu'il rejoigne la team afin qu'une team soit réduite à 0 joueur. Pour récupérer des potions ou "
						+ "des blocks, il vous suffira de marcher sur votre spawn."+ChatColor.LIGHT_PURPLE+" Bon jeu !!!");
				pl.sendMessage("");
				pl.sendMessage(ChatColor.BOLD+""+ChatColor.GREEN+"=================================");

				return true;
			}

		}

		return false;

	}

	public void start() {	
		main.LGPS.Scoreboard();
		main.LGPS.Timer();			
		Player player = Bukkit.getServer().getPlayer("");

		main.LGPT.teleportHumain(SpawnHumain);
		main.LGPT.teleportLoup(SpawnLoup);
		main.state = GameStates.game;

		if (!(main.LGPT.getTeamsHumain().hasPlayer(player) || main.LGPT.getTeamsLoup().hasPlayer(player))) {
			if(!(player == null)) {
				main.LGPT.getTeamsSpectateur().addPlayer(player);
				main.LGPT.playersSpectateur.add(player);
			}
		}

		for(Player p : Bukkit.getOnlinePlayers()){
			p.setGameMode(GameMode.ADVENTURE);
			p.setLevel(0);
			p.setFoodLevel(20);
			p.setHealth(20);
			if (main.LGPT.getTeamsSpectateur().hasPlayer(p)) {
				p.sendMessage(main.préfixe+"Bienvenue dans la team spectateur");
				p.teleport(Spawn);
				p.setFlying(true);
				p.getInventory().clear();
				ItemStack is = new ItemStack(Material.COMPASS);
				ItemMeta im = is.getItemMeta();
				im.setDisplayName("Spectateur ( Right Click )");
				is.setItemMeta(im);
				p.getInventory().addItem(is);
			}
		}

	}

}
