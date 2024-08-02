package org.twipnetwork.magicalCampfire;

import org.bukkit.plugin.java.JavaPlugin;

public final class MagicalCampfire extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new MagicalCampfireListener(this), this);
        getLogger().info("MagicalCampfire Plugin Enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MagicalCampfire Plugin Disabled!");
    }
}