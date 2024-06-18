package fr.elowyr.dimension;

import fr.elowyr.dimension.commands.*;
import fr.elowyr.dimension.listeners.EventListener;
import fr.elowyr.dimension.listeners.MobListener;
import fr.elowyr.dimension.listeners.SpawnerListener;
import fr.elowyr.dimension.manager.EventManager;
import fr.elowyr.dimension.utils.commands.CommandFramework;
import org.bukkit.plugin.java.JavaPlugin;

public class ElowyrDimension extends JavaPlugin {
    private static ElowyrDimension instance;

    private final CommandFramework commandFramework = new CommandFramework(this);

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        new EventManager();
        registerListeners();
        registerCommands();
    }

    public void registerCommands() {
        commandFramework.registerCommands(new TeleportCommand());
        commandFramework.registerCommands(new TicketCommand());
        commandFramework.registerCommands(new EventStartCommand());
        commandFramework.registerCommands(new EventStopCommand());
    }

    public void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new MobListener(), this);
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        this.getServer().getPluginManager().registerEvents(new SpawnerListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static ElowyrDimension getInstance() {
        return instance;
    }
}
