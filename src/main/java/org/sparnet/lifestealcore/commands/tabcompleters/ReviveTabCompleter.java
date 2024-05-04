package org.sparnet.lifestealcore.commands.tabcompleters;

import org.sparnet.lifestealcore.Data;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static sun.security.util.KnownOIDs.Data;

public class ReviveTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        ArrayList<String> list = new ArrayList<>();

        //add tab completer for everyone on dead list
        if(args.length == 1){
            try{
                for(String uuid : org.sparnet.lifestealcore.Data.get().getConfigurationSection("dead").getKeys(false)){
                    list.add(Bukkit.getPlayer(UUID.fromString(uuid)).getDisplayName());
                }
            }catch(NullPointerException ex){
                // then just return an empty list
            }

        }
        return list;
    }
}