package fr.elowyr.dimension.commands;

import fr.elowyr.dimension.utils.Utils;
import fr.elowyr.dimension.utils.commands.Command;
import fr.elowyr.dimension.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TeleportCommand {

    @Command(name = "dimension", permission = "elowyrdimension.teleport.dimension")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.getArgs(0).equalsIgnoreCase("help")) {
            if (player.hasPermission("elowyrdimension.help")) {
                player.sendMessage(Utils.color("&b/dimension help &7- &fObtenir de l'aide."));
                player.sendMessage(Utils.color("&b/dimension &7- &fTéléportation vers la dimension"));
            } else {
                player.sendMessage(Utils.color("&6&lDimension &7&l• &cVous n'avez pas la permission."));
            }
        } else if (args.length() == 0 ){
            player.sendMessage(Utils.color("&b/dimension help &7- &fObtenir de l'aide."));
            player.sendMessage(Utils.color("&b/dimension ticket <pseudo> &7- &fTéléportation vers la dimension"));
        }
    }
}
