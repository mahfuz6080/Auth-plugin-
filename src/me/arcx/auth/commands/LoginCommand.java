package me.arcx.auth.commands;

import me.arcx.auth.AuthManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class LoginCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length != 1) {
            player.sendMessage("§cUsage: /login <password>");
            return true;
        }
        try {
            if (AuthManager.login(player.getName(), args[0])) {
                AuthManager.setAuthenticated(player.getUniqueId(), true);
                player.sendMessage("§aLogin successful! Redirecting...");
                sendToServer(player);
            } else {
                player.sendMessage("§cInvalid password.");
            }
        } catch (Exception e) {
            player.sendMessage("§cError during login.");
            e.printStackTrace();
        }
        return true;
    }

    private void sendToServer(Player player) {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(b);
        try {
            out.writeUTF("Connect");
            out.writeUTF(me.arcx.auth.AuthPlugin.getInstance().getConfig().getString("redirect-server"));
            player.sendPluginMessage(me.arcx.auth.AuthPlugin.getInstance(), "BungeeCord", b.toByteArray());
        } catch (Exception e) {
            player.sendMessage("§cCould not redirect.");
        }
    }
}
