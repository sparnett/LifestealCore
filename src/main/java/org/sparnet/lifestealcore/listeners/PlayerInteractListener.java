package org.sparnet.lifestealcore.listeners;

import org.sparnet.lifestealcore.Data;
import org.sparnet.lifestealcore.LifestealCore;
import org.sparnet.lifestealcore.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class PlayerInteractListener implements Listener {

    Plugin plugin = LifestealCore.getPlugin(LifestealCore.class);

    @EventHandler
    public void PlayerInteract(PlayerInteractEvent e) {

        //Heart Item Interact
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.NETHER_STAR) && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§cHeart")) {

                double pHealth = Util.getHearts(e.getPlayer());
                double maxHearts = plugin.getConfig().getDouble("MaxHealth");

                if(pHealth + 2 > maxHearts) {e.getPlayer().sendMessage(ChatColor.RED + "This Action Violates the \"Max Hearts\" Parameter."); return;}

                e.getPlayer().getInventory().getItemInMainHand().setAmount(e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
                Util.setHearts(e.getPlayer(), pHealth + 2);

            }
            //Revive Totem Interact
            else if(e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§aRevive Totem")){
                if(!(Data.get().getConfigurationSection("dead") == null)){
                    Util.setRevivePage(1, e.getPlayer());
                }
            }

        }

    }

}