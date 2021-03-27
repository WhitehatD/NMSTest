package me.whitehatd.NMSTest;

import de.tr7zw.nbtapi.NBTEntity;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.List;

public class CustomZombie extends EntityZombie {

    public CustomZombie(World world){

        super(world);
        this.setBaby(false);
        clearPathfinders();
    }
    private void clearPathfinders() {
        try {
            List goalB = (List)Utils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
            List goalC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
            List targetB = (List)Utils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
            List targetC = (List)Utils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    public static Zombie spawn(Player player) {
        Location location = player.getLocation();
        World mcWorld = ((CraftWorld) location.getWorld()).getHandle();
        final CustomZombie customEntity = new CustomZombie(
                mcWorld);
        customEntity.setLocation(location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch());
        ((CraftLivingEntity) customEntity.getBukkitEntity())
                .setRemoveWhenFarAway(false);
        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        NBTEntity nbtEntity = new NBTEntity(customEntity.getBukkitEntity());
        nbtEntity.setBoolean("Silent", true);
        return (CraftZombie) customEntity.getBukkitEntity();
    }

}
