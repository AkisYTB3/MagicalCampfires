package org.notionsmp.magicalCampfire;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.CommandPermission;
import org.bukkit.command.CommandSender;

@CommandAlias("campfire")
public class CampfireCommand extends BaseCommand {

    @Subcommand("reload")
    @CommandPermission("magicalcampfire.reload")
    public void onReload(CommandSender sender) {
        MagicalCampfire.getInstance().reload();
        sender.sendMessage("§aMagicalCampfire config reloaded.");
    }
}
