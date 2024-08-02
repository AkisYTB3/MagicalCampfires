package org.twipnetwork.magicalCampfire;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.Location;

public class MagicalCampfireListener implements Listener {
    private final Plugin plugin;
    private final int regenInterval;
    private final boolean worksWithSoul;

    public MagicalCampfireListener(Plugin plugin) {
        this.plugin = plugin;
        FileConfiguration config = plugin.getConfig();
        this.regenInterval = config.getInt("regenInterval", 40);
        this.worksWithSoul = config.getBoolean("worksWithSoul", false);
        startRegenTask();
    }

    private void startRegenTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (isNearLitCampfire(player)) {
                        double newHealth = Math.min(player.getHealth() + 0.5, player.getMaxHealth());
                        player.setHealth(newHealth);
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, regenInterval);
    }

    private boolean isNearLitCampfire(Player player) {
        Location playerLoc = player.getLocation();
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Location loc = playerLoc.clone().add(x, y, z);
                    if (loc.getBlock().getType() == Material.CAMPFIRE || (worksWithSoul && loc.getBlock().getType() == Material.SOUL_CAMPFIRE)) {
                        Campfire campfire = (Campfire) loc.getBlock().getBlockData();
                        if (campfire.isLit()) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
