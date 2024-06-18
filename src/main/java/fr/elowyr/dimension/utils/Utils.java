package fr.elowyr.dimension.utils;

import com.massivecraft.factions.Faction;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class Utils {

    public static String color(final String value) {
        if (value == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public static List<String> color(final List<String> value) {
        final ListIterator<String> iterator = value.listIterator();
        while (iterator.hasNext()) {
            iterator.set(color(iterator.next()));
        }
        return value;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        int range = max - min + 1;
        int randomNum = random.nextInt(range) + min;
        return randomNum;
    }

    public static boolean isDefaultFaction(Faction faction) {
        return faction.isNone() || faction.isPeaceful() || faction.isSafeZone() || faction.isWarZone() || !faction.isNormal();
    }

}
