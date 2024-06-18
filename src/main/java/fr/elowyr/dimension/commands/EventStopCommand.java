package fr.elowyr.dimension.commands;

import fr.elowyr.dimension.ElowyrDimension;
import fr.elowyr.dimension.manager.EventManager;
import fr.elowyr.dimension.utils.Utils;
import fr.elowyr.dimension.utils.commands.Command;
import fr.elowyr.dimension.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class EventStopCommand {

    @Command(name = "eventminage.stop", permission = "elowyrdimension.event.stop")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (EventManager.getInstance().isFinished()) {
            player.sendMessage(Utils.color(ElowyrDimension.getInstance().getConfig().getString("EVENT.ALREADY-FINISH")));
            return;
        }

        EventManager.getInstance().setFinished(true);
        EventManager.getInstance().onFinish();
    }
}
