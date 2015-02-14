package fr.lgp.scoreboards;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import fr.lgp.main.main;

public class LGPTeams implements Listener {

	public main plugin;	
	public ArrayList<Player> playersHumain = new ArrayList<Player>();
	public ArrayList<Player> playersLoup = new ArrayList<Player>();
	public ArrayList<Player> playersSpectateur = new ArrayList<Player>();

	public LGPTeams(main plugin) {
		this.plugin = plugin;
	}

	public Team getTeamsSpectateur() {
		Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();

		Team spectateur = main.getTeam("Spectateur");
		if (spectateur == null) {
			main.registerNewTeam("Spectateur");
		}
		spectateur.setPrefix(ChatColor.GRAY+"[Spectateur] ");
		spectateur.setDisplayName("Spectateur");
		return spectateur;	
	}

	public Team getTeamsHumain() {
		Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();

		Team humain = main.getTeam("Humain");
		if (humain == null) {
			main.registerNewTeam("Humain");
		}
		humain.setPrefix(ChatColor.AQUA+"[Humain] ");
		humain.setDisplayName("Humain");

		return humain;	
	}

	public Team getTeamsLoup() {
		Scoreboard main = Bukkit.getScoreboardManager().getMainScoreboard();

		Team loup = main.getTeam("Loup");
		if (loup == null) {
			main.registerNewTeam("Loup");
		}
		loup.setPrefix(ChatColor.DARK_BLUE+"[Loup] ");
		loup.setDisplayName("Loup");
		return loup;
	}

	public int nmbHumain() {
		int nmbHumain = getTeamsHumain().getPlayers().size();
		if (nmbHumain <= -1) {
			nmbHumain = 0;
		}
		return nmbHumain;
	}

	public int nmbLoup() {
		int nmbLoup = getTeamsLoup().getPlayers().size();
		if (nmbLoup <= -1) {
			nmbLoup = 0;
		}
		return nmbLoup;
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();

		if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getItemInHand().equals(main.LGPI.CustomItem(fr.lgp.utils.LGPItems.Item.ItemHumain))) {
			if (getTeamsHumain().hasPlayer(p)){
				p.sendMessage(main.préfixe+ChatColor.RED+"Vous êtes déjà dans la team "+ChatColor.AQUA+"humain");
			}
			else if (getTeamsLoup().hasPlayer(p)) {
				p.sendMessage(main.préfixe+"Vous êtes maintenant dans la team "+ChatColor.AQUA+"humain");
				getTeamsLoup().removePlayer(p);
				playersLoup.remove(p);
				getTeamsHumain().addPlayer(p);

			}
			else {
				p.sendMessage(main.préfixe+"Bienvenue dans la team "+ChatColor.AQUA+"humain");
				getTeamsHumain().addPlayer(p);
				playersHumain.add(p);
			}
		}

		else if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getItemInHand().equals(main.LGPI.CustomItem(fr.lgp.utils.LGPItems.Item.ItemLoup))) {
			if (getTeamsLoup().hasPlayer(p)) {
				p.sendMessage(main.préfixe+ChatColor.RED+"Vous êtes déjà dans la team "+ChatColor.DARK_BLUE+"loup");
			}
			else if (getTeamsHumain().hasPlayer(p)) {
				p.sendMessage(main.préfixe+"Vous êtes maintenant dans la team "+ChatColor.DARK_BLUE+"loup");
				getTeamsHumain().removePlayer(p);
				playersHumain.remove(p);
				getTeamsLoup().addPlayer(p);
				playersLoup.add(p);
			}
			else {
				p.sendMessage(main.préfixe+"Bienvenue dans la team "+ChatColor.DARK_BLUE+"loup");
				getTeamsLoup().addPlayer(p);
				playersLoup.add(p);
			}
		}

	}

	public ArrayList<Player> getPlayersHumain() {
		return playersHumain;

	}
	public ArrayList<Player> getPlayersLoup() {
		return playersLoup;

	}
	public ArrayList<Player> getPlayersSpectateur() {
		return playersSpectateur;

	}
	public void teleportHumain(Location lo) {
		for (Player p : getPlayersHumain()) {
			p.teleport(lo);
		}
	}
	public void teleportLoup(Location lo) {
		for (Player p : getPlayersLoup()) {
			p.teleport(lo);
		}
	}
	public void teleportSpectateur(Location lo) {
		for (Player p : getPlayersSpectateur()) {
			p.teleport(lo);
		}
	}

	public void removeHumain() {
		for(Player p : getPlayersHumain()) {
			getTeamsHumain().removePlayer(p);
		}
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			getTeamsHumain().removePlayer(p);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			getTeamsHumain().removePlayer(p);
		}
	}
	public void removeLoup() {
		for(Player p : getPlayersLoup()) {
			getTeamsLoup().removePlayer(p);
		}
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			getTeamsLoup().removePlayer(p);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			getTeamsLoup().removePlayer(p);
		}
	}
	public void removeSpectateur() {
		for(Player p : getPlayersSpectateur()) {
			getTeamsSpectateur().removePlayer(p);
		}
		for(OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			getTeamsSpectateur().removePlayer(p);
		}
		for(Player p : Bukkit.getOnlinePlayers()) {
			getTeamsSpectateur().removePlayer(p);
		}
	}

}
