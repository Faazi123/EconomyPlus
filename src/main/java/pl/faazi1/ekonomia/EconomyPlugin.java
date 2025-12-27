package pl.faazi1.ekonomia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.faazi1.ekonomia.commands.EconomyCommand;

public class EconomyPlugin extends JavaPlugin {

    private EconomyManager economyManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        economyManager = new EconomyManager(this);

        getCommand("economy").setExecutor(new EconomyCommand(this));
        getCommand("economy").setTabCompleter((TabCompleter) getCommand("economy").getExecutor());

        getCommand("pay").setExecutor(new PlayerCommandExecutor(this));
        getCommand("pay").setTabCompleter((TabCompleter) getCommand("pay").getExecutor());


        getCommand("konto").setExecutor(new PlayerCommandExecutor(this));
        getCommand("pay").setExecutor(new PlayerCommandExecutor(this));

        // komenda admina
        getCommand("economy").setExecutor(new EconomyCommand(this));

        // PlaceholderAPI
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new EconomyPlaceholderHook(this).register();
            getLogger().info("PlaceholderAPI hook aktywny!");
        }

    }

    @Override
    public void onDisable() {
        economyManager.saveAll();
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    public double getBalance(org.bukkit.entity.Player player) {
        return economyManager.getBalance(player);
    }
}
