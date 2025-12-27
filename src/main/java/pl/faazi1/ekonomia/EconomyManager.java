package pl.faazi1.ekonomia;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyManager {

    private final EconomyPlugin plugin;

    public EconomyManager(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    public double getBalance(Player player) {
        return getMoney(player);
    }

    public double getBalance(OfflinePlayer player) {
        return getMoney(player);
    }

    public double getMoney(OfflinePlayer player) {
        return plugin.getConfig().getDouble("players." + player.getUniqueId(), 0);
    }

    public void setMoney(OfflinePlayer player, double amount) {
        plugin.getConfig().set("players." + player.getUniqueId(), amount);
        plugin.saveConfig();
    }

    public void deposit(OfflinePlayer player, double amount) {
        setMoney(player, getMoney(player) + amount);
    }

    public void withdraw(OfflinePlayer player, double amount) {
        setMoney(player, Math.max(0, getMoney(player) - amount));
    }

    public boolean has(Player player, double amount) {
        return getMoney(player) >= amount;
    }

    public void saveAll() {
        plugin.saveConfig();
    }
}
