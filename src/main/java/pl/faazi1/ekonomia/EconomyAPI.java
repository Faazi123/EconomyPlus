package pl.faazi1.ekonomia;

import org.bukkit.entity.Player;

public interface EconomyAPI {
    double getBalance(Player player);
    boolean has(Player player, double amount);
    void deposit(Player player, double amount);
    boolean withdraw(Player player, double amount);
}
