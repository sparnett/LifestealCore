package org.sparnet.lifestealcore;


import org.sparnet.lifestealcore.commands.AddHeartsCommand;
import org.sparnet.lifestealcore.commands.ResetCommand;
import org.sparnet.lifestealcore.commands.ReviveCommand;
import org.sparnet.lifestealcore.commands.WithdrawCommand;
import org.sparnet.lifestealcore.commands.tabcompleters.AddHeartsTabCompleter;
import org.sparnet.lifestealcore.commands.tabcompleters.ResetTabCompleter;
import org.sparnet.lifestealcore.commands.tabcompleters.ReviveTabCompleter;
import org.sparnet.lifestealcore.commands.tabcompleters.WithdrawTabCompleter;
import org.sparnet.lifestealcore.items.HeartItem;
import org.sparnet.lifestealcore.items.ReviveTotemItem;
import org.sparnet.lifestealcore.listeners.GUIInteractListener;
import org.sparnet.lifestealcore.listeners.PlayerInteractListener;
import org.sparnet.lifestealcore.listeners.PlayerJoinListener;
import org.sparnet.lifestealcore.listeners.PlayerKilledListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifestealCore extends JavaPlugin {

    @Override
    public void onEnable() {

        //register events
        getServer().getPluginManager().registerEvents(new PlayerKilledListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new GUIInteractListener(), this);

        //register commands
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("smpreset").setExecutor(new ResetCommand());
        getCommand("addhearts").setExecutor(new AddHeartsCommand());
        getCommand("revive").setExecutor(new ReviveCommand());

        //register tab completers
        getCommand("withdraw").setTabCompleter(new WithdrawTabCompleter());
        getCommand("smpreset").setTabCompleter(new ResetTabCompleter());
        getCommand("addhearts").setTabCompleter(new AddHeartsTabCompleter());
        getCommand("revive").setTabCompleter(new ReviveTabCompleter());

        //set config defaults
        getConfig().addDefault("DefaultHealth", 20.0);
        getConfig().addDefault("HealthGainedOnKill", 2.0);
        getConfig().addDefault("LoseLifeIfNotKilledByPlayer", false);
        getConfig().addDefault("BanIfLoseAllLives", false);
        getConfig().addDefault("MaxHealth", 40.0);
        getConfig().addDefault("DropHeartsOnDeath", true);

        getConfig().addDefault("HeartRecipe.Slot0", "DIAMOND_BLOCK");
        getConfig().addDefault("HeartRecipe.Slot1", "OBSIDIAN");
        getConfig().addDefault("HeartRecipe.Slot2", "DIAMOND_BLOCK");
        getConfig().addDefault("HeartRecipe.Slot3", "GOLD_BLOCK");
        getConfig().addDefault("HeartRecipe.Slot4", "NETHERITE_INGOT");
        getConfig().addDefault("HeartRecipe.Slot5", "GOLD_BLOCK");
        getConfig().addDefault("HeartRecipe.Slot6", "DIAMOND_BLOCK");
        getConfig().addDefault("HeartRecipe.Slot7", "OBSIDIAN");
        getConfig().addDefault("HeartRecipe.Slot8", "DIAMOND_BLOCK");

        getConfig().addDefault("ReviveTotemRecipe.Slot0", "TOTEM_OF_UNDYING");
        getConfig().addDefault("ReviveTotemRecipe.Slot1", "DIAMOND_BLOCK");
        getConfig().addDefault("ReviveTotemRecipe.Slot2", "TOTEM_OF_UNDYING");
        getConfig().addDefault("ReviveTotemRecipe.Slot3", "NETHERITE_INGOT");
        getConfig().addDefault("ReviveTotemRecipe.Slot4", "EMERALD");
        getConfig().addDefault("ReviveTotemRecipe.Slot5", "NETHERITE_INGOT");
        getConfig().addDefault("ReviveTotemRecipe.Slot6", "TOTEM_OF_UNDYING");
        getConfig().addDefault("ReviveTotemRecipe.Slot7", "DIAMOND_BLOCK");
        getConfig().addDefault("ReviveTotemRecipe.Slot8", "TOTEM_OF_UNDYING");

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        //set data up
        Data.setup();
        Data.get().addDefault("revive", " ");
        Data.get().addDefault("dead", " ");
        Data.get().addDefault("softrevive", " ");
        Data.get().options().copyDefaults(true);
        Data.save();

        //register recipes
        Bukkit.addRecipe(HeartItem.getHeartRecipe());
        Bukkit.addRecipe(ReviveTotemItem.getReviveTotemRecipe());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}

