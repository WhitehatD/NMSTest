package me.whitehatd.NMSTest;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class Events implements Listener {

    private final Plugin instance;

    public Events(Plugin instance){
        this.instance = instance;
    }

    @EventHandler
    public void onText(AsyncPlayerChatEvent e){
        if(e.getMessage().equalsIgnoreCase("pet pls")){
            Player player = e.getPlayer();
            Bukkit.getScheduler().scheduleSyncDelayedTask(instance, () -> {
                        Penguin penguin = new Penguin(player.getLocation(), instance);
                        penguin.follow(player);
                    }
            );
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(!(e.getEntity() instanceof ArmorStand))return;
        if(NMSTest.spawnedArmors.contains((ArmorStand) e.getEntity()))e.setCancelled(true);
    }

    @EventHandler
    public void onDamage2(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Zombie))return;
        if(NMSTest.spawnedZombies.contains((Zombie) e.getEntity()))e.setCancelled(true);
    }

}
