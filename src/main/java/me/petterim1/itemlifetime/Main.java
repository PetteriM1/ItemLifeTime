package me.petterim1.itemlifetime;

import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ItemSpawnEvent;
import cn.nukkit.plugin.PluginBase;

import java.lang.reflect.Field;

public class Main extends PluginBase implements Listener {

    private int itemLifetime;
    private Field age;

    public void onEnable() {
        try {
            age = Entity.class.getDeclaredField("age");
            age.setAccessible(true);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        saveDefaultConfig();
        itemLifetime = getConfig().getInt("itemLifetime", 6000);
        if (itemLifetime <= 6000) {
            itemLifetime = 6000 - itemLifetime;
        } else {
            itemLifetime = -itemLifetime + 6000;
        }
        getLogger().info("Enabled! Item lifetime: " + getConfig().getInt("itemLifetime", 6000) + " ticks");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent e) {
        Entity i = e.getEntity();
        i.namedTag.putShort("Age", itemLifetime);
        try {
            age.set(i, itemLifetime);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
