package org.sparnet.lifestealcore.commands;

import org.sparnet.lifestealcore.Data;
import org.sparnet.lifestealcore.LifestealCore;
import org.sparnet.lifestealcore.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ReviveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Plugin plugin = LifestealCore.getPlugin(LifestealCore.class);

        //if nobody dead, cancel command
        try{
            Data.get().getConfigurationSection("dead").getKeys(false);
        }catch(NullPointerException ex){
            sender.sendMessage(ChatColor.RED + "There is no One Currently Dead");
            return true;
        }

        //if there is player arg and player exists and is dead, then revive them
        if(args.length == 1 && Data.get().contains("dead." + Bukkit.getOfflinePlayer(args[0]).getUniqueId())){
            OfflinePlayer dead = Bukkit.getOfflinePlayer(args[0]);

            //if player is online, reset them
            if(Bukkit.getPlayer(args[0]) != null){
                Player player = Bukkit.getPlayer(args[0]);
                player.setGameMode(GameMode.SURVIVAL);
                Util.setHearts(player, plugin.getConfig().getDouble("DefaultHealth"));
                if(player.getBedSpawnLocation() == null){
                    player.teleport(player.getWorld().getSpawnLocation());
                }
                else{
                    player.teleport(player.getBedSpawnLocation());
                }
            }
            //else add to revive list
            else{
                Data.get().set("revive." + dead.getUniqueId(), "");
            }
        }
        //else just pull up revive page
        else if(sender instanceof Player){
            Util.setRevivePage(1, (Player)sender);
        }
        else{
            sender.sendMessage(ChatColor.RED + "The Console must input a Player arg after \"/revive\"\nUsage: /revive [Player]");
        }
        return true;
    }
}