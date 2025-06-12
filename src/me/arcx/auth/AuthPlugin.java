package me.arcx.auth;

import me.arcx.auth.commands.LoginCommand;
import me.arcx.auth.commands.RegisterCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AuthPlugin extends JavaPlugin {
    private static AuthPlugin instance;

    public static AuthPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getCommand("login").setExecutor(new LoginCommand());
        getCommand("register").setExecutor(new RegisterCommand());
        Bukkit.getPluginManager().registerEvents(new AuthListener(), this);
    }
}
