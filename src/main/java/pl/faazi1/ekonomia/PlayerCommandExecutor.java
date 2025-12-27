package pl.faazi1.ekonomia;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerCommandExecutor implements CommandExecutor, TabCompleter {

    private final EconomyPlugin plugin;

    public PlayerCommandExecutor(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Tylko gracze mogą używać tej komendy.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("konto")) {
            double balance = plugin.getBalance(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&fTwoje saldo: &a%Economy_balance%")
                    .replace("%Economy_balance%", String.valueOf(balance)));
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (args.length != 2) {
                player.sendMessage(ChatColor.RED + "Poprawne użycie: /pay <gracz> <ilość>");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(ChatColor.RED + "Gracz nie jest online.");
                return true;
            }

            // --- Blokada wysyłania sobie pieniędzy ---
            if (target.getUniqueId().equals(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Nie możesz wysyłać pieniędzy samemu sobie.");
                return true;
            }

            double amount;
            try {
                amount = Double.parseDouble(args[1]);
                if (amount <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                player.sendMessage(ChatColor.RED + "Podaj poprawną liczbę większą od 0.");
                return true;
            }

            if (!plugin.getEconomyManager().has(player, amount)) {
                player.sendMessage(ChatColor.RED + "Nie masz wystarczająco pieniędzy.");
                return true;
            }

            plugin.getEconomyManager().withdraw(player, amount);
            plugin.getEconomyManager().deposit(target, amount);

            player.sendMessage(ChatColor.GREEN + "Przelałeś " + amount + " monet do " + target.getName());
            target.sendMessage(ChatColor.GREEN + "Otrzymałeś " + amount + " monet od " + player.getName());
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player player)) return Collections.emptyList();

        List<String> completions = new ArrayList<>();

        if (cmd.getName().equalsIgnoreCase("pay")) {
            if (args.length == 1) {
                // nazwy wszystkich graczy online
                for (Player p : Bukkit.getOnlinePlayers()) {
                    completions.add(p.getName());
                }
            }
        }

        return completions;
    }
}
