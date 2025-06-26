package org.notionsmp.magicalcampfireFabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class MagicalCampfireFabric implements ModInitializer {
    public static MagicalCampfireFabric INSTANCE;
    private MagicalCampfireSettings campfire;
    private MagicalCampfireSettings soulCampfire;
    private boolean enabled;
    private MinecraftServer server;

    @Override
    public void onInitialize() {
        INSTANCE = this;
        reloadConfig();

        ServerLifecycleEvents.SERVER_STARTED.register((MinecraftServer srv) -> {
            server = srv;
            startTimers();
        });
    }

    public void reloadConfig() {
        File configFile = new File("config/magicalcampfire.json");
        MagicalCampfireConfig config = MagicalCampfireConfig.load(configFile);
        enabled = config.enabled;
        campfire = config.campfire;
        soulCampfire = config.soulCampfire;
    }

    private void startTimers() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!enabled || !campfire.enabled) return;
                for (World world : server.getWorlds()) {
                    for (var player : world.getPlayers()) {
                        if (player instanceof ServerPlayerEntity serverPlayer)
                            handleCampfire(serverPlayer, Blocks.CAMPFIRE, campfire);
                    }
                }
            }
        }, 0, campfire.interval);

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!enabled || !soulCampfire.enabled) return;
                for (World world : server.getWorlds()) {
                    for (var player : world.getPlayers()) {
                        if (player instanceof ServerPlayerEntity serverPlayer)
                            handleCampfire(serverPlayer, Blocks.SOUL_CAMPFIRE, soulCampfire);
                    }
                }
            }
        }, 0, soulCampfire.interval);
    }

    private void handleCampfire(ServerPlayerEntity player, Block block, MagicalCampfireSettings settings) {
        BlockPos center = player.getBlockPos();
        World world = player.getWorld();
        boolean found = false;

        for (int dx = -settings.range; dx <= settings.range && !found; dx++) {
            for (int dy = -settings.range; dy <= settings.range && !found; dy++) {
                for (int dz = -settings.range; dz <= settings.range && !found; dz++) {
                    BlockPos pos = center.add(dx, dy, dz);
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() == block && state.contains(CampfireBlock.LIT) && state.get(CampfireBlock.LIT)) {
                        found = true;
                    }
                }
            }
        }

        if (found) {
            float newHealth = Math.min(player.getHealth() + (float) settings.amount, player.getMaxHealth());
            player.setHealth(newHealth);
            if (settings.actionbarEnabled) {
                player.sendMessage(Text.literal(settings.actionbarMessage), true);
            }
        }
    }
}
