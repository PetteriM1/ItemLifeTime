package me.petterim1.itemlifetime;

import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ItemSpawnEvent;
import cn.nukkit.plugin.PluginBase;

import java.lang.reflect.Field;

public class Main extends PluginBase implements Listener {

    private int itemLifetime;

    public void onEnable() {
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
    public void onItemSpawn(ItemSpawnEvent e) throws NoSuchFieldException, IllegalAccessException {
        Entity i = e.getEntity();
        i.namedTag.putShort("Age", itemLifetime);
        Class<?> c = i.getClass().getSuperclass();
        Field f = c.getDeclaredField("age");
        f.setAccessible(true);
        f.set(i, itemLifetime);
    }
}
