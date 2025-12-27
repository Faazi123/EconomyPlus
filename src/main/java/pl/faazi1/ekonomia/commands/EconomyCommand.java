package pl.faazi1.ekonomia.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.faazi1.ekonomia.EconomyPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EconomyCommand implements CommandExecutor, TabCompleter {

    private final EconomyPlugin plugin;

    public EconomyCommand(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("economyplus.admin")) {
            sender.sendMessage("§cBrak permisji.");
            return true;
        }

        if (args.length != 3) {
            sender.sendMessage("§c/economy remove <gracz> <ilość>");
            sender.sendMessage("§c/economy add <gracz> <ilość>");
            return true;
        }

        String action = args[0];
        OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§cPodaj prawidłową liczbę.");
            return true;
        }

        switch (action.toLowerCase()) {
            case "add":
                plugin.getEconomyManager().deposit(target, amount);
                sender.sendMessage("§aDodano §e" + amount + "§a dla gracza §e" + target.getName());
                break;

            case "remove":
                plugin.getEconomyManager().withdraw(target, amount);
                sender.sendMessage("§cUsunięto §e" + amount + "§c z portfela gracza §e" + target.getName());
                break;

            default:
                sender.sendMessage("§c/economy remove <gracz> <ilość>");
                sender.sendMessage("§c/economy add <gracz> <ilość>");
                break;
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!sender.hasPermission("economyplus.admin")) return Collections.emptyList();

        List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            completions.add("add");
            completions.add("remove");
        } else if (args.length == 2) {
            for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                completions.add(player.getName());
            }
        }


        return completions;
    }
}
