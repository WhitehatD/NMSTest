package me.whitehatd.NMSTest;

import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Zombie;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class NMSTest extends JavaPlugin {

    public static ArrayList<ArmorStand> spawnedArmors = new ArrayList<>();
    public static ArrayList<Penguin> spawnedPets = new ArrayList<>();
    public static ArrayList<Zombie> spawnedZombies = new ArrayList<>();

    @Override
    public void onEnable(){
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        Utils.registerEntity("Zombie", 54, EntityZombie.class, CustomZombie.class);
        Bukkit.getConsoleSender().sendMessage(toColor("&aNMSTest enabled!"));
    }

    @Override
    public void onDisable(){
        for(Penguin pen : spawnedPets){
            pen.remove();
        }
    }

    public static String toColor(String t){
        return ChatColor.translateAlternateColorCodes('&', t);
    }

}
