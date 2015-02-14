package fr.lgp.scoreboards;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import fr.lgp.main.main;
import fr.lgp.utils.GameStates;

public class LGPScoreboard {

	public main plugin;
	public LGPTeams LGPT;
	private NumberFormat formatter = new DecimalFormat("00");
	public Integer secondsLeft = 0;
	private static String préfixe = main.préfixe;

	public LGPScoreboard(main plugin) {
		this.plugin = plugin;
	}

	public void Timer() {

		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin , new BukkitRunnable() {

			@Override
			public void run() {
				if (main.state == GameStates.game) {
					if (main.minutesLeft != -2) {
						main.LGPS.Scoreboard();
						secondsLeft--;
						main.Game();
					}
					if (secondsLeft == -1) {
						main.minutesLeft--;
						secondsLeft = 59;
						main.Game();
					}
					if (main.minutesLeft == -1) {
						main.minutesLeft = -2;
						secondsLeft = 0;
						Bukkit.getServer().broadcastMessage(préfixe +ChatColor.RED+"Temps écouler! Fin de la partie!");
					}
				}
			}
		}, 20L, 20L);

	}

	@SuppressWarnings("static-access")
	public void Scoreboard() {
		Scoreboard sBoard = main.sbManager.getNewScoreboard();
		int nmbHumain = main.LGPT.nmbHumain();
		int nmbLoup = main.LGPT.nmbLoup();
		Objective obj = sBoard.registerNewObjective("LGPlugin", "dummy");
		obj.setDisplayName(ChatColor.GRAY+" -- "+""+ChatColor.GOLD+"Loup Garou"+""+ChatColor.GRAY+" -- ");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);

		for (Player p : Bukkit.getServer().getOnlinePlayers()){
			p.setScoreboard(main.sbManager.getNewScoreboard());
			p.setScoreboard(sBoard);
		}	

		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.DARK_BLUE+"Team Loup")).setScore(8);
		obj.getScore(Bukkit.getOfflinePlayer("Players :"+ChatColor.WHITE+" "+nmbLoup)).setScore(7);
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY+"---------- ")).setScore(6);
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.AQUA+"Team Humain")).setScore(5);
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.WHITE+"Players : "+nmbHumain)).setScore(4);
		obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GRAY+"----------")).setScore(3);
		obj.getScore(Bukkit.getOfflinePlayer("")).setScore(2);

		if (main.minutesLeft == 0 && secondsLeft == 0) {
			obj.getScore(Bukkit.getOfflinePlayer(ChatColor.RED+"00"+ChatColor.GRAY+":"+ChatColor.RED+"00")).setScore(1);
		}
		else {
			obj.getScore(Bukkit.getOfflinePlayer(ChatColor.GREEN+formatter.format(plugin.minutesLeft)+ChatColor.GRAY+":"+ChatColor.GREEN+formatter.format(this.secondsLeft))).setScore(1);		
		}

	}

}
