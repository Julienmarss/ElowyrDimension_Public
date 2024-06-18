package fr.elowyr.dimension.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onSpawnerSpawn(CreatureSpawnEvent event) {
        if (event.getEntity().getWorld().getName().equalsIgnoreCase("Mine") || event.getEntity().getWorld().getName().equalsIgnoreCase("dimension")) {
            if (event.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER || event.getEntity().getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
                event.setCancelled(false);
            } else {
                event.setCancelled(true);
            }
        }
    }
}
