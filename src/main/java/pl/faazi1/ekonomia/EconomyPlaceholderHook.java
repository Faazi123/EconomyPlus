package pl.faazi1.ekonomia;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyPlaceholderHook extends PlaceholderExpansion {

    private final EconomyPlugin plugin;

    public EconomyPlaceholderHook(EconomyPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "economy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Faazi1";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return "";

        if (identifier.equalsIgnoreCase("balance")) {
            return String.valueOf(plugin.getBalance(player));
        }

        return null;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier) {
        if (player == null) return "";

        if (identifier.equalsIgnoreCase("balance")) {
            return String.valueOf(plugin.getBalance((Player) player));
        }

        return null;
    }
}
