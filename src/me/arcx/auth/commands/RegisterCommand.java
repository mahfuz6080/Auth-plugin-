package me.arcx.auth.commands;

import me.arcx.auth.AuthManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegisterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("§cUsage: /register <password>");
            return true;
        }
        try {
            if (AuthManager.isRegistered(player.getName())) {
                player.sendMessage("§cYou are already registered.");
                return true;
            }
            AuthManager.register(player.getName(), args[0]);
            AuthManager.setAuthenticated(player.getUniqueId(), true);
            player.sendMessage("§aRegistration successful! Redirecting...");
            new LoginCommand().onCommand(sender, cmd, label, args); // redirect
        } catch (Exception e) {
            player.sendMessage("§cError during registration.");
            e.printStackTrace();
        }
        return true;
    }
}
