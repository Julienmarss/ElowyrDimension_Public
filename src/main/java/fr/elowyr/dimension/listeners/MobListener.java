package fr.elowyr.dimension.listeners;

import fr.elowyr.dimension.ElowyrDimension;
import fr.elowyr.dimension.utils.CustomHeads;
import fr.elowyr.dimension.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class MobListener implements Listener {

    private ItemStack helmetZombie = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ0NTIxZjUyMGViOWY2ZTE3NzkwODhiMzgxYjRkZTQwNDQ3NWVlNDllN2M5Y2JkOTE2NmMxOTk2YTNiNWVmNyJ9fX0=");
    private ItemStack chestplateZombie = new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.GRAY).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();
    private ItemStack leggingsZombie = new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.GRAY).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();
    private ItemStack bootsZombie = new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.GRAY).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();

    private ItemStack helmetSkeleton = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODQ0NTIxZjUyMGViOWY2ZTE3NzkwODhiMzgxYjRkZTQwNDQ3NWVlNDllN2M5Y2JkOTE2NmMxOTk2YTNiNWVmNyJ9fX0=");
    private ItemStack chestplateSkeleton = new ItemBuilder(Material.LEATHER_CHESTPLATE).setLeatherArmorColor(Color.PURPLE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();
    private ItemStack leggingsSkeleton = new ItemBuilder(Material.LEATHER_LEGGINGS).setLeatherArmorColor(Color.PURPLE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();
    private ItemStack bootsSkeleton = new ItemBuilder(Material.LEATHER_BOOTS).setLeatherArmorColor(Color.PURPLE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4).toItemStack();

    @EventHandler
    public void onMobSpawning(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity == null) return;
        if (!entity.getWorld().getName().equalsIgnoreCase("dimension")) return;
        EntityEquipment entityEquipment = entity.getEquipment();
        if (entity instanceof Zombie) {
            entityEquipment.setHelmet(helmetZombie);
            entityEquipment.setChestplate(chestplateZombie);
            entityEquipment.setLeggings(leggingsZombie);
            entityEquipment.setBoots(bootsZombie);
            entity.setHealth(20);
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', ElowyrDimension.getInstance().getConfig().getString("name.zombie")));
            entity.setCustomNameVisible(true);
            ((Zombie) entity).setBaby(false);
            ((Zombie) entity).setVillager(false);
        } else if (entity instanceof Skeleton) {
            entityEquipment.setHelmet(helmetSkeleton);
            entityEquipment.setChestplate(chestplateSkeleton);
            entityEquipment.setLeggings(leggingsSkeleton);
            entityEquipment.setBoots(bootsSkeleton);
            entity.setHealth(20);
            entity.setCustomName(ChatColor.translateAlternateColorCodes('&', ElowyrDimension.getInstance().getConfig().getString("name.skeleton")));
            entity.setCustomNameVisible(true);
        } else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeathMob(EntityDeathEvent event) {
        if (!event.getEntity().getWorld().getName().equalsIgnoreCase("dimension")) return;

        if (event.getEntityType() == EntityType.ZOMBIE || event.getEntityType() == EntityType.SKELETON) {
            event.getDrops().clear();
        }
    }
}
