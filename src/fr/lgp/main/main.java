package fr.lgp.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import fr.lgp.commands.LGPCommands;
import fr.lgp.eventhandler.LGPEventHandler;
import fr.lgp.scoreboards.LGPScoreboard;
import fr.lgp.scoreboards.LGPTeams;
import fr.lgp.utils.GameStates;
import fr.lgp.utils.LGPItems;

public class main extends JavaPlugin {

	Logger logger = null;
	public static ScoreboardManager sbManager = null;
	public static Scoreboard sb = null;
	public static LGPCommands LGPC = null;
	public static LGPScoreboard LGPS = null;
	public static LGPItems LGPI = null;
	public static LGPTeams LGPT = null;
	public static LGPEventHandler LGPEH = null;
	public static Integer timeBeforeTheParty = null;
	public static Integer minutesLeft = null;	
	public static String préfixe = ChatColor.GRAY+"["+ChatColor.GOLD+"LG"+ChatColor.GRAY+"]: ";
	public static GameStates state;

	@Override
	public void onEnable() {
		logger = this.getLogger();
		logger.info("[LGPlugin] is Enable.");
		PluginManager pm = getServer().getPluginManager() ;
		sbManager = Bukkit.getScoreboardManager();
		LGPC = new LGPCommands(this);
		LGPS = new LGPScoreboard(this);
		LGPI = new LGPItems(this);
		LGPT = new LGPTeams(this);
		LGPEH = new LGPEventHandler(this);
		timeBeforeTheParty = this.getConfig().getInt("time_before_the_party");
		minutesLeft = this.getConfig().getInt("time_of_a_game");
		sb = Bukkit.getServer().getScoreboardManager().getNewScoreboard();

		pm.registerEvents(LGPEH, this);
		pm.registerEvents(LGPT, this);
		getCommand("lg").setExecutor(LGPC);
		LGPI.GiveItemTeam();

		state = GameStates.waiting;
	}

	@Override
	public void onDisable() {
		System.out.println("[LGPlugin] is Disable.");
		LGPT.removeHumain();
		LGPT.removeLoup();
		LGPT.removeSpectateur();
	}


	public static void Game() {
		int nmbHumain = main.LGPT.nmbHumain();
		int nmbLoup = main.LGPT.nmbLoup();
		for(Player p : Bukkit.getOnlinePlayers()){
			if(state == GameStates.game) {
				if(LGPT.getTeamsHumain().hasPlayer(p)) {
					if(p.hasPotionEffect(PotionEffectType.WEAKNESS)) {
						p.removePotionEffect(PotionEffectType.WEAKNESS);
						LGPT.getTeamsHumain().removePlayer(p);
						LGPT.getPlayersHumain();
						LGPT.getTeamsLoup().addPlayer(p);
						LGPT.getPlayersLoup();
						p.teleport(LGPC.SpawnLoup);
					}
				}
				else {
					p.removePotionEffect(PotionEffectType.WEAKNESS);
				}
				if(LGPT.getTeamsLoup().hasPlayer(p)) {
					if(p.hasPotionEffect(PotionEffectType.REGENERATION)) {
						p.removePotionEffect(PotionEffectType.REGENERATION);
						p.removePotionEffect(PotionEffectType.SPEED);
						p.removePotionEffect(PotionEffectType.JUMP);
						LGPT.getTeamsLoup().removePlayer(p);
						LGPT.getPlayersLoup();
						LGPT.getTeamsHumain().addPlayer(p);
						LGPT.getPlayersHumain();	
						p.teleport(LGPC.SpawnHumain);
					}			
				}
				else {
					p.removePotionEffect(PotionEffectType.REGENERATION);
				}

				if(nmbHumain == 0) {
					LGPT.getTeamsSpectateur().addPlayer(Bukkit.getServer().getPlayer(""));
					Bukkit.getServer().broadcastMessage(ChatColor.BOLD+""+ChatColor.GREEN+"--------------------");
					Bukkit.getServer().broadcastMessage("");
					Bukkit.getServer().broadcastMessage(ChatColor.DARK_GRAY+"Les "+ChatColor.DARK_BLUE+"Loups "+ChatColor.DARK_GRAY+"ont gagnés.");
					Bukkit.getServer().broadcastMessage("");
					Bukkit.getServer().broadcastMessage(ChatColor.BOLD+""+ChatColor.GREEN+"--------------------");
					main.state = GameStates.end;
				}
				if (nmbLoup == 0) {
					LGPT.getTeamsSpectateur().addPlayer(Bukkit.getServer().getPlayer(""));
					Bukkit.getServer().broadcastMessage(ChatColor.BOLD+""+ChatColor.GREEN+"--------------------");
					Bukkit.getServer().broadcastMessage("");
					Bukkit.getServer().broadcastMessage(ChatColor.DARK_GRAY+"Les "+ChatColor.AQUA+"Humains "+ChatColor.DARK_GRAY+"ont gagnés.");
					Bukkit.getServer().broadcastMessage("");
					Bukkit.getServer().broadcastMessage(ChatColor.BOLD+""+ChatColor.GREEN+"--------------------");
					main.state = GameStates.end;
				}
			}
		}
	}



}
