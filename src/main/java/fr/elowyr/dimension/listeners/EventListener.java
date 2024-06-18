package fr.elowyr.dimension.listeners;

import com.massivecraft.factions.FPlayers;
import fr.elowyr.dimension.ElowyrDimension;
import fr.elowyr.dimension.manager.EventManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!player.getWorld().getName().equalsIgnoreCase("dimension")) return;

        if (!EventManager.getInstance().isFinished()) {
            if (event.getBlock().getType() == Material.EMERALD_ORE) {
                event.setCancelled(true);
                EventManager.getInstance().scores.put(FPlayers.getInstance().getByPlayer(player).getFaction(),
                        EventManager.getInstance().getScore(FPlayers.getInstance().getByPlayer(player).getFaction()) + 1);
                event.getBlock().setType(Material.BEDROCK);
                Bukkit.getScheduler().runTaskLater(ElowyrDimension.getInstance(), () -> {
                    event.getBlock().setType(Material.EMERALD_ORE);
                }, 20L * 10);
            }
        }
    }
}
