package me.arcx.auth;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AuthListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("§cPlease /login or /register to continue.");
        Bukkit.getScheduler().runTaskLater(AuthPlugin.getInstance(), () -> {
            if (!AuthManager.isAuthenticated(e.getPlayer().getUniqueId())) {
                e.getPlayer().kickPlayer("§cYou took too long to authenticate!");
            }
        }, AuthPlugin.getInstance().getConfig().getInt("kick-delay") * 20L);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!AuthManager.isAuthenticated(e.getPlayer().getUniqueId())) {
            e.setTo(e.getFrom());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        AuthManager.setAuthenticated(e.getPlayer().getUniqueId(), false);
    }
}
