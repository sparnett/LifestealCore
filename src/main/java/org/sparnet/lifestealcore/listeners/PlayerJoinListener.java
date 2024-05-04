package org.sparnet.lifestealcore.listeners;

import org.sparnet.lifestealcore.Data;
import org.sparnet.lifestealcore.LifestealCore;
import org.sparnet.lifestealcore.Util;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class PlayerJoinListener implements Listener {

    Plugin plugin = LifestealCore.getPlugin(LifestealCore.class);
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        //Set player to default health if they have never played before
        if(!player.hasPlayedBefore()) {
            Util.setHearts(player, plugin.getConfig().getDouble("DefaultHealth"));
            return;
        }

        //revive players if necessary
        if(Data.get().contains("revive." + player.getUniqueId())){
            player.setGameMode(GameMode.SURVIVAL);
            Util.setHearts(player, plugin.getConfig().getDouble("DefaultHealth"));
            if(player.getBedSpawnLocation() == null){
                e.getPlayer().teleport(e.getPlayer().getWorld().getSpawnLocation());
            }
            else{
                e.getPlayer().teleport(player.getBedSpawnLocation());
            }
            Data.get().set("revive." + player.getUniqueId(), null);
            Data.save();
        }
        //soft revive players if necessary
        if(Data.get().contains("softrevive." + player.getUniqueId())){
            Data.get().set("softrevive." + player.getUniqueId(), null);
            Util.setHearts(player, plugin.getConfig().getDouble("DefaultHealth"));
            Data.save();
        }

    }

}