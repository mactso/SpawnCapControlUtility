package com.mactso.spawncapcontrolutility.events;

import java.lang.reflect.Field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mactso.spawncapcontrolutility.Main;
import com.mactso.spawncapcontrolutility.config.MyConfig;

import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.coremod.api.ASMAPI;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(bus = Bus.FORGE, modid = Main.MODID)
public class EventHandler {
	private static final Logger LOGGER = LogManager.getLogger();


    @SubscribeEvent (priority = EventPriority.LOW)
    public static void onAboutToStart(ServerAboutToStartEvent event)
    {
    	
    	Field field = null;
        String fn = ASMAPI.mapField("f_21586_"); // internal MobCategory field : "max"
        try {
            field = MobCategory.class.getDeclaredField(fn);
            field.setAccessible(true);
        }
        catch (Exception e) {
            e.printStackTrace();
            return;
        }
        LOGGER.info("SpawncapControlUtiity Startup");
        LOGGER.info("Mob Category List");

		for (MobCategory m : MobCategory.values()) {
			String n = m.getName().toUpperCase();
        	LOGGER.info("Mob Category: " + n);
        }
        try {
            field.setInt(MobCategory.MONSTER, MyConfig.getMonsterCap());
            field.setInt(MobCategory.CREATURE, MyConfig.getCreatureCap());
            field.setInt(MobCategory.AMBIENT, MyConfig.getAmbientCap());
            field.setInt(MobCategory.WATER_CREATURE, MyConfig.getWaterCreatureCap());
            field.setInt(MobCategory.WATER_AMBIENT, MyConfig.getWaterAmbientCap());
            field.setInt(MobCategory.UNDERGROUND_WATER_CREATURE, MyConfig.getUndergroundWaterCreatureCap());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
