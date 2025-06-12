package me.arcx.auth;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.UUID;

public class AuthManager {
    private static final File file = new File(AuthPlugin.getInstance().getDataFolder(), "users.yml");
    private static final YamlConfiguration users = YamlConfiguration.loadConfiguration(file);
    private static final HashMap<UUID, Boolean> authenticated = new HashMap<>();

    public static boolean isRegistered(String name) {
        return users.contains(name);
    }

    public static void register(String name, String password) throws Exception {
        users.set(name, hash(password));
        save();
    }

    public static boolean login(String name, String password) throws Exception {
        if (!isRegistered(name)) return false;
        return users.getString(name).equals(hash(password));
    }

    public static void setAuthenticated(UUID uuid, boolean value) {
        authenticated.put(uuid, value);
    }

    public static boolean isAuthenticated(UUID uuid) {
        return authenticated.getOrDefault(uuid, false);
    }

    private static void save() throws IOException {
        users.save(file);
    }

    private static String hash(String input) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] bytes = md.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}
