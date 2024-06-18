package fr.elowyr.dimension.listeners;

import fr.elowyr.dimension.ElowyrDimension;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FishingListener implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.FISHING) {

            int delayInTicks = 20;
            Bukkit.getScheduler().runTaskLater(ElowyrDimension.getInstance(), () -> {
                if (event.getState() == PlayerFishEvent.State.FISHING) {
                    event.setCancelled(true);

                    Player player = event.getPlayer();

                    Material[] fishTypes = {
                            Material.RAW_FISH,
                            Material.RAW_FISH,
                            Material.COOKED_FISH,
                    };

                    Random random = new Random();
                    Material randomFish = fishTypes[random.nextInt(fishTypes.length)];

                    player.getInventory().addItem(new ItemStack(randomFish, 1));
                }
            }, delayInTicks);
        }
    }
}
