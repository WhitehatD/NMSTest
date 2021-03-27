package me.whitehatd.NMSTest;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.IntStream;

public class Penguin {

    public final HashMap<ArmorStand, Vector> parts = new HashMap<>();
    private BukkitRunnable runnable, runnable2;
    private final Plugin instance;
    private ArmorStand init;
    private Zombie zombie;

    public Penguin(Location location, Plugin instance){
        this.instance = instance;
        NMSTest.spawnedPets.add(this);

        location.setPitch(0);
        location.setYaw(0);

        init = Utils.getNewArmorStand(location, false, true);
        init.setHelmet(Utils.createCustomSkull(1, "", null, "53d1877be95a9edb86df2256f23958324c2ec19ef94277ce2fb5c3301841dc"));
        parts.put(init, new Vector(0,0,0));
        NMSTest.spawnedArmors.add(init);

        Vector v2 = new Vector(0f, -0.4f, 0f);
        ArmorStand p2 = Utils.getNewArmorStand(location.clone().add(v2), false, true);
        p2.setHelmet(new ItemStack(Material.STAINED_CLAY, 1, (short)4));
        parts.put(p2, v2);
        NMSTest.spawnedArmors.add(p2);

        Vector v5 = new Vector(0f, -0.1f, 0f);
        ArmorStand p5 = Utils.getNewArmorStand(location.clone().add(v5), false,  true);
        p5.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        parts.put(p5, v5);
        NMSTest.spawnedArmors.add(p5);

    }

    public void follow(Player player){
        zombie = CustomZombie.spawn(player);
        NMSTest.spawnedZombies.add(zombie);
        Bukkit.getScheduler().runTaskLater(instance, () -> {
                   ((CraftEntity) zombie).getHandle().setInvisible(true);
                }, 1L);
        runnable2 = new BukkitRunnable () {
            @Override
            public void run() {
                if(zombie.getLocation().distance(player.getLocation())>3)
                    ((EntityInsentient) ((CraftEntity) zombie).getHandle()).getNavigation().a(player.getLocation().getX(),
                        player.getLocation().getY(), player.getLocation().getZ(), 1f);
            }
        };
        runnable2.runTaskTimer(instance, 5L, 40L);
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                Location loc = zombie.getLocation();
                for(ArmorStand stand : parts.keySet()){
                    stand.teleport(loc.clone().add(parts.get(stand)));
                }
            }
        };
        runnable.runTaskTimer(instance, 5L, 3L);
    }

    public void stopFollow(){
        NMSTest.spawnedZombies.remove(zombie);
        zombie.remove();
        runnable.cancel();
        runnable2.cancel();
    }

    public void remove(){
        stopFollow();
        for(ArmorStand stand : parts.keySet()) {
            NMSTest.spawnedArmors.remove(stand);
            stand.remove();
        }
        parts.clear();
    }
}
