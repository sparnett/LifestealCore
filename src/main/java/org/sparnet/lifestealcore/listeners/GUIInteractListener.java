package org.sparnet.lifestealcore.listeners;

import org.sparnet.lifestealcore.Data;
import org.sparnet.lifestealcore.LifestealCore;
import org.sparnet.lifestealcore.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class GUIInteractListener implements Listener {
    @EventHandler
    public void GUIInteract(InventoryClickEvent e){
        Plugin plugin = LifestealCore.getPlugin(LifestealCore.class);


        //Check if Correct Inventory
        if(!e.getView().getTitle().equals("Select a Player:")) return;
        //Check if player is even clicking on an item
        if(e.getCurrentItem() == null) return;
        //check if correct inventory (2)
        if(!e.getClickedInventory().equals(e.getView().getTopInventory())) {e.setCancelled(true); return;}
        //Check if Correct Action
        if(!e.getAction().equals(InventoryAction.PICKUP_ALL)) {e.setCancelled(true); return;}

        //set name var
        String name = e.getCurrentItem().getItemMeta().getDisplayName();

        //Check if they want to change page
        if(name.equals("<-- Back")){
            Util.setRevivePage(Integer.parseInt(e.getInventory().getItem(4).getItemMeta().getDisplayName())-1, (Player)e.getView().getPlayer());
            e.setCancelled(true);
            return;
        }
        else if(name.equals("Forward -->")){
            Util.setRevivePage(Integer.parseInt(e.getInventory().getItem(4).getItemMeta().getDisplayName())+1, (Player)e.getView().getPlayer());
            e.setCancelled(true);
            return;
        }
        //check if player is clicking on stuff they shouldn't be
        else if(e.getSlot() > 0 && e.getSlot() < 8){
            e.setCancelled(true);
            return;
        }
        //if Player was holding Revive Totem, remove it
        if(e.getView().getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TOTEM_OF_UNDYING) && e.getView().getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§aRevive Totem")){
            e.getView().getPlayer().getInventory().getItemInMainHand().setAmount(e.getView().getPlayer().getInventory().getItemInMainHand().getAmount()-1);
        }

        //if player is online, reset them
        if(Bukkit.getPlayer(name) != null){
            e.getView().close();
            Bukkit.getPlayer(name).setGameMode(GameMode.SURVIVAL);
            Util.setHearts(Bukkit.getPlayer(name), plugin.getConfig().getDouble("DefaultHealth"));
            if(Bukkit.getPlayer(name).getBedSpawnLocation() == null){
                Bukkit.getPlayer(name).teleport(Bukkit.getPlayer(name).getWorld().getSpawnLocation());
            }
            else{
                Bukkit.getPlayer(name).teleport(Bukkit.getPlayer(name).getBedSpawnLocation());
            }
        }
        //else add to revive list
        else{
            Data.get().set("revive." + Bukkit.getOfflinePlayer(name).getUniqueId(), "");
        }
        e.getView().close();
        Data.get().set("dead." + Bukkit.getOfflinePlayer(name).getUniqueId(), null);
        Data.save();
        e.setCancelled(true);
    }
}