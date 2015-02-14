package fr.lgp.utils;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.lgp.main.main;

public class LGPItems {

	public main plugin;

	public LGPItems(main plugin){
		this.plugin = plugin;
	}

	public enum Item {
		ItemHumain, ItemLoup, 
		EpeeHumain, BlocHumain, BarrierHumain, PotionHumain, ChestplateHumain, LegginHumain, BootsHumain, axeHumain,
		EpeeLoup, BlocLoup, LadderLoup, PotionLoup, ChestplateLoup, LegginLoup, BootsLoup, PickaxeLoup;
	}

	public void GiveItemTeam() {
		Player player = Bukkit.getServer().getPlayer("");
		if(player == null){
		}

		else if (player.getInventory().containsAtLeast(CustomItem(Item.ItemHumain), 0) && player.getInventory().containsAtLeast(CustomItem(Item.ItemLoup), 1)) {
		}

		else {
			player.getInventory().clear();
			player.getInventory().setItem(0, CustomItem(Item.ItemHumain));
			player.getInventory().setItem(1, CustomItem(Item.ItemLoup));
		}

	}

	public void ItemHumain(Player p) {
		p.getInventory().clear();
		p.getInventory().addItem(CustomItem(Item.EpeeHumain));
		p.getInventory().addItem(CustomItem(Item.BlocHumain));
		p.getInventory().addItem(CustomItem(Item.BarrierHumain));
		p.getInventory().addItem(CustomItem(Item.axeHumain));
		p.getInventory().addItem(CustomItem(Item.PotionHumain));
		p.getInventory().setChestplate(CustomItem(Item.ChestplateHumain));
		p.getInventory().setLeggings(CustomItem(Item.LegginHumain));
		p.getInventory().setBoots(CustomItem(Item.BootsHumain));
		p.setFoodLevel(20);
	}

	public void ItemLoup(Player p) {
		p.getInventory().clear();
		p.getInventory().addItem(CustomItem(Item.EpeeLoup));
		p.getInventory().addItem(CustomItem(Item.BlocLoup));
		p.getInventory().addItem(CustomItem(Item.LadderLoup));
		p.getInventory().addItem(CustomItem(Item.PickaxeLoup));
		p.getInventory().addItem(CustomItem(Item.PotionLoup));
		p.getInventory().setChestplate(CustomItem(Item.ChestplateLoup));
		p.getInventory().setLeggings(CustomItem(Item.LegginLoup));
		p.getInventory().setBoots(CustomItem(Item.BootsLoup));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5000, 0));
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 5000, 0));
		p.setFoodLevel(20);
	}

	public ItemStack CustomItem(Item item) {
		ItemStack is = null;
		ItemMeta im;
		ArrayList<String> lore;
		switch (item) {

		case ItemHumain :
			is = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
			im = is.getItemMeta() ;
			im.setDisplayName(ChatColor.AQUA+"Team Humain"+ChatColor.GRAY+" ( Right Click )");
			lore = new ArrayList<String>() ;
			//			lore.add("") ;
			im.setLore(lore);
			is.setItemMeta(im) ;
			break;
		case ItemLoup :
			is = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.WITHER.ordinal());
			im = is.getItemMeta() ;
			im.setDisplayName(ChatColor.DARK_BLUE+"Team Loup"+ChatColor.GRAY+" ( Right Click )");
			lore = new ArrayList<String>() ;
			//			lore.add("") ;
			im.setLore(lore);
			is.setItemMeta(im) ;
			break;
		case BarrierHumain:
			is = new ItemStack(Material.NETHER_FENCE, 64);
			im = is.getItemMeta() ;
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			break;
		case LadderLoup:
			is = new ItemStack(Material.LADDER, 64);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			is.setItemMeta(im) ;
			break;
		case BlocHumain:
			is = new ItemStack(Material.NETHER_BRICK, 64);
			im = is.getItemMeta() ;
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			break;
		case BlocLoup:
			is = new ItemStack(Material.SPRUCE_WOOD_STAIRS, 64);
			im = is.getItemMeta() ;
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			is.setItemMeta(im) ;
			break;
		case BootsHumain:
			is = new ItemStack(Material.LEATHER_BOOTS, 1);
			LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta.setColor(Color.AQUA);
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			is.setItemMeta(meta);
			break;
		case BootsLoup:
			is = new ItemStack(Material.LEATHER_BOOTS, 1);
			LeatherArmorMeta meta1 = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta1.setColor(Color.BLACK);
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			is.setItemMeta(im);
			is.setItemMeta(meta1);
			break;
		case ChestplateHumain:
			is = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta meta2 = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta2.setColor(Color.AQUA);
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			is.setItemMeta(meta2);
			break;
		case ChestplateLoup:
			is = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
			LeatherArmorMeta meta3 = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta3.setColor(Color.BLACK);
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			is.setItemMeta(im) ;
			is.setItemMeta(meta3);
			break;
		case EpeeHumain:
			is = new ItemStack(Material.IRON_SWORD);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			im.addEnchant(Enchantment.KNOCKBACK, 1, true);
			is.setItemMeta(im) ;
			break;
		case EpeeLoup:
			is = new ItemStack(Material.WOOD_SWORD);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			im.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
			is.setItemMeta(im) ;
			break;
		case axeHumain:
			is = new ItemStack(Material.IRON_AXE);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			break;
		case PickaxeLoup:
			is = new ItemStack(Material.WOOD_PICKAXE);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			im.addEnchant(Enchantment.DIG_SPEED, 3, true);
			is.setItemMeta(im) ;
			break;
		case LegginHumain:
			is = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta meta4 = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta4.setColor(Color.AQUA);
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			is.setItemMeta(im) ;
			is.setItemMeta(meta4);
			break;
		case LegginLoup:
			is = new ItemStack(Material.LEATHER_LEGGINGS, 1);
			LeatherArmorMeta meta5 = (LeatherArmorMeta) is.getItemMeta();
			im = is.getItemMeta();
			meta5.setColor(Color.BLACK);
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			is.setItemMeta(im) ;
			is.setItemMeta(meta5);
			break;
		case PotionHumain:
			is = new ItemStack(Material.POTION, 1, (short) 16385);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.AQUA+is.getType());
			lore = new ArrayList<String>() ;
			//			lore.add("") ;
			im.setLore(lore);
			is.setItemMeta(im);
			break;
		case PotionLoup:
			is = new ItemStack(Material.POTION, 1, (short) 16460);
			im = is.getItemMeta();
			im.setDisplayName(""+ChatColor.DARK_BLUE+is.getType());
			lore = new ArrayList<String>();
			//			lore.add("") ;
			im.setLore(lore);
			is.setItemMeta(im);
			break;
		default:
			break;
		}

		return is;

	}

}
