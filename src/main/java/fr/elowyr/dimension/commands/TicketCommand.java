package fr.elowyr.dimension.commands;

import fr.elowyr.dimension.ElowyrDimension;
import fr.elowyr.dimension.utils.ItemBuilder;
import fr.elowyr.dimension.utils.Utils;
import fr.elowyr.dimension.utils.commands.Command;
import fr.elowyr.dimension.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TicketCommand {

    @Command(name = "dimension.ticket", permission = "elowyrdimension.ticket.give")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() != 1) {
            player.sendMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("MESSAGES.TICKET.USAGE")));
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));

        if (target == null) {
            player.sendMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("MESSAGES.TICKET.NO-PLAYER")));
            return;
        }

        target.getInventory().addItem(new ItemStack(new ItemBuilder(Material.PAPER)
                .setName(Utils.color(ElowyrDimension.getInstance().getConfig().getString("TICKET.NAME")))
                .setLore(Utils.color(ElowyrDimension.getInstance().getConfig().getStringList("TICKET.LORE")))
                .toItemStack()));
    }
}
