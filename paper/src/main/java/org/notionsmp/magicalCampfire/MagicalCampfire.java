package org.notionsmp.magicalCampfire;

import co.aikar.commands.PaperCommandManager;
import com.tcoded.folialib.FoliaLib;
import org.bukkit.plugin.java.JavaPlugin;

public final class MagicalCampfire extends JavaPlugin {
    private static MagicalCampfire instance;
    private MagicalCampfireListener listener;
    public FoliaLib foliaLib;

    @Override
    public void onEnable() {
        foliaLib = new FoliaLib(this);
        instance = this;
        saveDefaultConfig();
        listener = new MagicalCampfireListener();
        getServer().getPluginManager().registerEvents(listener, this);
        PaperCommandManager manager = new PaperCommandManager(this);
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
