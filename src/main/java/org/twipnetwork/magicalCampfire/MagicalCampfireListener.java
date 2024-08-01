package org.twipnetwork.magicalCampfire;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.type.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.Plugin;
import org.bukkit.Location;

public class MagicalCampfireListener implements Listener {
    private final Plugin plugin;

    public MagicalCampfireListener() {
        this.plugin = Bukkit.getPluginManager().getPlugin("MagicalCampfire");
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
        }.runTaskTimer(plugin, 0L, 40L);
    }

    private boolean isNearLitCampfire(Player player) {
        Location playerLoc = player.getLocation();
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    Location loc = playerLoc.clone().add(x, y, z);
                    if (loc.getBlock().getType() == Material.CAMPFIRE) {
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
