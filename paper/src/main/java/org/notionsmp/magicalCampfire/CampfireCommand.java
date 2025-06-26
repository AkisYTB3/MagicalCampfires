package org.notionsmp.magicalCampfire;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.CommandPermission;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

@CommandAlias("campfire")
public class CampfireCommand extends BaseCommand {

    @Subcommand("reload")
    @CommandPermission("magicalcampfire.reload")
    public void onReload(CommandSender sender) {
        MagicalCampfire.getInstance().reload();
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<green>MagicalCampfire config reloaded."));
    }
}
