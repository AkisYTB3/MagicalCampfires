package org.notionsmp.magicalCampfire;

import co.aikar.commands.BukkitCommandManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicalCampfire extends JavaPlugin {
    private static MagicalCampfire instance;
    private MagicalCampfireListener listener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        listener = new MagicalCampfireListener();
        getServer().getPluginManager().registerEvents(listener, this);
        BukkitCommandManager manager = new BukkitCommandManager(this);
        manager.registerCommand(new CampfireCommand());
    }

    @Override
    public void onDisable() {
    }

    public void reload() {
        reloadConfig();
        listener.reload();
    }

    public static MagicalCampfire getInstance() {
        return instance;
    }
}
